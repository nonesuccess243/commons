package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 加在字段上，不用加在get函数上
 * @author niyuzhe
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DbIgnore
{
        
}
