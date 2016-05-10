/***********************************************
 * Title:       RequestInstance.java
 * Description: RequestInstance.java
 * Create Date: 2012-9-6
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.model.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.httpclient.exception.HttpClientException;
import com.nbm.httpclient.regex.RegexVarParser;
import com.nbm.httpclient.regex.RegexVarSingleResult;

/**
 *
 */
public class RequestInstance
{

        private final static Logger log = LoggerFactory.getLogger(RequestInstance.class);

        private String name;
        private String url;
        private List<String> originPatterns = new ArrayList<String>(20);
        private String encoding;

        private List<String> newPatterns = new ArrayList<String>(20);

        private Map<String, String> defaultParams = new HashMap<String, String>();

        private List<RegexVarParser> parsers = new ArrayList<RegexVarParser>(20);

        
        /**
         * 为了便于调试，添加此字段，用于说明该requestInstance是在哪个配置文件中配置的
         */
	private String sourcePath;

        /**
         * 保存所有pattern中的变量之和。 如果解析出的变量数目少于这个数目，则说明解析有可能失败。
         */
        // TODO private int varAmount;

        /**
         * @param originPattern
         *                the pattern to set
         */
        public void addPattern(String originPattern)
        {
                this.originPatterns.add(originPattern);
                String newPattern = generateNewPattern(originPattern);
                log.debug("生成的pattern：" + newPattern);

                this.newPatterns.add(newPattern);
                parsers.add(new RegexVarParser(newPattern));
        }

        /**
         * 根据pattern的配置，在context中查找变量值。
         * 按照pattern的顺序查找，每次找context中的第一个匹配项，为了提高效率，
         * 在context中将已匹配的内容以及之前的部分全部丢弃，再进行下一次匹配。
         * 
         * @param responseContent
         * @return
         */
        public Map<String, String> handleResponse(String responseContent)
        {
                log.debug("开始解析");
                responseContent = addEnd(responseContent);
                Map<String, String> result = new HashMap<String, String>();
                for (RegexVarParser parser : parsers)
                {
                        try
                        {
                                RegexVarSingleResult singleResult = parser.parseFirst(responseContent);
                                log.debug("截短");
                                responseContent = responseContent.substring(singleResult.getEndIndex());

                                result.putAll(singleResult.getResult());
                        }catch(HttpClientException e)
                        {
                                e.set("requestName", name);
                                log.info("解析过程中发生错误"+ e.getMessage());
                        }
                }

                trimAll(result);
                return result;
        }

        /**
         * 为要解析的字符串增加前缀和后缀。 为了满足整体文本只有一个变量的情况，如果不加开始和结束标记，使用懒惰模式不能正确匹配全文 TODO
         * 待测试
         * 
         * @param responseContent
         * @return
         */
        private String addEnd(String responseContent)
        {
                responseContent = "~-~-" + responseContent + "~-~-";
                return responseContent;
        }

        /**
         * @return the name
         */
        public String getName()
        {
                return name;
        }

        /**
         * @param name
         *                the name to set
         */
        public void setName(String name)
        {
                this.name = name;
        }

        /**
         * @return the url
         */
        public String getUrl()
        {
                return url;
        }

        /**
         * @param url
         *                the url to set
         */
        public void setUrl(String url)
        {
                this.url = url;
        }

        /**
         * @return the encode
         */
        public String getEncoding()
        {
                return encoding;
        }

        /**
         * @param encoding
         *                the encode to set
         */
        public void setEncoding(String encoding)
        {
                this.encoding = encoding;
        }

        public void addDefaultParam(String paramName, String paramValue)
        {
                defaultParams.put(paramName, paramValue);
        }

        public String[][] getDefaultParams()
        {
                String[][] params = new String[defaultParams.size()][2];
                int index = 0;
                for (String name : defaultParams.keySet())
                {
                        params[index] = new String[2];
                        params[index][0] = name;
                        params[index][1] = defaultParams.get(name);
                        index++;
                }

                return params;
        }

        /**
         * #{{td}}替换为<td[^>]{0,10000}?>[\s\n]{0,10000}?
         * 
         * @{{td 替换为</td[^>]{0,10000}?>[\s\n]{0,10000}? !{td}替换为<td[^>
         *       ]{0,10000}?>${unitName}\s{0,10000}?</td>[\s\S]{0,10000}?
         *       这一条不太好写，暂时不实现了……
         * @param originPattern
         * @return
         */
        private String generateNewPattern(String originPattern)
        {

                return originPattern.replaceAll("\\#\\{\\{", "<").replace("@{{", "</")
                                .replaceAll("}}", "[^>]{0,10000}?>[\\\\s\\\\n]{0,10000}?");
        }

        /**
         * 将map中的所有字符串都执行trim()
         * 
         * @param result
         */
        private static void trimAll(Map<String, String> result)
        {
                for (String key : result.keySet())
                {
                        result.put(key, result.get(key).trim());
                }
        }

        @Override
	public String toString()
	{
		return "RequestInstance [name=" + name + ", url=" + url
				+ ", originPatterns=" + originPatterns
				+ ", encoding=" + encoding + ", newPatterns="
				+ newPatterns + ", defaultParams="
				+ defaultParams + ", parsers=" + parsers
				+ ", sourcePath=" + sourcePath + "]";
	}

	public void setSourcePath(String sourcePath)
	{
		this.sourcePath = sourcePath;
		
	}

	public String getSourcePath()
	{
		return this.sourcePath;
	}

}
