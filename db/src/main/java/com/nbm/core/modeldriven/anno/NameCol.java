package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 是某张表表示名称含义的列，例如用户表的用户名列
 * 
 * 针对name列的select操作较为常见，如果都构建example则过于麻烦，因此后期计划在生成的dao中直接增加关于此列的函数3
 * 
 * 标记为nameCol的字段类型必须为String
 * @author 玉哲
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NameCol
{

}
