/***********************************************
 * Title:       ClientInstance.java
 * Description: ClientInstance.java
 * Create Date: 2012-9-6
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;
import com.nbm.httpclient.exception.ClientInstanceCloseException;
import com.nbm.httpclient.exception.HttpClientException;
import com.nbm.httpclient.exception.ServerInternalErrorException;
import com.nbm.httpclient.http.HtmlPostGettor;
import com.nbm.httpclient.model.request.RequestInstance;

/**
 * 提供提交请求，解析相应内容的函数
 */
public class ClientInstance implements Cloneable
{
        private final static Logger log = LoggerFactory.getLogger(ClientInstance.class);

        private HtmlPostGettor gettor = HtmlPostGettor.getIndependentInstance();
        private String name;
        private String host;
        private String encoding;

        private boolean open = true;

        private Map<String, RequestInstance> requests = new HashMap<String, RequestInstance>();

        public Collection<RequestInstance> getRequests()
	{
		return requests.values();
	}

	/**
         * 根据请求名称，搜索配置文件中对应的request配置，并执行。 TODO
         * 接受二维数组传参的形式只是在调试期间比较方便，正式开发的时候还是改成List比较好。待重构
         * 
         * @throws ServerInternalErrorException
         */
        public ResponseModel run(String requestName, String[][] paramGet, String[][] paramPost)
                        throws ServerInternalErrorException
        {
                if (!open)
                {
                        throw new ClientInstanceCloseException();
                }

                ResponseModel response = new ResponseModel();

                long startTime = System.currentTimeMillis();

                log.debug("执行" + requestName);

                RequestInstance request = requests.get(requestName);
                String responseContent = getResponseContent(requestName,
                                makeNameValuePair(paramGet), makeNameValuePair(paramPost), request);
                response.setResultContent(responseContent);

                long netEndTime = System.currentTimeMillis();// 网络用时

                Map<String, String> result = request.handleResponse(responseContent);
                response.setVars(result);

                long endTime = System.currentTimeMillis();

                log.debug("解析" + requestName + "完成，共解析到变量" + result.size() + "个，共耗时"
                                + (endTime - startTime) + "ms，其中网络耗时" + (netEndTime - startTime)
                                + "ms，解析耗时" + (endTime - netEndTime) + "ms。");

                return response;
        }

        /**
         * 以NameValuePair为参数进进行参数传递
         * 
         * @param requestName
         * @param paramGet
         * @param paramPost
         * @param request
         * @return
         * @throws ServerInternalErrorException
         */
        private String getResponseContent(String requestName,
                        List<NameValuePair> paramGet,
                        List<NameValuePair> paramPost,
                        RequestInstance request) throws ServerInternalErrorException
        {
                if (request == null)
                        throw new NullPointerException("未找到RequestInstance[name=" + requestName
                                        + "].");

                // List<NameValuePair> paramPairGet =
                // makeNameValuePair(paramGet);

                // 将defaultParam合并到paramGet中。由于默认参数是历次request都不变的，基本不可能有中文，所以暂时放在get中使用。
                // 后期需要再开发，支持灵活配置的默认参数
                paramGet.addAll(makeNameValuePair(request.getDefaultParams()));

                // List<NameValuePair> paramPairPost =
                // makeNameValuePair(paramPost);

                String responseContent;
                try
                {
                        responseContent = gettor.getPostHtmlContent(host, request.getUrl(),
                                        request.getEncoding(), paramGet, paramPost);
                        log.debug(responseContent);
                } catch (ServerInternalErrorException sie)
                {
                        throw sie;
                } catch (Exception e)
                {
                        //2014-12-20 李霖修改异常信息显示
                        throw new HttpClientException("发生网络异常", e).set("requestName",
                                        request.getName());
                }

                return responseContent;
        }

        /**
         * @param paramGet
         * @return
         */
        private List<NameValuePair> makeNameValuePair(String[][] paramGet)
        {
                List<NameValuePair> paramPairGet = new ArrayList<NameValuePair>();
                if (paramGet == null || paramGet.length == 0)
                {
                        log.debug("构造http请求参数的键值对时，传入了空参数列表，将返回空参数列表");
                } else
                {

                        for (String[] pairs : paramGet)
                        {
                                paramPairGet.add(new BasicNameValuePair(pairs[0], pairs[1]));
                        }
                }
                return paramPairGet;
        }

        public void addRequestInstance(RequestInstance requestInstance)
        {
        	
        	if( this.requests.containsKey(requestInstance.getName()))
        	{
        		
        		log.error("发现重复的request[request name={} path={}, request2 name={} path={}]", 
        				this.requests.get(requestInstance.getName()).getName(),
        				this.requests.get(requestInstance.getName()).getSourcePath(),
        				requestInstance.getName(),
        				requestInstance.getSourcePath());
        		
//        		throw new NbmBaseRuntimeException("发现重复的request")
//        			.set("request1", this.requests.get(requestInstance.getName())).set("request2", requestInstance);
        	}else
        	{
        		// set default values
                        if (requestInstance.getEncoding() == null
                                        || "".equals(requestInstance.getEncoding()))
                                requestInstance.setEncoding(encoding);
                        requests.put(requestInstance.getName(), requestInstance);
        	}
                
        }

