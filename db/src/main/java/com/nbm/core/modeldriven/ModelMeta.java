package com.nbm.core.modeldriven;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.nbm.commons.PackageUtils2;
import com.nbm.commons.db.meta.UnderlineCamelConverter;
import com.nbm.core.modeldriven.anno.DisplayName;
import com.nbm.core.modeldriven.data.PackageUtils;
import com.nbm.core.modeldriven.exception.ModelMetaInitException;
import com.nbm.exception.NbmBaseException;
import com.nbm.exception.NbmBaseRuntimeException;


/**
 * 用于表示实体类元信息的类
 * 
 * 默认会发现常用的包前缀，如果有不常用的，需要手动执行discover函数
 * @author niyuzhe
 *
 */
public class ModelMeta
{
        private final static Logger log = LoggerFactory.getLogger(ModelMeta.class);
        
        
        private final static Map<Class<? extends PureModel>, ModelMeta> CACHE = new HashMap<>();
        
        /**
         * 用不含包名的类名作为键的缓存类。
         * 
         * 由于有此缓存类存在，不支持同名Model，不论是否在一个包下。
         * 
         * 此限制应不会导致太严重的问题，因为所有的model都对应了一张数据库表，本身就不允许同名。
         */
        private final static Map<String, ModelMeta> CACHE_BY_SIMPLE = new HashMap<>();
        
        private final static Map<String, Class<? extends PureModel>> CLASS_BY_SIMPLE = new HashMap<>();
        
        
        
//        static
//        {
//                discover("com.nbm");
//                discover("com.wayeasoft");
//        }
//        
        /**
         * 发现packageName中的Model
         * 
         * 此函数的职责由ModelRegister取代
         * 
         * @see ModelRegister
         */
        @Deprecated
        public static void discover( String packageName )
        {
                Set<Class<? extends PureModel>> results = PackageUtils.getClasses(packageName, PureModel.class);
                
                
                for( Class<? extends PureModel> clas : results )
                {
                        CLASS_BY_SIMPLE.put(clas.getSimpleName(), clas);
                }
                
                Set<Class<? extends PureModel>> errorResults = new HashSet<>();;
                
                log.debug(results.toString());
                
                for( Class<? extends PureModel> c : results)
                {
                        log.debug("发现model[class={}, simpleName={}]", c, c.getSimpleName());
                        
                        ModelMeta result;
                        try
                        {
                                result = new ModelMeta(c);
                                log.debug("创建meta完成[class={}, simpleName={}]", c, c.getSimpleName());
                                
                                CACHE.put(c, result);
                                
                                log.debug(CACHE.toString());
                                
                                CACHE_BY_SIMPLE.put(c.getSimpleName(), result);
                                
                                log.debug(CACHE_BY_SIMPLE.toString());
                                
                        } catch (ModelMetaInitException e)
                        {
                                errorResults.add(c);
                        }
                        
                }
                
                int errorSize = errorResults.size();
                
                log.debug("经过初始处理，无法初始化的类为[{}]", errorResults);
                
                while( !errorResults.isEmpty() )
                {
                        for( Iterator<Class<? extends PureModel>> i = errorResults.iterator(); i.hasNext(); )
                        {
                                Class<? extends PureModel> c = i.next();
                                
                                ModelMeta result;
                                try
                                {
                                        result = new ModelMeta(c);
                                        log.debug("创建meta完成[class={}, simpleName={}]", c, c.getSimpleName());
                                        
                                        CACHE.put(c, result);
                                        
                                        log.debug(CACHE.toString());
                                        
                                        CACHE_BY_SIMPLE.put(c.getSimpleName(), result);
                                        
                                        log.debug(CACHE_BY_SIMPLE.toString());
                                        
                                        i.remove();
                                        
                                        log.debug("[{}]成功处理完成，从errorResults中删除。errorResuls剩余数量为：[{}]", c, errorResults.size());
                                        
                                } catch (ModelMetaInitException e)
                                {
                                        log.error("发生错误", e);
                                        log.debug("[{}]又一次发生错误，继续保留在errorResults中。errorResults剩余数量为：[{}]", c, errorResults.size());
                                }
                        }
                        if( errorSize != errorResults.size())
                        {
                                log.debug("本次循环前未处理的数目为[{}]，循环后减少为[{}]，仍剩余的为[{}]", errorSize, errorResults.size(), errorResults);
                                errorSize = errorResults.size();
                                continue;
                        }else
                        {
                                log.debug("经过一次完成循环，没有任何新的类被成功处理，循环退出，剩余未初始化class：{}", errorResults);
                                break;
                        }
                }
                
//                for( Class<? extends Model> c : PackageUtils2.getClassesByPackagenameAndGenericClass(packageName, Model.class))
//                {
//                        log.debug("发现model[class={}, simpleName={}]", c, c.getSimpleName());
//                        ModelMeta result = new ModelMeta(c);
//                        CACHE.put(c, result);
//                        CACHE_BY_SIMPLE.put(c.getSimpleName(), result);
//                }
                
        }
        
       
        
        public static ModelMeta getModelMeta(Class<? extends PureModel> modelClass)
        {
                ModelMeta result = CACHE.get(modelClass);
                if( result == null )
                {
                        try
                        {
                                result = new ModelMeta(modelClass);
                        } catch (ModelMetaInitException e)
                        {
                                throw new NbmBaseRuntimeException("初始化ModelMeta发生异常", e);
                        }
                        CACHE.put(modelClass, result);
                        CACHE_BY_SIMPLE.put(modelClass.getSimpleName(), result);
                }
                return result;
        }
        
        
        /**
         * 
         * @return 所有已知的model类的modelmeta信息
         */
        public static Collection<ModelMeta> getAllDiscoveredModelMeta()
        {
                return CACHE.values();
        }
        
        public static ModelMeta getModelMetaBySimpleName( String modelClassSimpleName )
        {
                ModelMeta result = CACHE_BY_SIMPLE.get(modelClassSimpleName);
                
                if( result == null )
                {
                        throw new NbmBaseRuntimeException("model不存在").set("simpleName", modelClassSimpleName);
                }
                return result;
        }
        
        private ModelMeta(Class<? extends PureModel> modelClass) throws ModelMetaInitException
        {
                super();
                
                log.debug("处理{}", modelClass);
                
                this.modelClass = modelClass;
                
                if(modelClass.getAnnotation(DisplayName.class)!=null)
                {
                        displayName = modelClass.getAnnotation(DisplayName.class).value();
                }
                
                fields = ModelUtils.INSTANCE.getFields(modelClass);
                
                log.debug("获取到fields:{}", fields);
                
                fieldMap = new HashMap<>(fields.size());
                
                fieldMapByDbName = new HashMap<>(fields.size());
                
                for( Field f: fields )
                {
                        fieldMap.put(f.getName(), f);
                        
                        fieldMapByDbName.put(f.getDbName(), f);
                }
                
                log.debug("fieldMap:{}", fieldMap);
                log.debug("fieldMapByDbName:{}", fieldMapByDbName);
                
                
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
        
        /**
         * 用display注解配置的字符串，一般用于配置显示用的中文名
         */
        private String displayName;

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

        public String getDisplayName()
        {
                return displayName;
        }
}
