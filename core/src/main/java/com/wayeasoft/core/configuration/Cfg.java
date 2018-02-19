package com.wayeasoft.core.configuration;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;

/**
 * 会在classpath中读取appconfig.properties文件，得到相应的配置值。
 * 
 * 如果不存在appconfig.properties文件，不会报错，但会建立一个空的config对象。这种模式一般应用于全部配置信息都使用默认值的情况。
 * 
 * 如果不想用配置文件，也可以用本类的set函数进行赋值，只要在使用Cfg信息的代码初始化之前进行set即可——此模式用途不大，因此未完整测试
 * 
 * 
 * @author niyuzhe
 *
 */
public enum Cfg
{
        /**
         * I for INSTANCE
         */
        I;

        private final static String PROPERTIES_FILE = "appconfig.properties";
        
        private static Logger log = LoggerFactory.getLogger(Cfg.class);

        private Configuration config = null;
        
        static
        {
                I.warmUp();
        }

        /**
         * 传入配置文件路径进行初始化
         * 
         * 此函数不推荐使用，实际中并没有差异化配置的必要，写这个函数只是为了测试程序在有无配置文件时的不同行为
         * 
         * @param propertiesFile
         */
        public void warmUp(String propertiesFile)
        {
                URL resource = this.getClass().getClassLoader().getResource(propertiesFile);

                if (resource == null)// 配置文件不存在
                {
                        log.warn("注意！！！当前项目环境中没有[{}]配置文件，所有配置都将使用默认内容", propertiesFile);
                        config = null;
                        log.info("配置信息热身完成，内容为：[无配置文件]");
                } else
                {
                        try
                        {
                                log = LoggerFactory.getLogger(Cfg.class);
                                
                                config = new Configurations().properties(new File(resource.toURI()));
                                log.info("配置信息热身完成，共[{}]条", config.size());
                        } catch (Exception e)
                        {
                                throw new NbmBaseRuntimeException("初始化cfg发生异常", e);
                        }
                }
                
        }

        public void warmUp()
        {
                warmUp(PROPERTIES_FILE);
        }

        public <T> T get(String key, Class<T> claz)
        {
                return config.get(claz, key);
        }

        /**
         * 
         * @param key
         * @param claz
         * @param defaultValue
         * @return
         * 
         * @throws org.apache.commons.configuration2.ex.ConversionException
         *                 if the value is not compatible with the requested type
         */
        public <T> T get(String key, Class<T> claz, T defaultValue)
        {
                if (config == null)
                {
                        return defaultValue;
                }
                return config.get(claz, key, defaultValue);
        }

        public Cfg set(String key, Object value)
        {
                config.setProperty(key, value);
                return I;
        }

        public Set<Map<String, Object>> getAllDeclared()
        {

                // TODO 未完成
                Reflections reflections = new Reflections("com", new SubTypesScanner(), new TypeAnnotationsScanner());

                Set<Class<?>> cfgableClasses = reflections.getTypesAnnotatedWith(Cfgable.class);

                Set<Map<String, Object>> results = new HashSet<>();

                for (Class<?> claz : cfgableClasses)
                {
                        for (Cfgable cfgable : claz.getAnnotationsByType(Cfgable.class))
                        {
                                Map<String, Object> result = new HashMap<>();
                                result.put("class", claz);
                                result.put("cfgable", cfgable);
                                results.add(result);
                        }
                }

                return results;
        }

}
