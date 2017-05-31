package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.nbm.core.modeldriven.DbType;

@Retention(RetentionPolicy.RUNTIME)
public @interface DbTypeAnno
{
        public DbType value();
}
