package com.younker.waf.dbgrid;

import static com.younker.waf.dbgrid.DBGridToken.DATE_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.FORMAT_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.NUMERIC_TOKEN;

import com.younker.waf.utils.WebUtil;

/**
 * 抽取DBGrid类中的静态函数
 * 
 * @author computer
 * 
 */
public class DBGridUtil
{
        /**
         * 返回参数字符串中，第一个$之前的部分
         */
        public static String getLabel(String column)
        {
                if (column == null || column.equals(""))
                {
                        return "";
                } else
                {
                        return WebUtil.split(column, FORMAT_TOKEN)[0];
                }
        }

        public static int getDateFormat(String column)
        {
                try
                {
                        if (column == null || column.equals(""))
                        {
                                byte byte0 = -1;
                                return byte0;
                        }
                        if (column.toLowerCase().indexOf(DATE_TOKEN) == -1)
                        {
                                byte byte1 = -1;
                                return byte1;
                        } else
                        {
                                int i = Integer.parseInt(column.substring(1));
                                return i;
                        }
                } catch (NumberFormatException ex)
                {
                        byte byte2 = -1;
                        return byte2;
                }
        }

        public static int getNumberFormat(String column)
        {
                try
                {
                        if (column == null || column.equals(""))
                        {
                                byte byte0 = -1;
                                return byte0;
                        }
                        if (column.toLowerCase().indexOf(NUMERIC_TOKEN) == -1)
                        {
                                byte byte1 = -1;
                                return byte1;
                        } else
                        {
                                int i = Integer.parseInt(column.substring(1));
                                return i;
                        }
                } catch (NumberFormatException ex)
                {
                        byte byte2 = -1;
                        return byte2;
                }
        }

        public static String getDataFormat(String column)
        {
                String strTemp[] = WebUtil.split(column, FORMAT_TOKEN);
                if (strTemp.length > 1)
                {
                        return strTemp[1];
                } else
                {
                        return "";
                }
        }

}