        public void getRequestInstance(String name)
        {
                requests.get(name);
        }

        /**
         * 合并ClientInstance，将other中的所有request配置保存在本示例中。
         * other和本实例如果只有一个配置了host和encoding属性
         * ，其余一个为null，则最终将有内容的host和encoding保存在本实例中。 如果两个都没有配置，则合并后本实例依然保存null。
         * 如果两个都配置了，但配置的内容不相同，则抛出异常。
         * 
         * @param other
         */
        public void merge(ClientInstance other)
        {
        	
        	if( StringUtils.isBlank(getName()))
        	{
        		setName(other.getName());
        	}else if ( StringUtils.isNotBlank(other.getName()))
        	{
        		if( !other.getName().equals(getName()))
        		{
        			String errorMessage = "要合并的两个ClientInstance的name配置不相同，只能合并配置完全相容的client[this.name="
                                                + getName()
                                                + ", other.name="
                                                + other.getName()
                                                + "].";
                                log.error(errorMessage);
                                throw new IllegalArgumentException(errorMessage);
        		}
        	}
        	
                if (getHost() == null)// 本实例的host为null，不论othet.getHost()为何值都可以直接覆盖
                {
                        setHost(other.getHost());
                } else if (other.getHost() != null)// 两个getHost都不为空
                {
                        if (!other.getHost().equals(getHost()))// 两个host不相等，抛出异常
                        {
                                String errorMessage = "要合并的两个ClientInstance的host配置不相同，只能合并配置完全相容的client[this.host="
                                                + getHost()
                                                + ", other.host="
                                                + other.getHost()
                                                + "].";
                                log.error(errorMessage);
                                throw new IllegalArgumentException(errorMessage);
                        }// 两个host相等的情况，无需处理
                }// other的host为null，无需处理

                if (getEncoding() == null)
                {
                        setEncoding(other.getEncoding());
                } else if (other.getEncoding() != null)
                {
                        if (!other.getEncoding().equals(getEncoding()))
                        {
                                String errorMessage = "要合并的两个ClientInstance的encoding配置不相同，this.encoding="
                                                + getEncoding()
                                                + ", other.encoding="
                                                + other.getEncoding();
                                log.error(errorMessage);
                                throw new IllegalArgumentException(errorMessage);
                        }
                }
                
                if( other.properties != null )
                {
                	if( this.properties == null )
                	{
                		this.properties = other.properties;
                	}else
                	{
                		for( Entry<String, String> entry: other.properties.entrySet())
                		{
                			if( this.properties.get(entry.getKey()) != null )
                			{
                				throw new NbmBaseRuntimeException("重复定义properties")
                					.set("propertiesName", entry.getKey())
                					.set("propertiesValue1", entry.getValue())
                					.set("propertiesValue2", this.properties.get(entry.getKey()));
                			}else
                			{
                				this.properties.put(entry.getKey(), entry.getValue());
                			}
                		}
                	}
                		
                }


                //20160323改为统一调用addRequestInstance函数，这样可以利用该函数中的重复判断——倪
                for( Entry<String, RequestInstance> e : other.requests.entrySet())
                {
                	addRequestInstance(e.getValue());
                }
                
                log.info("成功合并clientInstance[name=" + this.getName() + "],增加了"
                                + other.requests.size() + "个request");

        }

        /**
         * @return the host
         */
        public String getHost()
        {
                return host;
        }

        /**
         * 设置host，并以host为name初始化htmlPostGettor
         * 
         * @param host
         *                the host to set
         */
        public void setHost(String host)
        {
                this.host = host;
        }

        /**
         * @return the encoding
         */
        public String getEncoding()
        {
                return encoding;
        }

        /**
         * @param encoding
         *                the encoding to set
         */
        public void setEncoding(String encoding)
        {
                this.encoding = encoding;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
                return "ClientInstance [host=" + host + ", encoding=" + encoding + ", requests="
                                + requests + "]";
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

        private Map<String, String> properties = new HashMap<>();

        void setProperties(String key, String value)
        {
                properties.put(key, value);
        }

        public String getProperties(String key)
        {
                return properties.get(key);
        }

        @Override
        public ClientInstance clone()
        {
                ClientInstance instance = new ClientInstance();
                instance.setName(this.name);
                instance.setHost(this.host);
                instance.setEncoding(this.encoding);

                instance.setOpen(this.open);

                instance.requests = this.requests;

                instance.properties = this.properties;

                return instance;
        }

        public void setOpen(boolean open)
        {
                this.open = open;
        }

}
