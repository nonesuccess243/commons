package com.nbm.core.modeldriven.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.data.exception.DuplicateModelNameException;
import com.nbm.core.modeldriven.data.exception.ModelNotRegisterException;
import com.nbm.exception.NbmBaseRuntimeException;

/**
 * 根据配置的包名扫描model类。
 * 
 * 包名由spring的@Value方式注入，名称为modelPackages，格式为字符串，逗号分隔，每一节表示一个包
 * 
 * 不明原因，包名注入方式必须打开@EnableWebMvc才能生效
 * @author niyuzhe
 *
 */
@Service
public class ModelRegister
{

        private final static Logger log = LoggerFactory.getLogger(ModelRegister.class);
        @Autowired
        @Value("#{'${commons.modeldriven.packages}'.split(',')}")
        private String[] modelPackages;

        private Map<String, ModelMeta> modelMap;

        public Class<? extends PureModel> get(String modelName) throws ModelNotRegisterException
        {

                if (modelMap == null)
                {
                        warmUp();
                }

                ModelMeta result = modelMap.get(modelName);

                if (result == null)
                {
                        throw new ModelNotRegisterException(modelName);
                }

                return result.getModelClass();
        }



        /**
         * 初始化modelmap
         */
        private void warmUp()
        {
                modelMap = new HashMap<>();


                Set<Class<? extends PureModel>> classes = PackageUtils.getClasses(Arrays.asList(modelPackages), PureModel.class);

                for (Class<? extends PureModel> c : classes)
                {
                        log.debug("发现model[{}]", c.getSimpleName());
                        
                        if( modelMap.containsKey(c.getSimpleName()))
                        {
                                throw new DuplicateModelNameException(c, modelMap.get(c.getSimpleName()).getModelClass());
                        }else
                        {
                                modelMap.put(c.getSimpleName(), ModelMeta.getModelMeta(c));
                        }
                }
        }
        
        /**
         * 在非spring环境下，手动初始化的方法
         * @param modelPackages
         */
        public void warmUp(String... modelPackages)
        {
                if( this.modelPackages != null )
                {
                        throw new NbmBaseRuntimeException("已经通过spring注入了modelPackages信息，不能手动初始化");
                }
                this.modelPackages = modelPackages;
                warmUp();
        }
        
        

        /**
         * 返回所有已注册model类的元信息
         * 
         * 由于不能将字段信息暴露，此信息只能在后台使用，不能直接转换为json数据打给前台
         * 
         * @return
         */
        public Collection<ModelMeta> getAllModel()
        {
                return modelMap.values();
        }

}
