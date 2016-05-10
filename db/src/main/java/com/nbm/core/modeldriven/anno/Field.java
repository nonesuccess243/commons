package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Field
{
        public boolean dbIgnore() default false;

}
