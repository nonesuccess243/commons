package com.wayeasoft.waf.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.generator.CrudGenerator;

@RestController
@RequestMapping("/api/data")
public class CommonDataController
{
        private final static Logger log = LoggerFactory.getLogger(CommonDataController.class);
        
        @Autowired
        private DataSource dataSource;
        
        
        @RequestMapping("/{model}/{id}")
        @ResponseBody
        public String queryById(@PathVariable String model, @PathVariable Long id)
        { 
                log.debug("model={}, id={}", model, id);
                
                try
                {
                        
                        Connection connection = dataSource.getConnection();
                        
                        CrudGenerator.db = Db.MYSQL;
                        CrudGenerator c = new CrudGenerator(DemoModel.class);
                        
                        c.generate();
                        
                        DatabaseMetaData meta = connection.getMetaData();
                        ResultSet resultSet = meta.getTables(null, null, c.getMeta().getDbTypeName(), null);
                        
                        if( resultSet.next())
                        {
                                log.debug(resultSet.getString("TABLE_CAT") + "\t"  
                                                + resultSet.getString("TABLE_SCHEM") + "\t"  
                                                + resultSet.getString("TABLE_NAME") + "\t"  
                                                + resultSet.getString("TABLE_TYPE"));  
                        }else
                        {
                                int resultset = connection.createStatement().executeUpdate(c.getCreateSqlContent());
                        }
                        
                        
//                        log.debug(c.getCreateSqlContent());
//                        
//                        c.getMeta().getDbTypeName();
//                        
//                        int resultset = connection.createStatement().executeUpdate(c.getCreateSqlContent());
//
//                        log.debug("" + resultset);
//                        
//
//                        ResultSet resultset2 = dataSource.getConnection().createStatement().executeQuery("select 1 from " + c.getMeta().getDbTypeName());
//                        resultset2.getMetaData().getColumnCount();
                } catch (SQLException e)
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (Exception e)
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                return "success";
        }

}
