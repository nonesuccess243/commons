package com.wayeasoft.springmvc.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.nbm.exception.NbmBaseRuntimeException;
import com.wayeasoft.core.configuration.Cfg;

@Configuration
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

        @Override
        protected Class<?>[] getRootConfigClasses()
        {
                Set<Class<?>> result = new HashSet<>();
                result.add(RootConfig.class);
                String[] classes = Cfg.I.get("spring.mvc.config_classes", String[].class);
                if (classes != null)
                {
                        for (String className : classes)
                        {
                                try
                                {
                                        result.add(Class.forName(className));
                                } catch (ClassNotFoundException e)
                                {
                                        throw new NbmBaseRuntimeException("生成spring配置类时发生异常", e);
                                }
                        }
                }
                return result.toArray(new Class<?>[0]);
        }

        @Override
        protected Class<?>[] getServletConfigClasses()
        {
                String[] classes = Cfg.I.get("spring.mvc.servlet_config_classes", String[].class);
                if (classes != null)
                {
                        Set<Class<?>> result = new HashSet<>();
                        for (String className : classes)
                        {
                                try
                                {
                                        result.add(Class.forName(className));
                                } catch (ClassNotFoundException e)
                                {
                                        throw new NbmBaseRuntimeException("生成spring配置类时发生异常", e);
                                }
                        }
                        return result.toArray(new Class<?>[0]);
                } else
                {
                        return new Class<?>[]
                        { WebConfig.class };
                }
        }

        @Override
        protected String[] getServletMappings()
        {
                return Cfg.I.get("spring.mvc.servlet_mappings", String[].class, new String[]
                { "*.do" });
        }

}
