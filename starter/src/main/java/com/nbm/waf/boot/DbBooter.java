package com.nbm.waf.boot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.data.ModelRegister;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.exception.NbmBaseRuntimeException;
import com.younker.waf.db.DataSourceProvider;

public enum DbBooter
{
        
        I;
        private Logger log = LoggerFactory.getLogger(DbBooter.class);
        

        public void boot() 
        {
                
                
                try
                {
                        Connection connection = DataSourceProvider.instance().getDataSource().getConnection();
                        
                        for (Iterator<ModelMeta> iter = ModelRegister.INSTANCE.getAllModel().iterator(); iter
                                                        .hasNext();)
                        {
                                ModelMeta meta = iter.next();

                                log.debug("开始处理{}", meta.getModelClass().getName());

                                CrudGenerator.GENERATE_FILE = false;
                                CrudGenerator.db = Db.getByProductName(DataSourceProvider.instance().getDatabaseProductName());
                                CrudGenerator generator = new CrudGenerator(
                                                meta.getModelClass());
                                generator.generate();

                                
                                ResultSet tableMetaResultSet = connection.getMetaData().getTables(null, null, meta.getDbTypeName(), null);
                                if( tableMetaResultSet.next())//找到这张表了
                                {
                                        
                                        log.debug("该表已经存在:[{}]",tableMetaResultSet.getString("TABLE_CAT") + "\t"  
                                                        + tableMetaResultSet.getString("TABLE_SCHEM") + "\t"  
                                                        + tableMetaResultSet.getString("TABLE_NAME") + "\t"  
                                                        + tableMetaResultSet.getString("TABLE_TYPE"));  
                                }else//不存在这张表
                                {
                                        log.debug("不存在[{}]表，根据sql语句创建[{}]", meta.getDbTypeName(), generator.getCreateSqlContent());
                                        connection.createStatement().execute(
                                                        generator.getCreateSqlContent());
                                }
                                
                               
                        }
                        
//                connection.commit();
                        connection.close();
                } catch (Exception e)
                {
                        throw new NbmBaseRuntimeException("DB boot 发生异常", e);
                } 

        }
}
