/***********************************************
 * Title:       RegularTestMain.java
 * Description: RegularTestMain.java
 * Create Date: 2012-4-17
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.httpclient.model.VarParseException;

/**
 * 使用正则表达式解析Html内容的测试类。 构造时须指定匹配模式，该模式可以带有变量。 经过解析，可以得到变量-值的Map数组。
 */
public class RegexVarParser
{
        private final static Logger log = LoggerFactory.getLogger(RegexVarParser.class);

        /**
         * 包含变量${varName}的模式字符串
         */
        private String patternWithVar;

        /**
         * 替换变量名之后的模式字符串
         * 
         * @see RegexVarParser#replaceVar(String)
         */
        private String patternWithoutVar;

        /**
         * 将patternWithoutVar编译为Pattern对象
         */
        private Pattern pattern;

        /**
         * 缓存已找到的字符串
         */
        private String matchedString;

        /**
         * 
         * @param patternWithVar
         *                带有变量的模式字符串，形如：
         *                "<a href=\"${url}\" target=\"_blank\" class=\"l14\">"
         *                + "${title}</a> " +
         *                "<span class=\"sj\">\\S+</span></td>";
         */
        public RegexVarParser(String patternWithVar)
        {
                log.debug("构建RegexParser[" + patternWithVar + "].");

                this.patternWithVar = patternWithVar;
                patternWithoutVar = replaceVar(patternWithVar);

                log.debug("正则表达式：" + patternWithoutVar);
                pattern = Pattern.compile(patternWithoutVar);
        }

        /**
         * 返回所有的匹配结果
         * 
         * @param source
         * @return
         */
        public List<Map<String, String>> parse(String context)
        {
                long startTime = System.currentTimeMillis();

                List<Map<String, String>> result = new ArrayList<Map<String, String>>(200);

                Matcher m = pattern.matcher(context);

                while (m.find())
                {
                        String matched = m.group();
                        log.debug("匹配成功，正则表达式：" + pattern.pattern() + "\n匹配内容：" + matched);

                        matchedString = matched;

                        Map<String, String> info = parseSingle();
                        log.debug(info.toString());
                        result.add(info);
                }

                log.info("解析完毕，共解析结果" + result.size() + "条，耗时"
                                + (System.currentTimeMillis() - startTime) + "毫秒");

                return result;
        }

        /**
         * 返回第一个匹配结果
         * 
         * @param context
         * @return
         */
        public RegexVarSingleResult parseFirst(String context)
        {
                RegexVarSingleResult result = new RegexVarSingleResult();

                Map<String, String> resultMap = new HashMap<String, String>(200);
                result.setResult(resultMap);
                Matcher m = pattern.matcher(context);

                if (m.find())
                {
                        String matched = m.group();
                        log.debug("匹配成功，正则表达式：" + pattern.pattern() + "\n匹配内容：" + matched);

                        matchedString = matched;

                        resultMap = parseSingle();
                        result.setEndIndex(m.end());
                        result.setStartIndex(m.start());
                        result.setResult(resultMap);
                        log.debug("解析成功，共解析到变量" + resultMap.size() + "个");
                } else
                {
                        throw new VarParseException(pattern.pattern(), context);
                }

                return result;
        }

        /**
         * 解析单个正则表达式，将结果转换为键值对
         * 
         * @param source
         * @return
         */
        private Map<String, String> parseSingle()
        {
                Map<String, String> results = new HashMap<String, String>();

                List<String> varNames = getAllVarNames();

                for (String varName : varNames)
                {
                        log.debug("varName=" + varName);
                        results.put(varName, getVarValue(varName));
                }

                return results;
        }

        /**
         * 带有变量名称的模式字符串中的所有变量名称
         * 
         * @param source
         * @return
         */
        private List<String> getAllVarNames()
        {
                List<String> results = new ArrayList<String>(10);

                Pattern p = Pattern.compile(VAR_NAME_PATTERN);
                Matcher m = p.matcher(patternWithVar);

                while (m.find())
                {
                        results.add(m.group());
                }

                log.debug(results.toString());
                return results;
        }

        /**
         * 匹配模式字符串中的变量，${varName}。
         */
        private final static String VAR_SEARCH_PATTERN = "\\$\\{\\w+\\}";
        private final static String VAR_NAME_PATTERN = "(?<=\\$\\{)\\w+(?=\\})";
        private final static String NOT_NULL_STRING = "[\\\\s\\\\S]{0,1000000}?";//

        /**
         * 将模式字符串中的变量名称替换为NOT_NULL_STRING
         * 
         * @param varPattern
         * @return
         */
        private static String replaceVar(String varPattern)
        {
                return varPattern.replaceAll(VAR_SEARCH_PATTERN, NOT_NULL_STRING);
        }

        /**
         * 将模式字符串中的某个变量替换为\w+，其余内容中的变量替换为\w+，并作断言处理
         * 
         * @param varPattern
         * @param varName
         * @return
         */
        private String replaceSingleVar(String varName)
        {
                String _varName = "${" + varName + "}";
                int index = patternWithVar.indexOf(_varName);// indexOf不作正则表达式查询，因此${}不需要转义
                // log.debug(index);

                // 将varName前面的部分替换为(?<=前面的部分)
                // 将varName后面的部分替换为(?=后面的部分)
                String newPattern = "(?<=" + patternWithVar.substring(0, index) + ")" + _varName;
                String suffix = patternWithVar.substring(index + _varName.length());
                if (!"".equals(suffix))
                {
                        newPattern += "(?=" + suffix + ")";
                }

                newPattern = replaceVar(newPattern);

                return newPattern;
        }

        /**
         * 返回source中的第一个名称为varName的变量值
         * 
         * @param source
         * @param varName
         * @return
         */
        private String getVarValue(String varName)
        {
                String newPattern = replaceSingleVar(varName);
                log.debug("获取变量值[pattern=" + newPattern + "\nvarName=" + varName + "].");
                Matcher m = Pattern.compile(newPattern).matcher(matchedString);
                m.find();
                String result = m.group();
                log.debug("找到变量值：" + result);

                return result;
        }

        /**
         * @return the patternWithVar
         */
        public String getPatternWithVar()
        {
                return patternWithVar;
        }

}
