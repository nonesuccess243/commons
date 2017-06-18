package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.nbm.core.modeldriven.PureModel;

/**
 * 外键，一定叫xxid，xx是另外一张表的表名。如果不符合这个要求，则需要手动写foreigntable属性
 * @author 玉哲
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Fk
{
        public Class<? extends PureModel> foreign();
}
