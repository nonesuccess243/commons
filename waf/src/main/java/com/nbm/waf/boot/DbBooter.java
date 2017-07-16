package com.nbm.waf.boot;

import java.sql.ResultSet;
import java.util.Iterator;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.generator.CrudGenerator;

@Service
public class DbBooter
{
        
        private Logger log = LoggerFactory.getLogger(DbBooter.class);
        
        @Autowired
        private DataSource dataSource;
        
        
        private String basePackage = "com.wayeasoft.waf.demo";

        public void boot() throws Exception
        {
                log.debug("basePackage:{}", basePackage);
                
                ModelMeta.discover(basePackage);

                for (Iterator<ModelMeta> iter = ModelMeta
                                .getAllDiscoveredModelMeta().iterator(); iter
                                                .hasNext();)
                {
                        ModelMeta meta = iter.next();

                        log.debug("开始处理{}", meta.getModelClass().getName());

                        CrudGenerator.GENERATE_FILE = false;
                        CrudGenerator generator = new CrudGenerator(
                                        meta.getModelClass());
                        generator.generate();

                        
                        ResultSet tableMetaResultSet = dataSource.getConnection().getMetaData().getTables(null, null, meta.getDbTypeName(), null);
                        if( tableMetaResultSet.next())//找到这张表了
                        {
                                
                                log.debug("该表已经存在:[{}]",tableMetaResultSet.getString("TABLE_CAT") + "\t"  
                                                + tableMetaResultSet.getString("TABLE_SCHEM") + "\t"  
                                                + tableMetaResultSet.getString("TABLE_NAME") + "\t"  
                                                + tableMetaResultSet.getString("TABLE_TYPE"));  
                        }else//不存在这张表
                        {
                                log.debug("不存在[{}]表，根据sql语句创建[{}]", meta.getDbTypeName(), generator.getCreateSqlContent());
                                dataSource.getConnection().createStatement().execute(
                                                generator.getCreateSqlContent());
                        }
                        
                       
                }

        }
}
