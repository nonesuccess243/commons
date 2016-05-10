package com.nbm.core.modeldriven.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.nbm.core.modeldriven.enums.MySqlDateType;

/**
 * 本意是用于区分mysql的date和time字段
 * 
 * 但是不应该在名称中依赖mysql这一具体的数据库类型，因此废弃
 * 
 * 暂无替代方案，后期会修改为更通用的形式
 * @author 玉哲
 *
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface MysqlDateTime
{
	public MySqlDateType value();
}
