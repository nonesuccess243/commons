package com.nbm.waf.boot;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.data.ModelRegister;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.exception.NbmBaseRuntimeException;
import com.wayeasoft.core.configuration.Cfg;
import com.younker.waf.db.DataSourceProvider;

public enum DbBooter
{

        I;
        private Logger log = LoggerFactory.getLogger(DbBooter.class);

        public void boot()
        {

                try (Connection connection = DataSourceProvider.instance().getDataSource().getConnection())
                {

                        // 创建表
                        for (Iterator<ModelMeta> iter = ModelRegister.INSTANCE.getAllModel().iterator(); iter
                                        .hasNext();)
                        {
                                ModelMeta meta = iter.next();
                                try
                                {

                                        log.info("开始处理{}", meta.getModelClass().getName());

                                        CrudGenerator.GENERATE_FILE = false;
                                        CrudGenerator.db = Db.getByProductName(
                                                        DataSourceProvider.instance().getDatabaseProductName());
                                        CrudGenerator generator = new CrudGenerator(meta.getModelClass());
                                        generator.generate();

                                        ResultSet tableMetaResultSet = connection.getMetaData().getTables(null, null,
                                                        meta.getDbTypeName(), null);
                                        if (tableMetaResultSet.next())// 找到这张表了
                                        {

                                                log.info("该表已经存在:[{}]", tableMetaResultSet.getString("TABLE_CAT") + "\t"
                                                                + tableMetaResultSet.getString("TABLE_SCHEM") + "\t"
                                                                + tableMetaResultSet.getString("TABLE_NAME") + "\t"
                                                                + tableMetaResultSet.getString("TABLE_TYPE"));
                                        } else// 不存在这张表
                                        {
                                                log.info("不存在[{}]表，根据sql语句创建[{}]", meta.getDbTypeName(),
                                                                generator.getCreateSqlContent());
                                                connection.createStatement().execute(generator.getCreateSqlContent());
                                                connection.commit();
                                        }
                                } catch (Exception e)
                                {
                                        log.error(meta.getModelClass().toString(), e);
                                        // throw new NbmBaseRuntimeException("DB boot 发生异常", e).set("model",
                                        // meta.getModelClass());
                                        continue;
                                }

                        }

                        // 执行sql
                        String[] sqlPaths = Cfg.I.get("commons.db_booter.sql", String[].class, new String[]
                        {});
                        for (String sqlPath : sqlPaths)
                        {
                                log.info("run sql: [{}]", sqlPath);
                                
                                try
                                {
                                        String sql = new String(Files.readAllBytes(Paths.get(this.getClass()
                                                        .getClassLoader().getResource(sqlPath).toURI())));
                                        
                                        
                                        DataSourceProvider.instance().runBatch(sql);
                                } catch (Exception e)
                                {
                                        log.error("执行sql发生异常[sqlPath=" + sqlPath + "]", e);
                                }
                        }
                } catch (SQLException e1)
                {
                        throw new NbmBaseRuntimeException("DB boot 发生异常", e1);
                }

                // connection.commit();
                // connection.close();

        }
}
