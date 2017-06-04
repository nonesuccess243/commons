package com.nbm.core.modeldriven;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.nbm.commons.db.meta.UnderlineCamelConverter;


/**
 * 用于表示实体类元信息的类
 * @author niyuzhe
 *
 */
public class ModelMeta
{
        private final static Logger log = LoggerFactory.getLogger(ModelMeta.class);
        
        
        private final static Map<Class<? extends PureModel>, ModelMeta> CACHE = new HashMap<>();
        
        public static ModelMeta getModelMeta(Class<? extends PureModel> modelClass)
        {
                ModelMeta result = CACHE.get(modelClass);
                if( result == null )
                {
                        result = new ModelMeta(modelClass);
                        CACHE.put(modelClass, result);
                }
                return result;
        }
        
        private ModelMeta(Class<? extends PureModel> modelClass)
        {
                super();
                this.modelClass = modelClass;
                
                fields = ModelUtils.INSTANCE.getFields(modelClass);
                
                fieldMap = new HashMap<>(fields.size());
                
                fieldMapByDbName = new HashMap<>(fields.size());
                
                for( Field f: fields )
                {
                        fieldMap.put(f.getName(), f);
                        
                        fieldMapByDbName.put(f.getDbName(), f);
                }
                
                
                needImport = new HashSet<>();
                for( Field f : fields )
                {
                        try
                        {
                                if( f.getType().getPackage() != null && !f.getType().getPackage().getName().equals("java.lang"))
                                        needImport.add(f.getType());
                        } catch (Exception e)
                        {
                                log.error(f.toString(), e);
                        }
                        
                }
        }
        
        private Class<? extends PureModel> modelClass;

        private List<Field> fields;
        
        /**
         * 需要根据字段名称获取字段对象的方法，没时间与上面的fields字段合并了，因此新建一个字段。
         */
        private Map<String, Field> fieldMap;
        
        /**
         * 根据数据库命名查找Field对象的Map
         */
        private Map<String, Field> fieldMapByDbName;
        
        
        /**
         * 在生成java类时，需要生成import语句的class集合。
         */
        private Set<Class<?>> needImport; 
        
        public Class<? extends PureModel> getModelClass()
        {
                return modelClass;
        }

        public List<Field> getFields()
        {
                return fields;
        }
        
        public Field getPkField()
        {
                for( Field field: fields )
                {
                        if( field.isPk())
                                return field;
                }
                throw new RuntimeException("未定义主键[model=" + modelClass + "].");
        }

        public Collection<Field> getDbFields()
        {
                return Collections2.filter(fields, new Predicate<Field>()
                {
                        @Override
                        public boolean apply(Field field)
                        {
                                return !field.isDbIgnore();
                        }

                });
        }
        
        public Field getNameCol()
        {
                for( Field field: fields )
                {
                        if( field.isNameCol())
                                return field;
                }
                return null;
        }
        
        public Collection<Field> getDbFieldsExceptPk()
        {
                return Collections2.filter(getDbFields(), new Predicate<Field>()
                {
                        @Override
                        public boolean apply(Field field)
                        {
                                return !field.isPk();
                        }

                });
        }
        
        public String getDbTypeName()
        {
                if( modelClass.getAnnotation(com.nbm.core.modeldriven.anno.ModelInfo.class) != null )
                {
                        com.nbm.core.modeldriven.anno.ModelInfo anno = modelClass.getAnnotation(com.nbm.core.modeldriven.anno.ModelInfo.class);
                        if( StringUtils.isNotBlank(anno.tableName() ))
                        {
                                return anno.tableName();
                        }
                }
                return UnderlineCamelConverter.INSTANCE.javaTypeName2TableName(modelClass.getSimpleName());
        }

        public Set<Class<?>> getNeedImport()
        {
                return needImport;
        }

        
        /**
         * 根据字段名称获取字段对象
         * @param fieldName
         */
        public Field getField(String fieldName)
        {
                return fieldMap.get(fieldName);
                
        }
        
        public Field getFieldByDbName( String dbFieldName)
        {
                return fieldMapByDbName.get(dbFieldName);
                                
        }
}
