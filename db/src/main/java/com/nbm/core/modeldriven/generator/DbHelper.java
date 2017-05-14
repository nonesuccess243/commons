package com.nbm.core.modeldriven.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.db.DataSourceProvider;

public class DbHelper
{
        private final static Logger log = LoggerFactory
                        .getLogger(DbHelper.class);

        public static void CREATE_TABLE(String tableName, String sql)
                        throws SQLException
        {
                Connection connection = null;
                try
                {
                        connection = DataSourceProvider.instance()
                                        .getDataSource().getConnection();

                        DatabaseMetaData meta = connection.getMetaData();
                        ResultSet resultSet = meta.getTables(null, null,
                                        tableName, null);

                        if (resultSet.next())
                        {
                                log.error("表{}已经存在"
                                                + resultSet.getString(
                                                                "TABLE_CAT")
                                                + "\t"
                                                + resultSet.getString(
                                                                "TABLE_SCHEM")
                                                + "\t"
                                                + resultSet.getString(
                                                                "TABLE_NAME")
                                                + "\t"
                                                + resultSet.getString(
                                                                "TABLE_TYPE"),
                                                tableName);
                        } else
                        {
                                log.debug("使用以下sql建表：\n{}", sql);
                                int result = connection.createStatement()
                                                .executeUpdate(sql);
                                if (result == 0)
                                {
                                        log.info("创建{}表成功", tableName);
                                } else
                                {
                                        log.error("创建{}表失败", tableName);
                                }
                        }
                        
                        resultSet.close();
                } catch (SQLException e)
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } finally
                {
                        if (connection != null)
                        {
                                connection.close();
                        }
                }

        }

}
