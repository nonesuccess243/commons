/***********************************************
 * Title:       MessageConfigInit.java
 * Description: MessageConfigInit.java
 * Create Date: 2010-11-8
 * CopyRight:   CopyRight(c)@2010
 * Company:     NBM
 ***********************************************
 */
package com.younker.waf.tag.message;

import java.util.Map;

import javax.servlet.ServletContext;

import com.younker.waf.utils.config.ConfigurableWebUtil;

/**
 *
 */
public class MessageConfigInit implements ConfigurableWebUtil
{

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.younker.waf.utils.config.ConfigurableWebUtil#config(javax.servlet
         * .ServletConfig)
         */
        @Override
        public void config(ServletContext context, Map<String, String> paramMap) throws Exception
        {
                MessageConfig.getInstance().init(context.getResource("/WEB-INF/config/message.properties"));
        }
}
