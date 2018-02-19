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

import com.nbm.exception.NbmBaseRuntimeException;
import com.wayeasoft.core.configuration.Cfg;

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
        
        private final static String DEFAULT_JDBC_URL = "jdbc:h2:mem:DBName";

        private static String databaseProductName;
        
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

        public DataSourceProvider(DataSource dataSource)
        {
                ds = dataSource;
        }

        public static DataSourceProvider instance()
        {
                if (_instance == null)
                {
                        _instance = new DataSourceProvider();
                }
                return _instance;
        }
        
        /**
         * 传入datasource初始化的方式，仅用于与spring适配
         * @param dataSource
         * @return
         */
        public static DataSourceProvider initInstance( DataSource dataSource )
        {
                if (_instance == null)
                {
                        _instance = new DataSourceProvider(dataSource);
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
         * 用最简单的方式初始化数据源
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
		
		fillDatabaseProductName();
        	
        }
        
        /**
         * 用最简单的方式初始化数据源
         * @param p 传入一个配置对象，可以控制任何参数
         */
        public static void initSimple( PoolProperties p )
        {
        	org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        	datasource.setPoolProperties(p);
        	
        	ds = datasource;
        	
        	fillDatabaseProductName();
        	
        }
        
        /**
         * 用配置文件中配置的信息，初始化数据源。
         * 
         * 默认配置为初始化一个用户名为sa，密码为空的内存h2数据库，供测试使用。
         * 
         * 项目必须引用正确的jdbcdriver，默认配置方式下，必须引入h2
         * 
         * 默认配置详单：还没写
         * 
         */
        public static void initSimple()
        {
                
                log.info("使用appconfig方式进行数据源初始化");
                
                PoolProperties p = new PoolProperties();
                p.setUrl(Cfg.I.get("commons.datasource.url", String.class, DEFAULT_JDBC_URL));
                p.setDriverClassName(Cfg.I.get("commons.datasource.drivername", String.class, "org.h2.Driver"));
                p.setUsername(Cfg.I.get("commons.datasource.username", String.class, "sa"));
                p.setPassword(Cfg.I.get("commons.datasource.password", String.class, ""));
                p.setJmxEnabled(true);
                p.setTestWhileIdle(false);
                p.setTestOnBorrow(true);
                p.setValidationQuery("SELECT 1 FROM DUAL");
                p.setTestOnReturn(false);
                p.setValidationInterval(30000);
                p.setTimeBetweenEvictionRunsMillis(30000);
                p.setMaxActive(Cfg.I.get("commons.datasource.max_active", Integer.class, 100));
                p.setInitialSize(Cfg.I.get("commons.datasource.initial_size", Integer.class, 10));
                p.setMaxWait(Cfg.I.get("commons.datasource.max_wait", Integer.class, 10000));
                p.setRemoveAbandonedTimeout(Cfg.I.get("commons.datasource.remove_abandoned_timeout", Integer.class, 60));
                p.setMinEvictableIdleTimeMillis(30000);
                p.setMinIdle(Cfg.I.get("commons.datasource.min_idle", Integer.class, 10));
                p.setLogAbandoned(true);
                p.setRemoveAbandoned(true);
                p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
                
                log.info("初始化信息为：{}", p.toString());
                
                if( DEFAULT_JDBC_URL.equals(p.getUrl()))
                {
                        log.info("注意！！！！当前正在使用测试内存数据库");
                }
                
                org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
                datasource.setPoolProperties(p);
                
                ds = datasource;
                
                log.info("datasource初始化完成");
                
                fillDatabaseProductName();
                
        }

        private static void fillDatabaseProductName()
        {
                try(Connection conn = ds.getConnection())
                {
                        
                        databaseProductName = conn.getMetaData().getDatabaseProductName();
                        if( databaseProductName == null )
                        {
                                throw new NbmBaseRuntimeException("将null赋值给databaseProductName");
                        }
                }catch(Exception e )
                {
                        throw new NbmBaseRuntimeException("初始化datasource发生异常", e);
                }
        }
        
        public String getDatabaseProductName()
        {
                if( databaseProductName == null )
                {
                        throw new NbmBaseRuntimeException("databaseProductName为空");
                }
                return databaseProductName;
        }

}
