package com.wayeasoft.springmvc.api;

import com.nbm.exception.NbmBaseRuntimeException;

/**
 * @author niyuzhe
 *
 */
public abstract class ApiException extends NbmBaseRuntimeException
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * 错误代码，会被resultbean直接使用
         */
        private int errorCode;
        private String errorMessage;
        
        /**
         * 
         * @param errorCoce 建议4开头
         */
        public ApiException( int errorCode, String errorMessage)
        {
                super(errorMessage);
                this.errorCode = errorCode;
                this.errorMessage = errorMessage;
        }

        public int getErrorCode()
        {
                return errorCode;
        }

        public String getErrorMessage()
        {
                return errorMessage;
        }
}
