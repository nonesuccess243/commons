/******************************************************************************
 * Title:     Younker Web Application Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:    Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

/**
 * Maverick默认采用的DateConverter，不支持yyyy-MM-dd格式，自定义专用转换器。
 * 
 * @see org.apache.commons.beanutils.converters.DateConverter
 * 
 */
public class DateConverter implements Converter
{
        private static final String DATE_FORMAT1 = "yyyy-MM-dd";
        private static final String DATE_FORMAT2 = "yyyy-MM-dd HH:mm:ss";
        static SimpleDateFormat df1 = new SimpleDateFormat(DATE_FORMAT1);
        static SimpleDateFormat df2 = new SimpleDateFormat(DATE_FORMAT2);

        public DateConverter()
        {
        }

        public Object convert(@SuppressWarnings("rawtypes") Class type, Object value)
        {
                if (value == null)
                        return null;
                if (value instanceof Date)
                        return value;
                if (value instanceof String)
                {
                        String tmp = ((String) value).trim();
                        if (tmp.length() == 0)
                                return null;
                        Date d = null;
                        try
                        {
                                if (tmp.length() == DATE_FORMAT1.length())
                                        d = df1.parse(tmp);
                                else if (tmp.length() == DATE_FORMAT2.length())
                                        d = df2.parse(tmp);
                                return d;
                        } catch (Exception ex)
                        {
                                throw new RuntimeException("The input date formate is not valid : " + value);
                        }
                } else
                {
                        throw new RuntimeException("The input date format is not type of Date or String"
                                        + value.getClass());
                }
        }
}
