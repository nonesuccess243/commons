package com.younker.waf.db.mybatis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;

/**
 * Application Lifecycle Listener implementation class MybatisInitListener
 * 
 */
public class MybatisInitListener implements ServletContextListener
{

        private final static Logger log = LoggerFactory.getLogger(MybatisInitListener.class);;

        /**
         * Default constructor.
         */
        public MybatisInitListener()
        {
                // TODO Auto-generated constructor stub
        }

        /**
         * @see ServletContextListener#contextInitialized(ServletContextEvent)
         */
        public void contextInitialized(ServletContextEvent contextEvent)
        {
                try
                {
                    MybatisDao.INSTANCE.init();
                } catch (Exception e)
                {
                	throw new NbmBaseRuntimeException("Mybatis初始化失败", e);
                }
                log.info("Mybatis初始化完成");
        }


        @Override
        public void contextDestroyed(ServletContextEvent arg0)
        {
        	//
        }

}
