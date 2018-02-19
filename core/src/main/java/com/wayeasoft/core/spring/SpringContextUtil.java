package com.wayeasoft.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 提供一个在非spring环境下获取spring bean的全局方法
 * @author niyuzhe
 *
 */
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
                return SpringContextUtil.applicationContext.getBean(clazz);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
                        throws BeansException
        {
                SpringContextUtil.applicationContext = applicationContext;
        }

}
