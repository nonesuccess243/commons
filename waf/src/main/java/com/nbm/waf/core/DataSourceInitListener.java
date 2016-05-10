package com.nbm.waf.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.db.DataSourceProvider;

/**
 * 处理数据源初始化的相关事务。 当前还包含了使用数据源的工具类的初始化操作。TODO 应逐步去掉。暂定蛇年（2013）春节之前不整理 TODO
 * 生产环境下课考虑将注解去掉，采用web-fragment
 * 
 */
// @WebListener TODO 项目使用MyBatis进行数据源的初始化，暂时不需要此监听器。
public class DataSourceInitListener implements ServletContextListener
{

        private final static Logger log = LoggerFactory.getLogger(DataSourceInitListener.class);

        /**
         * Default constructor.
         */
        public DataSourceInitListener()
        {
        }

        /**
         * @see ServletContextListener#contextInitialized(ServletContextEvent)
         */
        public void contextInitialized(ServletContextEvent arg0)
        {
                DataSourceProvider.instance();
                log.info("数据源初始化完成");

                // CodeUtils.initDefault(DataSourceProvider.instance().getDataSource());

        }

        /**
         * @see ServletContextListener#contextDestroyed(ServletContextEvent)
         */
        public void contextDestroyed(ServletContextEvent arg0)
        {
        }

}
