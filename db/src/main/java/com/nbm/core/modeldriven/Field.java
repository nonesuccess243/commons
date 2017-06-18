package com.nbm.core.modeldriven;

import java.lang.annotation.Annotation;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.commons.db.meta.DbNamingConverter;
import com.nbm.commons.db.meta.UnderlineCamelConverter;
import com.nbm.core.modeldriven.anno.DbIgnore;
import com.nbm.core.modeldriven.anno.DbTemplate;
import com.nbm.core.modeldriven.anno.DisplayName;
import com.nbm.core.modeldriven.anno.Fk;
import com.nbm.core.modeldriven.anno.Length;
import com.nbm.core.modeldriven.anno.MysqlDateTime;
import com.nbm.core.modeldriven.anno.NameCol;
import com.nbm.core.modeldriven.anno.NotNull;
import com.nbm.core.modeldriven.anno.Pk;
import com.nbm.core.modeldriven.enums.MySqlDateType;
import com.nbm.core.modeldriven.exception.ModelMetaInitException;
import com.nbm.core.modeldriven.generator.CrudGenerator;

public class Field
{
        
        private final static Logger log = LoggerFactory.getLogger(Field.class);
        
	private String name;

	private String dbName;
	
	private String displayName;

	private boolean pk;
	
	/**
	 * 该字段是否为外键
	 */
	private boolean fk;
	
	/**
	 * 当fk为true时，表示外键所指向的model类
	 */
	private Class<? extends PureModel> foreign;

	private boolean dbIgnore;

	private Class<?> type;

	private int minLength;
	private int maxLength;

	private MySqlDateType dateType;
	
	private DbTemplate dbTemplate;
	
	/**
	 * 如果此类的信息不够，子类可以根据此字段获取更多信息
	 */
	private java.lang.reflect.Field originField;

	public Field( java.lang.reflect.Field f ) throws ModelMetaInitException
	{
	        
	        this.originField = f;
	        
	        setType(f.getType());

                setName(f.getName());

                setDbName(UnderlineCamelConverter.INSTANCE
                                .javaPropertyName2ColumnName(f.getName()));
                
                if(f.getAnnotation(DisplayName.class)!=null)
                {
                        setDisplayName(f.getAnnotation(DisplayName.class).value());
                }

                if (f.getAnnotation(DbIgnore.class) != null)
                {
                        setDbIgnore(true);
                } else
                {
                        setDbIgnore(false);
                }

                if (f.getName().toString().equals("id")
                                || f.getAnnotation(Pk.class) != null)
                {
                        setPk(true);
                } else
                {
                        setPk(false);
                }
                
                log.debug("fieldname:{}, fk:{}", f.getName(),f.getAnnotation(Fk.class) );
                
                if( f.getAnnotation(Fk.class) != null )
                {
                        fk = true;
                        if( f.getAnnotation(Fk.class).foreign() != null )
                        {
                                foreign = f.getAnnotation(Fk.class).foreign();
                        }else
                        {
                                foreign = ModelMeta.getModelMetaBySimpleName(DbNamingConverter.DEFAULT_ONE.javaPropertyName2JavaTypeName(
                                                f.getName().substring(0, f.getName().length()-2))).getModelClass();
                        }
                       
                        
                }
                //暂时删掉这部分内容，计划将所有外键的识别都归结到fk注解上，避免代码混乱
//                else if( f.getName().toString().endsWith("Id"))
//                {
//                        fk = true;
//                        
//                        try
//                        {
//                                // 这里调用了ModelMeta类的函数，需要获取的是所指向的类的ModelMeta对象，但此时对应的类的ModelMeta对象可能仍未构建完成
//                                foreign = ModelMeta.getModelMetaBySimpleName(DbNamingConverter.DEFAULT_ONE.javaPropertyName2JavaTypeName(
//                                         f.getName().substring(0, f.getName().length()-2))).getModelClass();
//                        }catch(Exception exception )
//                        {
//                              throw new ModelMetaInitException(exception);
//                        }
//                        
//                }
                else
                {
                        fk = false;
                }
                
                if( f.getAnnotation(NameCol.class) != null)
                {
                        setNameCol(true);
                }

                if (f.getAnnotation(Length.class) != null)
                {
                    setMaxLength(f.getAnnotation(Length.class).max());
                    setMinLength(f.getAnnotation(Length.class).min());
                }
                
                Class<?> type = f.getType();
                if(type == Date.class && f.getAnnotation(MysqlDateTime.class) != null)
                {
                        setDateType(f.getAnnotation(MysqlDateTime.class).value());
                }
                
                if(f.getAnnotation(NotNull.class) != null )
                {
                        setNotNull(true);
                }
                
                dbTemplate = type.getAnnotation(DbTemplate.class);
                if( dbTemplate == null)
                {
                        dbTemplate = new DbTemplate()
                        {
                                
                                @Override
                                public Class<? extends Annotation> annotationType()
                                {
                                        return DbTemplate.class;
                                }
                                
                                @Override
                                public String populateSuffix()
                                {
                                        return "";
                                }
                                
                                @Override
                                public String populatePrefix()
                                {
                                        return "";
                                }
                                
                                @Override
                                public String fetchSuffix()
                                {
                                        return "";
                                }
                                
                                @Override
                                public String fetchPrefix()
                                {
                                        return "";
                                }
                        };
                }
	}
	
	
	/**
	 * 是否为名字列
	 */
	private boolean nameCol;
	
	
	/**
	 * 是否非空
	 */
	private boolean notNull;

