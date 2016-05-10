/***********************************************
 * Title:       PropertiesUtils.java
 * Description: PropertiesUtils.java
 * Create Date: 2013-7-24
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.waf.core.modeldriven;

import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 */
public enum ModelUtils
{
        INSTANCE;

        public void copyProperties(Object from, Object to, String[] properties)
        {
                for (String propertiesName : properties)
                {
                        Object value;
                        try
                        {
                                value = PropertyUtils.getProperty(from, propertiesName);
                        } catch (Exception e)
                        {
                                throw new RuntimeException("在[" + from + "]中获取属性值[" +propertiesName + "]发生错误", e);
                        }

                        try
                        {
                                PropertyUtils.setProperty(to, propertiesName, value);
                        } catch (Exception e)
                        {
                                throw new RuntimeException("在[" + to + "]中设置属性[" +propertiesName + "]为[" + value + "]值时发生错误", e);
                        }

                }
        }

}
