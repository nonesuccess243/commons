/***********************************************
 * Title:       ServerInternalErrorException.java
 * Description: ServerInternalErrorException.java
 * Create Date: 2013年11月13日
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.exception;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 *
 */
public class ServerInternalErrorException extends HttpClientException
{
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private String result;
        private String host;
        private String path;
        private String encode;
        private List<NameValuePair> paramGet;
        private List<NameValuePair> paramPost;
        
        private int errorCode;

        /**
         * @param result
         * @param host
         * @param path
         * @param encode
         * @param paramGet
         * @param paramPost
         */
        public ServerInternalErrorException(int errorCode, String result, String host, String path, String encode,
                        List<NameValuePair> paramGet, List<NameValuePair> paramPost)
        {
                super("对接系统发生内部错误");
                this.result = result;
                this.host = host;
                this.path = path;
                this.encode = encode;
                this.paramGet = paramGet;
                this.paramPost = paramPost;
                this.errorCode = errorCode;

                this.set("result", result).set("host", host).set("path", path).set("encode", encode)
                                .set("paramGet", paramGet).set("paramPost", paramPost);
        }

        /**
         * @return the result
         */
        public String getResult()
        {
                return result;
        }
        
        public void setResult( String result )
        {
                this.result = result;
                this.set("result", result);
        }

        /**
         * @return the host
         */
        public String getHost()
        {
                return host;
        }

        /**
         * @return the path
         */
        public String getPath()
        {
                return path;
        }

        /**
         * @return the encode
         */
        public String getEncode()
        {
                return encode;
        }

        /**
         * @return the paramGet
         */
        public List<NameValuePair> getParamGet()
        {
                return paramGet;
        }

        /**
         * @return the paramPost
         */
        public List<NameValuePair> getParamPost()
        {
                return paramPost;
        }
        
        public int getErrorCode()
        {
                return errorCode;
        }


}
