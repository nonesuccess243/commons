/***********************************************
 * Title:       MessageTag.java
 * Description: MessageTag.java
 * Author:      computer
 * Create Date: 2010-10-19
 * CopyRight:   CopyRight(c)@2009
 * Company:     TJUSCS
 * Version:     1.0
 ***********************************************
 */
package com.younker.waf.tag.message;

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.ResponseUtils;

/**
 * @author computer
 * 
 */
public class MessageTag extends TagSupport
{

        /**
         * 
         */
        private static final long serialVersionUID = -2564458933383789668L;

        private final static Logger log = LoggerFactory.getLogger(MessageTag.class);

        private String key;

        /**
         * @return the key
         */
        public String getKey()
        {
                return key;
        }

        /**
         * @param key
         *                the key to set
         */
        public void setKey(String key)
        {
                this.key = key;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
         */
        @Override
        public int doStartTag() throws JspException
        {
                String result;
                try
                {
                        result = new String(MessageConfig.getInstance().getString(key).getBytes("ISO-8859-1"), "GBK");
                } catch (UnsupportedEncodingException e)
                {
                        log.error("impossible error!!!! Set the result value to empty string", e);
                        result = "";
                }

                // String [] encodings = {"ISO-8859-1","GBK","UTF-8","GB2312"};
                // for( String e1 : encodings )
                // {
                // for( String e2 : encodings )
                // {
                // try
                // {
                // log.debug("new String(result.getBytes(" + e1 + "), " + e2 +
                // "):   " + new String(result.getBytes(e1), e2));
                //
                // } catch (UnsupportedEncodingException e)
                // {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }
                // }
                // }
                log.debug(result);
                ResponseUtils.write(pageContext, result);
                return SKIP_BODY;
        }
}
