package com.wayeasoft.modeldriven.exception;

import com.nbm.exception.NbmBaseRuntimeException;

public class DataApiException extends NbmBaseRuntimeException
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        public DataApiException() {}
        
        public DataApiException( String errorMessage)
        {
                super(errorMessage);
        }

}
