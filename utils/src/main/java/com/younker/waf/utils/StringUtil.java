package com.younker.waf.utils;

import java.util.Iterator;

import org.joda.time.LocalDate;

/**
 * @author Niyuzhe
 * 
 */
public class StringUtil
{
        /**
         * 将实现iterable的对象转化为字符串
         * 
         * @param iterable
         *                要转化的对象
         * @param separator
         *                每次迭代的分隔符
         *                
         * @return 生成的字符串
         */
        public static <T> String toString(Iterable<T> iterable, String separator)
        {
                StringBuilder sb = new StringBuilder();

                Iterator<T> iter = iterable.iterator();

                if (iter.hasNext())
                        sb.append(iter.next());
                while (iter.hasNext())
                {
                        sb.append(separator).append(iter.next());
                }

                return sb.toString();
        }

        /**
         * 将实现iterable接口的对象转化为字符串，用换行符分割
         */
        public static <T> String toString(Iterable<T> iterable)
        {
                return toString(iterable, "\n");
        }

        /**
         * 将整数转化为相应位数的字符串，不足高位补0，多余高位舍去
         * 
         * @param value
         * @param length
         * @return
         */
        public static String getLongString(long value, int length)
        {
                if (length == 0)
                        return new String("0");
                value %= Math.pow(10, length);
                String s = String.valueOf(value);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length - s.length(); i++)
                {
                        sb.append(0);
                }
                sb.append(value);
                return sb.toString();
        }

        /**
         * 将origin中的{}顺次替换为params中的值，类似于logback拼接字符串的逻辑。
         * 
         * 如果origin中的{}数目大于params数目，则只替换前几个{}。如果小于，则只替换前几个param
         * 
         * @param origin
         * @param params
         * @return
         */
        public static String getString(String origin, Object... params)
        {
                for (Object param : params)
                {
                        if (param == null)
                        {
                                param = "[null]";
                        }
                        origin = origin.replaceFirst("\\{\\}", param.toString());
                }

                return origin;
        }

        /**
         * 全角转半角
         * 
         * @param origin
         * @return
         */
        public static String fullWidthToHalf(String origin)
        {

                if (origin == null || origin.equals(""))
                {
                        return origin;
                }

                char c[] = origin.toCharArray();
                for (int i = 0; i < c.length; i++)
                {
                        if (c[i] == '\u3000')
                        {
                                c[i] = ' ';
                        } else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
                        {
                                c[i] = (char) (c[i] - 65248);

                        }
                }

                String returnString = new String(c);

                return returnString;
        }

        /**
         * 在findFrom中查找findIt，如果与任意一个相等则返回true，所有都不相等则返回fasle
         * 
         * 如果findIt为null，则返回false
         * @param findIt
         * @param findFrom
         * @return
         */
        public static boolean contains(String findIt, String... findFrom)
        {
                if( findIt == null )
                {
                        return false;
                }
                
                for (String f : findFrom)
                {
                        if (f.equals(findIt))
                                return true;
                }
                return false;

        }

//        public static int getAge(LocalDate birthday)
//        {
//                LocalDate now = new LocalDate();
//                LocalDate birthdayOfThisYear = new LocalDate(now.getYear(), birthday.getMonthOfYear(),
//                                birthday.getDayOfMonth());
//                if (now.isAfter(birthdayOfThisYear) || now.isEqual(birthdayOfThisYear))
//                {
//                        return now.getYear() - birthday.getYear();
//                } else
//                {
//                        return now.getYear() - birthday.getYear() - 1;
//                }
//        }
        
        /**
         * 转换为字符串，值为null值返回“”
         * @param str
         * @return
         */
        public static String changeString(String str)
        {
                if(str==null)
                {
                        return "";
                }
                return str.toString();
        }
}
