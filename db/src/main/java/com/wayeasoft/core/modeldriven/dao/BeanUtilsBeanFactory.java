package com.wayeasoft.core.modeldriven.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.nbm.core.modeldriven.ComplexDbType;
import com.nbm.core.modeldriven.Field;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.exception.NbmBaseRuntimeException;

/**
 * 用于commondao在select数据后转为Java类的转换工具的生成过程
 * 
 * @author niyuzhe
 *
 */
public class BeanUtilsBeanFactory
{

        private static ConvertUtilsBean utils = new ConvertUtilsBean()
        {
                @Override
                public Object convert(String value, Class clazz)
                {
                        if (clazz.isEnum())
                        {
                                return Enum.valueOf(clazz, value);
                        } else
                        {
                                return super.convert(value, clazz);
                        }
                }
        };

        private static BeanUtilsBean beanUtils = new BeanUtilsBean(utils, new PropertyUtilsBean());


//        public static BeanUtilsBean get()
//        {
//                return beanUtils;
//        }
        
        
        /**
         * 代理beanUtils的populate行为
         * @param bean
         * @param properties
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         */
        public static void populate(Object bean, Map properties) throws IllegalAccessException, InvocationTargetException
        {
                if( PureModel.class.isAssignableFrom(bean.getClass()))
                {
                        registerModelClass(bean.getClass().asSubclass(PureModel.class));
                }
                beanUtils.populate(bean, properties);
        }

        private static Set<Class<? extends PureModel>> registered = new HashSet<>();

        /**
         * 判断某个Model类中的所有字段是否有ConplexDbType，如果有的话，注册converter
         * 
         * 会做缓存，如果第二次以上判断此类的话，会跳过判断过程直接返回。
         * 
         * @param modelClass
         */
        static void registerModelClass(Class<? extends PureModel> modelClass)
        {
                if (registered.contains(modelClass))
                {
                        return;
                }

                try
                {
                        for (Field fields : ModelMeta.getModelMeta(modelClass).getDbFields())
                        {
                                if (ComplexDbType.class.isAssignableFrom(fields.getType()))
                                {

                                        Class<? extends ComplexDbType> dbClass = fields.getType()
                                                        .asSubclass(ComplexDbType.class);

                                        utils.register(dbClass.newInstance().getConverter(), dbClass);
                                }
                        }

                } catch (InstantiationException | IllegalAccessException e)
                {
                        throw new NbmBaseRuntimeException("", e);
                }

                registered.add(modelClass);
        }

}
