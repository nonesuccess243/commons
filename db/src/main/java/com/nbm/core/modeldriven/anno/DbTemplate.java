package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于复杂类型（类类型）对应数据库中某个字段，在双向转换时需要调用某些特殊函数的情况
 * 
 * 如select [colomnName] 语句会被转换为select fetchPrefix() [colomnName] fetchSuffix ...
 * 
 * update和insert语句会被转换为insert/update populatePrefix [colomnName] populateSuffix ...
 * 
 * 具体的转换逻辑在commonMapper.xml中。
 * 
 * TODO 现在尚无方案支持多种数据库方言
 * 
 * @author niyuzhe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DbTemplate
{
        
        /**
         * 用于从数据库中取出数据时的前缀字符串
         * 
         * 
         * @return
         */
        public String fetchPrefix() default "";
        
        public String fetchSuffix() default "";
        
        public String populatePrefix() default "";
        
        public String populateSuffix() default "";

}
