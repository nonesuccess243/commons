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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * 
 * 这是一个servlet listener，用于初始化获取键值对的sql文件。
 * 
 * 配置文件固定在/WEB-INF/config/options.properties路径中。
 * 
 * 必须以sql.开头，值为一句结果集为两列，第一列的意义为code，第二列的意义为name的sql语句
 * 
 * 当初起名字的时候没有以listener为后缀
 * 
 * @author computer
 * 
 */
//@WebListener
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
