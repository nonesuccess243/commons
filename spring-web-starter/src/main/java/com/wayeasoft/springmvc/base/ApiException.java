package com.wayeasoft.springmvc.base;

import com.nbm.exception.NbmBaseException;

/**
 * 作为checked Exception，代表可被服务端处理的错误
 * 
 * 可被服务端处理的错误，一般视作客户端错误
 * 
 * @author niyuzhe
 *
 */
public class ApiException extends NbmBaseException
{

        /**
         * 错误代码，会被resultbean直接使用
         */
        private int errorCode;
        
        /**
         * 
         * @param errorCoce 建议4开头
         */
        public ApiException( int errorCoce, String errorMessage)
        {
                this.errorCode = errorCode;
                
        }
}
