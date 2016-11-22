package com.nbm.core.modeldriven.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 在model需要特殊配置时，可以添加此注解
 * @author 玉哲
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelInfo
{
        /**
         * 数据库中对应的表名
         * 
         * 默认情况下，会将java表示法转换为下划线表示法
         * 
         * 如果不是用此转换规则，则可以使用此注解属性进行配置
         * 
         * @return
         */
        public String tableName() default "";
}
