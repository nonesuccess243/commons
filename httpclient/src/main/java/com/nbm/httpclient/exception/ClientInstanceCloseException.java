package com.nbm.httpclient.exception;

import com.nbm.exception.NbmBaseRuntimeException;


public class ClientInstanceCloseException extends NbmBaseRuntimeException
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        public ClientInstanceCloseException()
        {
                super("对接接口已经关闭,无法对接");
        }

}
