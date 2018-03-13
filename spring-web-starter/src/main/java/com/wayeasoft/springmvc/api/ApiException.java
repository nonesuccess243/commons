package com.wayeasoft.springmvc.api;

import com.nbm.exception.NbmBaseException;

/**
 * 作为checked Exception，代表可被服务端处理的错误
 * 
 * 可被服务端处理的错误，一般视作客户端错误
 * 
 * @author niyuzhe
 *
 */
public abstract class ApiException extends NbmBaseException
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
                this.errorCode = errorCode;
                this.errorMessage = errorMessage;
        }

        public int getErrorCode()
        {
                return errorCode;
        }
}