	public String getName()
	{
		return name;
	}

	/**
	 * 获取首字母为大写的驼峰表示法名称
	 * 
	 * @return
	 */
	public String getNameFirstLetterUpper()
	{
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDbName()
	{
		return dbName;
	}

	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}

	
	private DbType dbType;
	
	/**
	 * 
	 * 
	 * 返回该Field对应的数据库类型
	 * 
	 * 其内容应对应数据库中的列类型
	 * 
	 * 初始目的是为了生成Mybatis的配置文件用
	 * @return
	 */
	public DbType getDbType()
	{
	        if( dbType == null )
		{
	                dbType = CrudGenerator.db.getDbTypeByJavaType(this);
		}
	        return dbType;
	}

	/**
         * 供freemarker配置文件调用
         * @return
         */
	public DbType getMybatisJdbcType()
	{
		DbType result = getDbType();
		if (result == DbType.VARCHAR2)
		{
			return DbType.VARCHAR;
		}
		else
		{
			return result;
		}
	}

	public String getDbTypeExtraInfo()
	{
		if (maxLength != 0)
		{
			return "(" + maxLength + ")";
		}
		else if (type == Long.class || type == Integer.class)
		{
			return "(11)";
		}
		else if (type == String.class || type.isEnum() || type == boolean.class
				|| type == Boolean.class)
		{
			return "(200)";
		}
		else return "";
	}

	public boolean isPk()
	{
		return pk;
	}

	private void setPk(boolean pk)
	{
		this.pk = pk;
	}

	public boolean isDbIgnore()
	{
		return dbIgnore;
	}

	public void setDbIgnore(boolean dbIgnore)
	{
		this.dbIgnore = dbIgnore;
	}

	public void setType(Class<?> type)
	{
		this.type = type;
	}

	public Class<?> getType()
	{
		return type;
	}

	public Class<?> getBoxingType()
	{
		if (type == int.class)
		{
			return Integer.class;
		}
		else if (type == long.class)
		{
			return Long.class;
		}
		else if (type == boolean.class)
		{
			return Boolean.class;
		}
		else if (type == float.class)
		{
			return Float.class;
		}
		else if (type == double.class)
		{
			return Double.class;
		}
		return type;
	}

	public int getMinLength()
	{
		return minLength;
	}

	public void setMinLength(int minLength)
	{
		this.minLength = minLength;
	}

	public int getMaxLength()
	{
		return maxLength;
	}

	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public MySqlDateType getDateType()
	{
		return dateType;
	}

	public void setDateType(MySqlDateType dateType)
	{
		this.dateType = dateType;
	}

        public boolean isNameCol()
        {
                return nameCol;
        }

        public void setNameCol(boolean nameCol)
        {
                this.nameCol = nameCol;
        }

        public boolean isNotNull()
        {
                return notNull;
        }

        public void setNotNull(boolean notNull)
        {
                this.notNull = notNull;
        }

        public String getDisplayName()
        {
                return displayName;
        }

        public void setDisplayName(String displayName)
        {
                this.displayName = displayName;
        }

        public java.lang.reflect.Field getOriginField()
        {
                return originField;
        }

        public DbTemplate getDbTemplate()
        {
                return dbTemplate;
        }

        public boolean isFk()
        {
                return fk;
        }

        public Class<? extends PureModel> getForeign()
        {
                return foreign;
        }
}
