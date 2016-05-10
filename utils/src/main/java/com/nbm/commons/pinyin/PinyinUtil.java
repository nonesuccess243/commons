package com.nbm.commons.pinyin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * @className:PinyingUtil.java
 * @classDescription:拼音操作工具类
 * @author:xiayingjie
 * @createTime:2010-10-21
 */
public class PinyinUtil
{

        private static Logger log = LoggerFactory.getLogger(PinyinUtil.class);

        /**
         * 将字符串转换成拼音数组
         * 
         * @param src
         * @return
         */
        public static String[] stringToPinyin(String src)
        {
                return stringToPinyin(src, false, null);
        }

        /**
         * 将字符串转换成拼音数组
         * 
         * @param src
         * @return
         */
        public static String[] stringToPinyin(String src, String separator)
        {
                return stringToPinyin(src, true, separator);
        }

        /**
         * 将字符串转换成拼音数组
         * 
         * @param src
         * @param isPolyphone
         *                是否查出多音字的所有拼音
         * @param separator
         *                多音字拼音之间的分隔符
         * @return
         */
        public static String[] stringToPinyin(String src, boolean isPolyphone, String separator)
        {
                if ("".equals(src) || null == src)
                {
                        return null;
                }
                char[] srcChar = src.toCharArray();
                int srcCount = srcChar.length;
                String[] srcStr = new String[srcCount];

                for (int i = 0; i < srcCount; i++)
                {
                        srcStr[i] = charToPinyin(srcChar[i], isPolyphone, separator);
                }
                return srcStr;
        }

        /**
         * 将单个字符转换成拼音
         * 
         * @param src
         * @return
         */
        public static String charToPinyin(char src, boolean isPolyphone, String separator)
        {
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
                defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
                defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

                StringBuffer tempPinying = new StringBuffer();

                if (src > 128)
                {
                        try
                        {
                                String[] strs = PinyinHelper.toHanyuPinyinStringArray(src,
                                                defaultFormat);

                                if (isPolyphone && null != separator)
                                {
                                        for (int i = 0; i < strs.length; i++)
                                        {
                                                tempPinying.append(strs[i]);
                                                if (strs.length != (i + 1))
                                                {
                                                        tempPinying.append(separator);
                                                }
                                        }
                                } else
                                {
                                        tempPinying.append(strs[0]);
                                }

                        } catch (BadHanyuPinyinOutputFormatCombination e)
                        {
                                log.error("charToPinyin error", e);
                        }
                } else
                {
                        tempPinying.append(src);
                }

                return tempPinying.toString();

        }

        public static String hanziToPinyin(String hanzi)
        {
                return hanziToPinyin(hanzi, " ");
        }

        /**
         * 将汉字转换成拼音
         * 
         * @param hanzi
         * @param separator
         * @return
         */
        public static String hanziToPinyin(String hanzi, String separator)
        {
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
                defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
                defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

                String pinyingStr = "";
                try
                {
                        pinyingStr = PinyinHelper.toHanyuPinyinString(hanzi, defaultFormat,
                                        separator);
                } catch (BadHanyuPinyinOutputFormatCombination e)
                {
                        log.error("hanziToPinyin error", e);
                }
                return pinyingStr;
        }

        /**
         * 将字符串数组转换成字符串
         * 
         * @param str
         * @param separator
         *                各个字符串之间的分隔符
         * @return
         */
        public static String stringArrayToString(String[] str, String separator)
        {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < str.length; i++)
                {
                        sb.append(str[i]);
                        if (str.length != (i + 1))
                        {
                                sb.append(separator);
                        }
                }
                return sb.toString();
        }

        /**
         * 简单的将各个字符数组之间连接起来
         * 
         * @param str
         * @return
         */
        public static String stringArrayToString(String[] str)
        {
                return stringArrayToString(str, "");
        }

        /**
         * 将字符数组转换成字符串
         * 
         * @param str
         * @param separator
         *                各个字符串之间的分隔符
         * @return
         */
        public static String charArrayToString(char[] ch, String separator)
        {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < ch.length; i++)
                {
                        sb.append(ch[i]);
                        if (ch.length != (i + 1))
                        {
                                sb.append(separator);
                        }
                }
                return sb.toString();
        }

        /**
         * 将字符数组转换成字符串
         * 
         * @param str
         * @return
         */
        public static String charArrayToString(char[] ch)
        {
                return charArrayToString(ch, " ");
        }

        /**
         * 取汉字的首字母
         * 
         * @param src
         * @param isCapital
         *                是否是大写
         * @return
         */
        public static char[] getHeadByChar(char src, boolean isCapital)
        {
                char[] headChars=new char[1];
                try
                {
                        if (src <= 128)
                        {
                                return new char[]
                                { src };
                        }
                        String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);
                        
                        if( pinyingStr == null )
                        {
                                pinyingStr = new String[0];
                        }

                        int polyphoneSize = pinyingStr.length;
                        headChars = new char[polyphoneSize];
                        int i = 0;
                        for (String s : pinyingStr)
                        {
                                char headChar = s.charAt(0);
                                if (isCapital)
                                {
                                        headChars[i] = Character.toUpperCase(headChar);
                                } else
                                {
                                        headChars[i] = headChar;
                                }
                                i++;
                        }

                        return headChars;    
                } catch (Exception e)
                {
                        log.error("获取首字母发生异常:"+src,e);
                }
                return headChars;
        }

        /**
         * 取汉字的首字母(默认是大写)
         * 
         * @param src
         * @return
         */
        public static char[] getHeadByChar(char src)
        {
                return getHeadByChar(src, true);
        }

        /**
         * 查找字符串首字母
         * 
         * @param src
         * @return
         */
        public static String[] getHeadByString(String src)
        {
                return getHeadByString(src, true);
        }

        /**
         * 查找字符串首字母
         * 
         * @param src
         * @param isCapital
         *                是否大写
         * @return
         */
        public static String[] getHeadByString(String src, boolean isCapital)
        {
                return getHeadByString(src, isCapital, null);
        }

        /**
         * 查找字符串首字母
         * 
         * @param src
         * @param isCapital
         *                是否大写
         * @param separator
         *                分隔符
         * @return
         */
        public static String[] getHeadByString(String src, boolean isCapital, String separator)
        {
                char[] chars = src.toCharArray();
                String[] headString = new String[chars.length];
                int i = 0;
                for (char ch : chars)
                {

                        char[] chs = getHeadByChar(ch, isCapital);
                        StringBuffer sb = new StringBuffer();
                        if (null != separator)
                        {
                                int j = 1;

                                for (char ch1 : chs)
                                {
                                        sb.append(ch1);
                                        if (j != chs.length)
                                        {
                                                sb.append(separator);
                                        }
                                        j++;
                                }
                        } else
                        {
                                if( chs != null && chs.length>=1 )
                                        sb.append(chs[0]);
                        }
                        headString[i] = sb.toString();
                        i++;
                }
                return headString;
        }

        public static void main(String[] args)
        {
                System.out.println(PinyinUtil.stringArrayToString(getHeadByString("我的心肝爱上")));
                StringBuffer sb = new StringBuffer();
                String a = null;
                sb.append(a);
        }

}
