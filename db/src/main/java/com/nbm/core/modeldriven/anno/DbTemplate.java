package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DbTemplate
{
        public String fetchPrefix() default "";
        
        public String fetchSuffix() default "";
        
        public String populatePrefix() default "";
        
        public String populateSuffix() default "";

}
