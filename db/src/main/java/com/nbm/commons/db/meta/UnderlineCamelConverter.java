/***********************************************
 * Title: Db2JavaUtil.java Description: Db2JavaUtil.java Author: computer Create
 * Date: 2010-8-16 CopyRight: CopyRight(c)@2009 Company: TJUSCS Version: 1.0
 ***********************************************
 */
package com.nbm.commons.db.meta;

import java.sql.Types;

/**
 * 按照约定的命名规范，在Java类名、字段名和数据库表名、字段名之间做字符串转换。
 * Java类名采用首字母大写的驼峰命名法，如TheClass，字段名和函数名采用首字母小写的驼峰命名法，如theMethod。
 * 数据库类名和字段名，都用下划线分割单词，如A_TABLE, A_FIELD。
 * 
 * @author computer
 * 
 */
public enum UnderlineCamelConverter implements DbNamingConverter
{
	INSTANCE;
	/**
	 * 将下划线分割的命名转换为首字母小写的驼峰命名法。 如SOME_FIELD转换为someField。
	 */
	@Override
	public String columnName2JavaPropertyName(String columnName)
	{
		char[] chars = columnName.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == '_') continue;
			if (i > 0 && chars[i - 1] == '_')
			{
				sb.append(chars[i]);
			}
			else
			{
				sb.append(Character.toLowerCase(chars[i]));
			}
		}
		return sb.toString();
	}

	@Override
	public String javaPropertyName2ColumnName(String javaPropertyName)
	{
		char[] chars = javaPropertyName.toCharArray();

		StringBuilder sb = new StringBuilder();
		sb.append(Character.toUpperCase(chars[0]));
		for (int i = 1; i < chars.length; i++)
		{
			if (Character.isUpperCase(chars[i]))
			{
				sb.append("_");
			}
			sb.append(Character.toUpperCase(chars[i]));
		}

		return sb.toString();
	}

	/**
	 * 下划线分割的命名转换为首字母大写的驼峰命名法。 如SOME_TABLE转换为SomeTable
	 */
	@Override
	public String tableName2JavaTypeName(String tableName)
	{
		char[] chars = tableName.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == '_') continue;
			if (i == 0 || (i > 0 && chars[i - 1] == '_'))
			{
				sb.append(Character.toUpperCase(chars[i]));
			}
			else
			{
				sb.append(Character.toLowerCase(chars[i]));
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.younker.waf.db.naming.DbNamingConverter#className2TableName(java
	 * .lang.String)
	 */
	@Override
	public String javaTypeName2TableName(String className)
	{
		char[] chars = className.toCharArray();

		StringBuilder sb = new StringBuilder();
		sb.append(Character.toUpperCase(chars[0]));
		for (int i = 1; i < chars.length; i++)
		{
			if (Character.isUpperCase(chars[i]))
			{
				sb.append("_");
			}
			sb.append(Character.toUpperCase(chars[i]));
		}

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.younker.waf.db.naming.DbNamingConverter#sqlType2JavaTypeName(int)
	 */
	@Override
	public String sqlType2JavaTypeName(int type)
	{
		String typeName = "";
		switch (type)
		{
			case Types.BOOLEAN:
				typeName = "bool";

			case Types.INTEGER:
			case Types.TINYINT:
			case Types.SMALLINT:
				typeName = "int";
				break;

			case Types.BIGINT:
				typeName = "long";
				break;

			case Types.FLOAT:
				typeName = "float";
				break;

			case Types.DOUBLE:
				typeName = "double";
				break;

			case Types.CHAR:
			case Types.VARCHAR:
			case Types.NCHAR:
			case Types.NVARCHAR:
				typeName = "String";
				break;

			case Types.DATE:
				typeName = "Date";
				break;

			case Types.TIME:
				typeName = "Time";
				break;

			case Types.TIMESTAMP:
				typeName = "Timestamp";
				break;

			default:
		}
		return typeName;
	}

}
