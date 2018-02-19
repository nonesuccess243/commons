package com.wayeasoft.core.configuration;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(value = Cfgables.class)
public @interface Cfgable
{
        /**
         * 配置项key
         * @return
         */
        public String key();
        
        /**
         * 关于配置项意义、用法的描述
         * @return
         */
        public String description();
        
        /**
         * 配置项类型。
         * 
         * 大部分情况下都会采用配置文件进行配置，因此配置项的类型应有与String双向转换的明确逻辑
         * @return
         */
        public Class<?> type();
        
        /**
         * 默认值
         * 
         * 由于注解中的数据类型不能为复杂类型，如果此字段不能存储，则在此字段中用文字描述默认值
         * @return
         */
        public String[] defaultValue();
        
}
