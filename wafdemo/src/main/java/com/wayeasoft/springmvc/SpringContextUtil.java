package com.wayeasoft.springmvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware 
{
        private static final SpringContextUtil instance = new SpringContextUtil();
        private static ApplicationContext applicationContext;

        private SpringContextUtil()
        {
        }

        public static SpringContextUtil getInstance()
        {
                return instance;
        }

        public <T> T getBean(Class<T> clazz)
        {
                
                System.out.println("applicationContext" + applicationContext);
                System.out.println(applicationContext.getBean(clazz));
                
                return SpringContextUtil.applicationContext.getBean(clazz);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
                        throws BeansException
        {
                System.out.println("param applicationContext" + applicationContext);
                SpringContextUtil.applicationContext = applicationContext;
                System.out.println("this applicationContext" + SpringContextUtil.applicationContext);
        }

}
