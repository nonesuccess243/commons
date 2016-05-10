package com.nbm.commons.db.meta;

public interface DbNamingConverter
{
	public final static DbNamingConverter DEFAULT_ONE = UnderlineCamelConverter.INSTANCE;

	/**
	 * 将数据库列名转换为Java属性名，即将下划线分割的命名转换为首字母小写的驼峰命名法。 如SOME_FIELD转换为someField。
	 */
	public abstract String columnName2JavaPropertyName(String columnName);

	public abstract String javaPropertyName2ColumnName(String javaPropertyName);

	/**
	 * 表名转换为Java类名
	 */
	public abstract String tableName2JavaTypeName(String tableName);

	/**
	 * 类名转换为表名
	 */
	public abstract String javaTypeName2TableName(String className);

	/**
	 * 返回数据库类型对应的Java类型
	 */
	public abstract String sqlType2JavaTypeName(int type);

}
