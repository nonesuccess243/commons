package com.wayeasoft.core.modeldriven.dao;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.h2.util.New;

import com.nbm.core.modeldriven.ComplexDbType;
import com.nbm.core.modeldriven.Field;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.exception.NbmBaseRuntimeException;

/**
 * 用于commondao在select数据后转为Java类的转换工具的生成过程
 * @author niyuzhe
 *
 */
public class BeanUtilsBeanFactory
{
        
        private static ConvertUtilsBean utils = new ConvertUtilsBean();
        
        private static BeanUtilsBean beanUtils = new BeanUtilsBean(utils,new PropertyUtilsBean()); 
        
        public static BeanUtilsBean get()
        {
                return beanUtils;
        }
        
        private static Set<Class<? extends PureModel>> registered = new HashSet<>();
        
        public static void registerConverter(Class<? extends PureModel> modelClass)
        {
                if( registered.contains(modelClass))
                {
                        return;
                }
                
                try
                {
                        for( Field fields : ModelMeta.getModelMeta(modelClass).getDbFields())
                        {
                                if( ComplexDbType.class.isAssignableFrom(fields.getType()))
                                {
                                        
                                        Class<? extends ComplexDbType> dbClass = fields.getType().asSubclass(ComplexDbType.class);
                                        
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
