package com.nbm.core.modeldriven;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.commons.PackageUtils;
import com.nbm.core.modeldriven.generator.CrudGenerator;

/**
 * 提供常见数据库的差异化方言细节
 * 
 * 并提供generateAll函数
 * @author niyuzhe
 *
 */
public enum Db
{
        ORACLE
        {
                @Override
                public DbType getDbTypeByJavaType(Field field)
                {
                        Class<?> type = field.getType();
                        if (type == Long.class || type == Integer.class || type == int.class || type == long.class)
                        {
                                return DbType.DECIMAL;
                        } else if (type == String.class || type == StringBuilder.class || type == StringBuffer.class)
                        {
                                return DbType.VARCHAR2;
                        } else if (type == Date.class)
                        {
                                return DbType.TIMESTAMP;
                        } else if (type == boolean.class || type == Boolean.class)
                        {
                                return DbType.VARCHAR2;
                        } else if (type.isEnum())
                        {
                                return DbType.VARCHAR2;
                        } else
                        {
                                throw new RuntimeException("无法获取有效的DbType[propertyDescripter=" + type + "].");
                        }
                }

        },
        MYSQL
        {
                @Override
                public DbType getDbTypeByJavaType(Field field)
                {
                        Class<?> type = field.getType();

                        if (field.isPk())
                        {
                                return DbType.BIGINT;
                        } else if (type == Long.class || type == long.class)
                        {
                                return DbType.BIGINT;
                        }
                        if (type == Integer.class || type == int.class)
                        {
                                return DbType.BIGINT;
                        } else if (type == String.class || type == StringBuilder.class || type == StringBuffer.class)
                        {
                                return DbType.VARCHAR;
                        } else if (type == Date.class)
                        {
                                if (field.getDateType() != null)
                                {
                                        return field.getDateType().getDateTypeName();
                                }
                                return DbType.DATE;
                        } else if (type == boolean.class || type == Boolean.class)
                        {
                                return DbType.VARCHAR;
                        } else if (type == float.class || type == Float.class)
                        {
                                return DbType.FLOAT;
                        } else if (type == double.class || type == Double.class)
                        {
                                return DbType.DOUBLE;
                        } else if (type == BigDecimal.class)
                        {
                                return DbType.DECIMAL;
                        }else if (type.isEnum())
                        {
                                return DbType.VARCHAR;
                        } else
                        {
                                throw new RuntimeException("无法获取有效的DbType[propertyDescripter=" + type + "].");
                        }
                }

        };

        private final static Logger log = LoggerFactory.getLogger(Db.class);

        public abstract DbType getDbTypeByJavaType(Field field);

//        public abstract String getDbTypeByExtraInfoIsEmpty(YesOrNo modelInfoIsEmpty);

        
        
        /**
         * 如果文件名中不含有ignore_db串，则直接返回
         * 
         * 如果包含，则返回db_加fileName
         * 
         * 什么用意？忘了
         * 
         * 
         * @param fileName
         * @return
         */
        public String getFtlFileName(String fileName)
        {
                if (fileName.indexOf("ignore_db") != -1)
                {
                        return fileName;
                } else
                {
                        return this.toString().toLowerCase() + "_" + fileName;
                }
        }

        public CrudGenerator generate(Class<? extends PureModel> modelClass)
        {
                try
                {
                        Db temp = CrudGenerator.db;
                        CrudGenerator.db = this;
                        CrudGenerator generator = new CrudGenerator(modelClass);
                        generator.generate();
                        CrudGenerator.db = temp;
                        
                        return generator;
                } catch (Exception e)
                {
                        throw new RuntimeException(e);
                }
        }

        
        /**
         * 生成所有packageName包下的可生成的model类
         * @param packageName
         */
        public void generateAll(String packageName)
        {

                Db temp = CrudGenerator.db;
                CrudGenerator.db = this;

                StringBuilder mybatisConfig = new StringBuilder("\n");

                for (Class<?> modelClass : PackageUtils.getClasses(packageName))
                {
                        // log.info("start class[{}]", className.getName());
                        if (PureModel.class.isAssignableFrom(modelClass) && !modelClass.isAnonymousClass())
                        {
                                log.info("find model class[{}]", modelClass.getName());
                                try
                                {
                                        new CrudGenerator(modelClass.asSubclass(PureModel.class)).generate();

                                        log.info("finish model class[{}]", modelClass.getName());
                                        mybatisConfig.append("<mapper resource=\"" + modelClass.getName().replace(
                                                        modelClass.getSimpleName(), "dao").replaceAll("\\.", "/")
                                                        + "/" + modelClass.getSimpleName() + "Mapper.xml\"" + "/>\n");

                                } catch (Exception e)
                                {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                        } else
                        {
                                // log.info("not model class");

                        }

                }
                
                log.debug(mybatisConfig.toString());
                
                CrudGenerator.db = temp;
        }

        public static void main(String[] args)
        {
                Db.ORACLE.generateAll("com.nbm");
        }

}
