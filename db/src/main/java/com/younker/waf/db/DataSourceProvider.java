/******************************************************************************
 * Title:     Younker Web Application Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在web项目中统一提供数据源，为数据库操作的核心类。
 * 
 * 默认情况下会读取jndi数据源，即context.xml中定义的名为jdbc/ds数据源
 * 
 * 提供initSimple函数，创建简单的数据源，依赖于tomcat的连接池实现
 */
public class DataSourceProvider
{ 

        private static DataSourceProvider _instance = null;
        private static DataSource ds = null;
        private static Context ctx = null;
        private static final Logger log = LoggerFactory.getLogger(DataSourceProvider.class);

        private DataSourceProvider()
        {
        	
        	if( ds != null )
        	{
        		return;
        	}

                try
                {
                        ctx = new InitialContext();
                        Context envContext = (Context) ctx.lookup("java:/comp/env");
                        ds = (DataSource) envContext.lookup("jdbc/ds");
                } catch (NamingException ex)
                {
                        log.error("Can't find datasource", ex);
                } finally
                {
                        try
                        {
                                ctx.close();
                        } catch (Exception ex)
                        {
                        }
                }
        }

        public static DataSourceProvider instance()
        {
                if (_instance == null)
                {
                        _instance = new DataSourceProvider();
                }
                return _instance;
        }

        public DataSource getDataSource()
        {
                return ds;
        }
        
        public void runBatch( String sql ) throws SQLException
        {
                Connection conn = getDataSource().getConnection();
                conn.createStatement().execute(sql);
                conn.commit();
                conn.close();
        }
        
        public void runBatch( Path path ) throws SQLException, IOException
        {
        	runBatch(new String(Files.readAllBytes(path)));
        }
        
        
        /**
         * 初始化简单数据源
         * @param driverClassName
         * @param url
         * @param username
         * @param password
         */
        public static void initSimple( String driverClassName, String url, String username, String password )
        {
        	PoolProperties p = new PoolProperties();
		p.setUrl(url);
		p.setDriverClassName(driverClassName);
		p.setUsername(username);
		p.setPassword(password);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1 FROM DUAL");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
		                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
		datasource.setPoolProperties(p);
		
		ds = datasource;
        	
        }
        public static void initSimple( PoolProperties p )
        {
        	org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        	datasource.setPoolProperties(p);
        	
        	ds = datasource;
        	
        }

//        public Connection getConnection()
//        {
//                Connection connection = null;
//                try
//                {
//                        connection = ds.getConnection();
//
//                } catch (SQLException ex)
//                {
//                        log.error("Can't get an unused connection ", ex);
//                }
//                return connection;
//        }
}
