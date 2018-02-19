package com.nbm.core.modeldriven.data.exception;

public class ModelNotRegisterException extends DataApiException
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private String modelName;
        
        
        public ModelNotRegisterException(String modelName )
        {
                super("根据名称找不到model");
                set("modelName", modelName);
        }
        
        public String getModelName()
        {
                return modelName;
        }

}
