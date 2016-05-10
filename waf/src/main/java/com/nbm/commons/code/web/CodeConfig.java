/***********************************************
 * Title:       CodeConfig.java
 * Description: CodeConfig.java
 * Author:      computer
 * Create Date: 2010-9-1
 * CopyRight:   CopyRight(c)@2009
 * Company:     TJUSCS
 * Version:     1.0
 ***********************************************
 */
package com.nbm.commons.code.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author computer
 * 
 */
@WebListener
public class CodeConfig implements ServletContextListener
{
        private final static Logger log = LoggerFactory.getLogger(CodeConfig.class);

        @Override
        public void contextDestroyed(ServletContextEvent arg0)
        {
                // TODO Auto-generated method stub

        }

        @Override
        public void contextInitialized(ServletContextEvent contextEvent)
        {
                log.debug("sql code 开始初始化");
                try
                {
                        CodeSQLConfig.getInstance().init(
                                        contextEvent.getServletContext().getResource(
                                                        "/WEB-INF/config/options.properties"));
                } catch (Exception e)
                {
                        log.error("sql code初始化失败", e);
                }

        }

}
