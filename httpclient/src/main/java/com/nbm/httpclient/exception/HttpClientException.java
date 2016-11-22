/***********************************************
 * Title:       HttpClientException.java
 * Description: HttpClientException.java
 * Create Date: 2012-9-6
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.exception;

import com.nbm.exception.NbmBaseRuntimeException;

/**
 *
 */
public class HttpClientException extends NbmBaseRuntimeException
{
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public HttpClientException()
        {
        }

        public HttpClientException(String message)
        {
                super(message);
        }
        
        public HttpClientException(String message, Throwable cause)
        {
                super(message, cause);
        }

}
