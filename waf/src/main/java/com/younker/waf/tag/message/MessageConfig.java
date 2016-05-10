/***********************************************
 * Title:       MessageConfig.java
 * Description: MessageConfig.java
 * Create Date: 2010-11-8
 * CopyRight:   CopyRight(c)@2010
 * Company:     NBM
 ***********************************************
 */
package com.younker.waf.tag.message;

import java.io.IOException;
import java.net.URL;
import java.util.PropertyResourceBundle;

//import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 *
 */
public class MessageConfig
{
        // private Logger log = LoggerFactory.getLogger(MessageConfig.class);

        private final static MessageConfig _instance = new MessageConfig();

        private MessageConfig()
        {
        }

        public static MessageConfig getInstance()
        {
                return _instance;
        }

        private PropertyResourceBundle RESOURCE_BUNDLE;

        public void init(URL url) throws IOException
        {
                RESOURCE_BUNDLE = new PropertyResourceBundle(url.openStream());
        }

        public String getString(String key)
        {
                try
                {
                        return RESOURCE_BUNDLE.getString(key);
                } catch (Exception e)
                {
                        return "";
                }
        }
}
