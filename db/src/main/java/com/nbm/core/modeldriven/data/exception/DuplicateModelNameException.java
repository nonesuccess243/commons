package com.nbm.core.modeldriven.data.exception;

import com.nbm.core.modeldriven.PureModel;

/**
 * 在Java中，不同包下允许存在同名的类。
 * 
 * 但在modeldriven框架中，由于需要按照model类的名字获取model类的功能，因此硬性规定，所有Model必须全局统一名称。
 * @author niyuzhe
 *
 */
public class DuplicateModelNameException extends DataApiException
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        public DuplicateModelNameException( Class<? extends PureModel> modelToPut, Class<? extends PureModel> modelAlreadyPut)
        {
                super("扫描时发现重复model");
                
                set("modelToPut", modelToPut);
                set("modelAlreadyPut", modelAlreadyPut);
        }

}
