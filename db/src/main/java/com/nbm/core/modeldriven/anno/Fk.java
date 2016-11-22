package com.nbm.core.modeldriven.anno;

/**
 * 外键，一定叫xxid，xx是另外一张表的表名。如果不符合这个要求，则需要手动写foreigntable属性
 * @author 玉哲
 *
 */
public @interface Fk
{
        public String foreignTable();
}
