/******************************************************************************
 * Title:     Younker Web Application Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:    Xiao Jian
 * Version:   2.0
 *****************************************************************************/

package com.younker.waf.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtil
{

        private static final String dateSeperator = "-";

        private static final long MS_PER_SECOND = 1000;
        private static final long MS_PER_MINUTE = MS_PER_SECOND * 60;
        private static final long MS_PER_HOUR = MS_PER_MINUTE * 60;
        private static final long MS_PER_DAY = MS_PER_HOUR * 24;

        // added by xiaojian for getting string-format date
        private static final String DATE_FORMAT1 = "yyyy-MM-dd";
        private static final String DATE_FORMAT2 = "yyyy-MM-dd HH:mm:ss";
        static SimpleDateFormat df1 = new SimpleDateFormat(DATE_FORMAT1);
        static SimpleDateFormat df2 = new SimpleDateFormat(DATE_FORMAT2);

        public static final int MS = 0;
        public static final int SECOND = 1;
        public static final int MINUTE = 2;
        public static final int HOUR = 3;
        public static final int DAY = 4;
        public static final String NULL_VALUE = "";

        private final static Logger log = LoggerFactory.getLogger(WebUtil.class);

        public WebUtil()
        {

        }

        /**
         * 编码转换
         * 
         * @param strIn
         *                输入字符串
         * @return 转换后的字符串
         */
        public static String UnicodeToGB(String strIn)
        {
                String strOut = null;
                if (strIn == null || strIn.trim().equals(""))
                {
                        return strIn;
                }
                try
                {
                        byte b[] = strIn.getBytes("ISO8859_1");
                        strOut = new String(b, "GBK");
                } catch (UnsupportedEncodingException ex)
                {
                        strOut = strIn;
                        // throw new
                        // RuntimeException("failed converting UNICODE to GBK",ex);
                }
                return strOut;

        }

        /**
         * 编码转换
         * 
         * @param strIn
         *                输入字符串
         * @return 转换后的字符串
         */
        public static String GBToUnicode(String strIn)
        {
                String strOut = null;
                if (strIn == null || strIn.trim().equals(""))
                {
                        return strIn;
                }
                try
                {
                        byte b[] = strIn.getBytes("GBK");
                        strOut = new String(b, "ISO8859_1");
                } catch (UnsupportedEncodingException ex)
                {
                        strOut = strIn;
                }
                return strOut;
        }

        /**
         * 判断一字符串是否为空，如果为空，则返回指定的字符串，如果非空，则取消字符串两边的空格。
         * 
         * @param inStr
         *                需要判断的字符串
         * @param returnStr
         *                如果inStr为null,需要返回的值
         * @return 判断后的字符串
         */
        public static String isNull(String inStr, String returnStr)
        {

                if (inStr == null || inStr.equals(""))
                {
                        String s = returnStr;
                        return s;
                } else
                {
                        String s1 = inStr.trim();
                        return s1;
                }

        }

        /**
         * 将String形式的日期表示（格式：YYYY-MM-DD '-'为任意单字符分隔符号）转化成Date形式的日期表示
         * 
         * @param strExp
         *                日期字符串
         * @param d
         *                如果转换出错，返回的缺省日期
         * @return 转换后的日期，若转换中出错，返回第二个参数指明的缺省日期
         */
        public static Date getDate(String strExp, Date d)
        {
                Date ud = null;
                if (strExp.length() != 10)
                {
                        return d;
                }

                try
                {
                        Calendar cal = new GregorianCalendar();
                        cal.setLenient(false);
                        cal.set(Integer.parseInt(strExp.substring(0, 4)), Integer.parseInt(strExp.substring(5, 7)) - 1,
                                        Integer.parseInt(strExp.substring(8, 10)));
                        ud = cal.getTime();
                } catch (Exception e)
                {
                        ud = d;
                }
                return ud;
        }

        /**
         * TODO 说明方法用途
         * 
         * @param inString
         * @return
         */
        public static Timestamp getTimestamp(String inString)
        {
                /*
                 * String tempStr = ""; String theYear = ""; String theMonth =
                 * ""; String theDay = "";
                 */
                if (inString == null || inString.equals(""))
                {
                        return null;
                }

                Timestamp timestamp = Timestamp.valueOf(inString.trim());
                return timestamp;

        }

        /**
         * 判断一字符串是否为空，如果为空，则返回""，如果非空，则取消字符串两边的空格
         * 
         * @param strChk
         *                输入字符串
         * @return 返回字符串
         */
        public static String nvl(String strChk)
        {
                return nvl(strChk, "");
        }

        /**
         * 判断一字符串是否为空或是空串，如果为空或是空串，则返回指定的字符串，如果非空，则取消字符串两边的空格并返回。
         * 
         * 如参数为abc和bcd，则返回abc。如参数为null和abc，则返回abc
         * 
         * @param strChk
         *                需要判断的字符串
         * @param strExp
         *                如果inStr为null,需要返回的值
         * @return 判断后的字符串
         */

        public static String nvl(String strChk, String strExp)
        {
                if (isNull(strChk))
                {
                        return strExp;
                } else
                {
                        return strChk.trim();
                }
        }

        /**
         * 判断一字符串是否为空，如果为空，则返回true，否则返回false
         * 
         * @param strExp
         *                输入字符串
         * @return boolean
         */
        public static boolean isNull(String strExp)
        {
                return strExp == null || strExp.length() == 0;
        }

        /**
         * 判断字符串是否符合日期格式，如2011-05-08，‘-’可以是任意的单个分割符
         * 
         * @param strExp
         * @return boolean
         */
        public static boolean isDate(String strExp)
        {
                if (strExp.length() != 10)
                {
                        return false;
                }
                boolean flag;
                try
                {
                        Calendar cal = new GregorianCalendar();
                        cal.setLenient(false);
                        cal.set(Integer.parseInt(strExp.substring(0, 4)), Integer.parseInt(strExp.substring(5, 7)) - 1,
                                        Integer.parseInt(strExp.substring(8, 10)));
                        Date ud = cal.getTime();
                        boolean flag1 = true;
                        return flag1;
                } catch (Exception e)
                {
                        flag = false;
                }
                return flag;
        }

        /**
         * TODO 说明方法用途
         * 
         * @param intYear
         * @param intMonth
         * @param intDay
         * @return
         */
        public static boolean isDate(int intYear, int intMonth, int intDay)
        {
                boolean flag;
                try
                {
                        Calendar cal = new GregorianCalendar();
                        cal.setLenient(false);
                        cal.set(intYear, intMonth, intDay);
                        Date ud = cal.getTime();
                        boolean flag1 = true;
                        return flag1;
                } catch (Exception e)
                {
                        flag = false;
                }
                return flag;
        }

        /**
         * 判断字符串是否可以转换成Float类型，并且位数小于等于第二个参数
         * 
         * @param strExp
         * @param intWidth
         * @return
         */
        public static boolean isFloat(String strExp, int intWidth)
        {
                if (isNull(strExp) || intWidth <= 0 || strExp.length() > intWidth)
                {
                        return false;
                } else
                {
                        return isFloat(strExp);
                }
        }

        /**
         * 判断字符串是否可以转换成Float类型，并且转换后的值整数位数小于等于intWidth 小数位数小于等于intZero
         * 
         * @param strExp
         *                输入字符串
         * @param intWidth
         *                整数位数
         * @param intZero
         *                小数位数
         * @return
         */
        public static boolean isFloat(String strExp, int intWidth, int intZero)
        {
                if (isFloat(strExp))
                {
                        int i = strExp.indexOf(".");
                        if (i < 0)
                        {
                                return strExp.length() <= intWidth;
                        }
                        return strExp.length() - i - 1 <= intZero;
                } else
                {
                        return false;
                }
        }

        /**
         * 判断字符串是否可以转换为Float类型
         * 
         * @param strExp
         * @return
         */
        public static boolean isFloat(String strExp)
        {
                if (isNull(strExp))
                {
                        return false;
                }
                try
                {
                        Float.valueOf(strExp);
                        boolean flag = true;
                        return flag;
                } catch (NumberFormatException e)
                {
                        boolean flag1 = false;
                        return flag1;
                }
        }

        /**
         * 判断一字符串是否为整型数据，并且位数小于等于intWidth
         * 
         * @param strExp
         *                传入的字符串
         * @param intWidth
         *                整型位数比较的值
         * @return
         */
        public static boolean isInteger(String strExp, int intWidth)
        {
                if (isNull(strExp) || intWidth <= 0 || strExp.length() > intWidth)
                {
                        return false;
                } else
                {
                        return isInteger(strExp);
                }
        }

        /**
         * 判断一字符串是否为整型数据，如果是，则返回true，否则返回false <LI>isFloat() <LI>isNumberic()
         * <LI>isEmail() <LI>isDate()
         * 
         * @param strExp
         *                需要判断的字符串
         * @return boolean
         */
        public static boolean isInteger(String strExp)
        {
                if (isNull(strExp))
                {
                        return false;
                }
                try
                {
                        Integer.valueOf(strExp);
                        boolean flag = true;
                        return flag;
                } catch (NumberFormatException e)
                {
                        boolean flag1 = false;
                        return flag1;
                }
        }

        /**
         * 判断一字符串是否为数字型，如果是，返回true，否则返回false
         * 
         * @param strExp
         * @return
         */
        public static boolean isNumeric(String strExp)
        {
                return isFloat(strExp) || isInteger(strExp);
        }

        /**
         * 判断一字符串是否符合邮件格式，包含@符号不行不是最后一位。 符合则返回true，否则返回false
         * 
         * @param strExp
         * @return
         */
        public static boolean isEmail(String strExp)
        {
                int intFind = strExp.indexOf("@");
                return intFind > 0 && intFind < strExp.length() - 1;
        }

        /**
         * 获取系统当前时间
         * 
         * @return 格式为yyyy-mm-dd hh:mm:ss
         */
        public static String getNow()
        {
                GregorianCalendar gcdNow = new GregorianCalendar();
                String strYear = formatNumber(Integer.toString(gcdNow.get(Calendar.YEAR)), 4);
                String strMonth = formatNumber(Integer.toString(gcdNow.get(Calendar.MONTH) + 1), 2);
                String strDay = formatNumber(Integer.toString(gcdNow.get(Calendar.DAY_OF_MONTH)), 2);
                String strHour = formatNumber(Integer.toString(gcdNow.get(Calendar.HOUR_OF_DAY)), 2);
                String strMinute = formatNumber(Integer.toString(gcdNow.get(Calendar.MINUTE)), 2);
                String strSecond = formatNumber(Integer.toString(gcdNow.get(Calendar.SECOND)), 2);
                String strNow = "";
                strNow = String.valueOf(String.valueOf(strYear)) + dateSeperator + strMonth + dateSeperator + strDay
                                + " " + strHour + ":" + strMinute + ":" + strSecond;
                return strNow;
        }

        /**
         * 获取系统当前时间
         * 
         * @param seperator
         *                日期分隔符
         * @return 格式为yyyy-mm-dd hh:mm:ss '-'为参数指明的分隔符
         */
        public static String getNow(String seperator)
        {
                GregorianCalendar gcdNow = new GregorianCalendar();
                String strYear = formatNumber(Integer.toString(gcdNow.get(Calendar.YEAR)), 4);
                String strMonth = formatNumber(Integer.toString(gcdNow.get(Calendar.MONTH) + 1), 2);
                String strDay = formatNumber(Integer.toString(gcdNow.get(Calendar.DAY_OF_MONTH)), 2);
                String strHour = formatNumber(Integer.toString(gcdNow.get(Calendar.HOUR_OF_DAY)), 2);
                String strMinute = formatNumber(Integer.toString(gcdNow.get(Calendar.MINUTE)), 2);
                String strSecond = formatNumber(Integer.toString(gcdNow.get(Calendar.SECOND)), 2);
                String strNow = "";
                strNow = String.valueOf(String.valueOf(strYear)) + seperator + strMonth + seperator + strDay + " "
                                + strHour + ":" + strMinute + ":" + strSecond;
                return strNow;
        }

        /**
         * 获取系统当前日期
         * 
         * @return 格式为yyyy-mm-dd
         */
        public static String getCurrentDate()
        {
                return getNow().substring(0, 10);
        }

        /**
         * 获取系统当前日期，采用指定的分隔符
         * 
         * @param seperator
         *                日期分隔符
         * @return
         */
        public static String getCurrentDate(String seperator)
        {
                return getNow(seperator).substring(0, 10);
        }

        /**
         * 获取系统当前年份
         * 
         * @return
         */
        public static String getCurrentYear()
        {
                return getNow().substring(0, 4);
        }

        /**
         * 格式化有序号或编号意义的字符串显示<br>
         * 使用情景：数据表的序号，要求n位序号且系统自动管理 <LI>0000001 <LI>... <LI>0012345 <LI>...
         * 
         * @param strNumber
         *                原始字符串
         * @param iLength
         *                格式化后的长度
         * @return 格式化后的字符串
         */
        public static String formatNumber(String strNumber, int iLength)
        {
                if (!isNumeric(strNumber))
                {
                        return strNumber;
                }
                if (strNumber.length() >= iLength)
                {
                        return strNumber;
                }
                String strZero = "";
                for (int i = 0; i < iLength - strNumber.length(); i++)
                {
                        strZero = String.valueOf(String.valueOf(strZero)).concat("0");
                }

                return String.valueOf(strZero) + String.valueOf(strNumber);
        }

        /**
         * 格式化数字类型变量，统一小数位数的显示格式<br>
         * 使用情景：要求所有数字保留n位小数
         * 
         * @param in
         *                原始数值
         * @param iLength
         *                格式化后小数位的精度
         * @return 格式化后的数字以字符串形式输出
         */
        public static String formatNumber(double in, int iLength)
        {
                String formatStr = "0.";
                for (int i = 0; i < iLength; i++)
                {
                        formatStr += "0";
                }
                DecimalFormat df = new DecimalFormat(formatStr);
                return df.format(in);
        }

        /**
         * 将Date类型的数据格式化为 "yyyy-DD-mm"
         */
        public static String formatDate(Date d)
        {
                if (d == null)
                {
                        return NULL_VALUE;
                }
                return df1.format(d);
        }

        /**
         * 将Date类型的数据格式化为yyyy-MM-dd HH:mm:ss
         */
        public static String formatDate2(Date d)
        {
                if (d == null)
                {
                        return NULL_VALUE;
                }
                return df2.format(d);
        }

        /**
         * 在str中将str1换为str2
         * 
         * @param str
         *                原始字符串
         * @param str1
         *                被替换的子字符串
         * @param str2
         *                替换子字符串
         * @return 替换后的str
         */
        public static String replace(String str, String str1, String str2)
        {
                String newstr = "";

                for (int intPos = str.indexOf(str1); intPos != -1; intPos = str.indexOf(str1))
                {
                        newstr = newstr + str.substring(0, intPos) + str2;
                        str = str.substring(intPos + str1.length());
                }

                newstr = String.valueOf(newstr) + String.valueOf(str);
                return newstr;
        }

        /**
         * 将str1以str2为分割符号，分解成相应的数组
         * 
         * @param str1
         *                原始字符串
         * @param str2
         *                输入字符串
         * @return 分割后的字符串数组
         */
        public static String[] split(String str1, String str2)
        {
                StringTokenizer st = new StringTokenizer(str1, str2);
                int count = st.countTokens();
                String array[] = new String[count];
                for (int i = 0; st.hasMoreTokens(); i++)
                {
                        array[i] = st.nextToken();
                }

                return array;
        }

        /**
         * 在原数组上对所有字符串进行trim操作
         */
        public static void trimStrArray(String[] strs)
        {
                for (int i = 0; i < strs.length; i++)
                {
                        strs[i] = strs[i].trim();
                }
        }

        public static long getLongValue(String str)
        {
                try
                {
                        if (str == null || str.equals(""))
                        {
                                long l = -1L;
                                return l;
                        } else
                        {
                                long l1 = Long.parseLong(str.trim());
                                return l1;
                        }
                } catch (Exception ex)
                {
                        long l2 = -1L;
                        return l2;
                }
        }

        public static double getDoubleValue(String str)
        {
                try
                {
                        if (str == null || str.equals(""))
                        {
                                double d = 0.0D;
                                return d;
                        } else
                        {
                                double d1 = Double.parseDouble(str.trim());
                                return d1;
                        }
                } catch (Exception ex)
                {
                        double d2 = 0.0D;
                        return d2;
                }
        }

        /**
         * 将str转换成int数据类型，如果str为无效数据，将返回0<br>
         * 类似的函数还有： <LI>getDoubleValue() <LI>getLongValue() <LI>getFloatValue()
         * 
         * @param str
         *                输入字符串
         * @return 转换后的整形封装器类
         */
        public static int getIntValue(String str)
        {
                try
                {
                        if (str == null || str.equals(""))
                        {
                                int i = 0;
                                return i;
                        } else
                        {
                                int j = Integer.parseInt(str.trim());
                                return j;
                        }
                } catch (Exception ex)
                {
                        int k = 0;
                        return k;
                }
        }

        /**
         * 将str转换成Integer数据类型，如果str为无效数据，将返回null<br>
         * 类似的函数还有： <LI>getDouble() <LI>getLong() <LI>getFloat() <LI>
         * getBigDecimal() <LI>getTimestamp()
         * 
         * @param str
         *                输入字符串
         * @return 转换后的整数
         */

        public static Integer getInteger(String str)
        {
                try
                {
                        if (str == null || str.equals(""))
                        {
                                Integer integer = null;
                                return integer;
                        } else
                        {
                                Integer integer1 = new Integer(str.trim());
                                return integer1;
                        }
                } catch (Exception ex)
                {
                        Integer integer2 = null;
                        return integer2;
                }
        }

        public static BigDecimal getBigDecimal(String str)
        {
                try
                {
                        if (str == null || str.equals(""))
                        {
                                BigDecimal bigdecimal = null;
                                return bigdecimal;
                        } else
                        {
                                BigDecimal bigdecimal1 = new BigDecimal(str.trim());
                                return bigdecimal1;
                        }
                } catch (Exception ex)
                {
                        BigDecimal bigdecimal2 = null;
                        return bigdecimal2;
                }
        }

        public static Double getDouble(String str)
        {
                try
                {
                        if (str == null || str.equals(""))
                        {
                                Double double1 = null;
                                return double1;
                        } else
                        {
                                Double double2 = Double.valueOf(str.trim());
                                return double2;
                        }
                } catch (Exception ex)
                {
                        Double double3 = null;
                        return double3;
                }
        }

        /**
         * 解析字符串得到Long 存在的必要性就是隐藏异常？？
         * 
         * @param str
         * @return
         */
        public static Long getLong(String str)
        {
                try
                {
                        if (str == null || str.equals(""))
                        {
                                Long long1 = null;
                                return long1;
                        } else
                        {
                                Long long2 = Long.valueOf(str.trim());
                                return long2;
                        }
                } catch (Exception ex)
                {
                        Long long3 = null;
                        return long3;
                }
        }

        public static long compareDate(Date s, Date d)
        {
                return (s.getTime() - d.getTime());
        }

        /**
         * 日期比较函数，根据比较参数，返回两个日期的差额<br>
         * 比较参数： <LI>MS - 毫秒 <LI>SECOND - 秒 <LI>MINUTE - 分 <LI>HOUR - 时 <LI>DAY
         * - 天
         * 
         * @param s
         *                原日期
         * @param d
         *                目标日期
         * @param flag
         *                比较参数
         * @return 日期相比较的差额
         */
        public static long compareDate(Date s, Date d, int flag)
        {
                long l = 0;
                switch (flag)
                {
                case MS:
                        l = compareDate(s, d);
                        break;
                case SECOND:
                        l = compareDate(s, d) / MS_PER_SECOND;
                        break;
                case MINUTE:
                        l = compareDate(s, d) / MS_PER_MINUTE;
                        break;
                case HOUR:
                        l = compareDate(s, d) / MS_PER_HOUR;
                        break;
                case DAY:
                        l = compareDate(s, d) / MS_PER_DAY;
                        break;
                default:
                        l = compareDate(s, d);
                }
                return l;
        }

        /**
         * 日期比较函数，根据比较参数，返回两个日期的差额<br>
         * 比较参数： <LI>MS - 毫秒 <LI>SECOND - 秒 <LI>MINUTE - 分 <LI>HOUR - 时 <LI>DAY
         * - 天
         * 
         * @param s
         *                原日期（格式：YYYY-MM-DD '-'为任意单字符分隔符号）
         * @param d
         *                目标日期 （格式：YYYY-MM-DD '-'为任意单字符分隔符号）
         * @param flag
         *                比较参数
         * @return 日期相比较的差额
         */
        public static long compareDate(String s, String d, int flag)
        {
                Date s1 = getDate(s, null);
                Date d1 = getDate(d, null);
                return compareDate(s1, d1, flag);
        }

        /**
         * 将数字字符串传唤为科学技术法格式
         */
        public static String setComma(String strSrc)
        {
                if (strSrc == null || strSrc.equals(""))
                {
                        return "";
                }
                StringTokenizer st = new StringTokenizer(strSrc, ".");
                int i = 0;
                boolean bMinus = false;
                String strTemp1 = new String("");
                String strTemp2 = new String("");
                while (st.hasMoreTokens())
                {
                        switch (i)
                        {
                        case 0:
                                strTemp1 = st.nextToken();
                                if ('-' == strTemp1.charAt(0))
                                {
                                        strTemp1 = strTemp1.substring(1, strTemp1.length());
                                        bMinus = true;
                                }
                                break;

                        case 1:
                                strTemp2 = st.nextToken();
                                break;
                        }
                        i++;
                }
                String strBuff = new String("");
                int iLoop = strTemp1.length() / 3;
                int iMark = strTemp1.length() - iLoop * 3;
                strBuff = strTemp1.substring(0, iMark);
                for (i = 0; i < iLoop; i++)
                {
                        if (strTemp1.length() > 3 && strBuff.length() > 0)
                        {
                                strBuff = strBuff.concat(",");
                        }
                        strBuff = strBuff + strTemp1.substring(iMark, iMark + 3);
                        iMark += 3;
                }

                if (bMinus)
                {
                        if (strTemp2.length() == 0)
                        {
                                return "-".concat(strBuff);
                        } else
                        {
                                return "-".concat(strBuff).concat(".").concat(strTemp2);
                        }
                }
                if (strTemp2.length() == 0)
                {
                        return strBuff;
                } else
                {
                        return strBuff.concat(".").concat(strTemp2);
                }
        }

        /**
         * Translates the String representation of a BigDecmal into a
         * BigDecimal. TODO 说明方法用途
         * 
         * @param val
         *                String representation of BigDecimal
         * @param scale
         *                scale of the BigDecimal value to be returned
         * @param roundingMode
         *                mode of rounding
         */
        public static String getBigDecimalString(String val, int scale, int roundingMode)
        {
                if (val == null)
                {
                        val = "0";
                }
                BigDecimal from = new BigDecimal(val);
                BigDecimal to = from.setScale(scale, roundingMode);
                return to.toString();
        }

        /**
         * TODO 说明方法用途 Translates the String representation of a BigDecmal into
         * a BigDecimal, with roundingmode of ROUND_HALF_UP
         * 
         * @param val
         *                String representation of BigDecimal
         * @param scale
         *                scale of the BigDecimal value to be returned
         */
        public static String getBigDecimalString(String val, int scale)
        {
                if (val == null)
                {
                        val = "0";
                }
                BigDecimal from = new BigDecimal(val);
                BigDecimal to = from.setScale(scale, BigDecimal.ROUND_HALF_UP);
                return to.toString();
        }

        /**
         * 按照一定格式返回request的所有参数的字符串形式
         */
        public static String generateRequestDebugInfo(ServletRequest request)
        {
                StringBuilder sb = new StringBuilder("HTTP params: ");
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements())
                {
                        String name = params.nextElement().toString();
                        sb.append(name + "=" + request.getParameter(name) + "\n");
                }
                return sb.toString();
        }

        /**
         * 根据系统所支持的charset进行穷举转换，为乱码问题提供调试信息。 在系统正式运行时，关于此函数的调用语句应该删掉，否则会影响效率
         * 
         * @param origin
         * @return
         */
        @Deprecated
        public static String generateCharsetDebugInfo(String origin)
        {
                List<String> charSets = Arrays.asList("UTF-8", "GBK", "ISO-8859-1", "GB2312");

                StringBuilder sb = new StringBuilder();

                for (Iterator<String> iterFrom = charSets.iterator(); iterFrom.hasNext();)
                {
                        String charSetFrom = iterFrom.next();
                        for (Iterator<String> iterTo = charSets.iterator(); iterTo.hasNext();)
                        {
                                String charSetTo = iterTo.next();
                                sb.append("new String ( origin.getBytes(" + charSetTo + "), " + charSetFrom + " )");
                                try
                                {
                                        String result = new String(origin.getBytes(charSetTo), charSetFrom);
                                        sb.append(result);
                                } catch (Exception e)
                                {
                                        log.error("encoding Exception", e);
                                }
                                sb.append("\n");
                        }
                }

                return sb.toString();
        }
        
        public static void clearSessionAttribute()
        {
                HttpSession session = ServletActionContext.getRequest().getSession();

                for (Enumeration<String> e = session.getAttributeNames(); e.hasMoreElements();)
                {
                        session.removeAttribute(e.nextElement());
                }
        }

}