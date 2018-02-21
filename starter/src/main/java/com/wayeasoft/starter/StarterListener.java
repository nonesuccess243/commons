package com.wayeasoft.starter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化所有commons组件
 * 
 */
public class StarterListener implements ServletContextListener
{

        private final static Logger log = LoggerFactory.getLogger(StarterListener.class);;

        /**
         * Default constructor.
         */
        public StarterListener()
        {
                // TODO Auto-generated constructor stub
        }

        /**
         * @see ServletContextListener#contextInitialized(ServletContextEvent)
         */
        public void contextInitialized(ServletContextEvent contextEvent)
        {
                Starter.I.start();
        }


        @Override
        public void contextDestroyed(ServletContextEvent arg0)
        {
        	//
        }

}
