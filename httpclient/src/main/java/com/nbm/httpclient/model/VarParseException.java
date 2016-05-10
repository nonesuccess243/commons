/***********************************************
 * Title:       VarParseException.java
 * Description: VarParseException.java
 * Create Date: 2013-2-2
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.model;

import com.nbm.httpclient.exception.HttpClientException;

/**
 *
 */
public class VarParseException extends HttpClientException
{
        

        /**
         * 
         */
        private static final long serialVersionUID = 7182633425735072036L;

        public VarParseException( String pattern, String context )
        {
                this.errorMessage = "正则表达式解析变量发生错误";
                this.set("pattern", pattern);
                this.set("context", context);
        }

}
