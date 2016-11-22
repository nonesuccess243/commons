/***********************************************
` * Title:       HtmlGettor.java
 * Description: HtmlGettor.java
 * Create Date: 2012-2-18
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;
import com.nbm.httpclient.exception.ServerInternalErrorException;

/**
 * 使用Post方法获取Html内容的工具类
 */
public class HtmlPostGettor
{

        private final static Logger log = LoggerFactory.getLogger(HtmlPostGettor.class);
        
        private final static Logger http_access_log = LoggerFactory.getLogger("http_access");
        
        private final static HtmlPostGettor DEFAULT_INSTANCE;
        private final static Map<String, HtmlPostGettor> instances = new HashMap<String, HtmlPostGettor>();
        static
        {
                DEFAULT_INSTANCE = new HtmlPostGettor(new DefaultHttpClient());
        }

        /**
         * 每次调用返回的对象共享cookie
         * 
         * @return
         */
        public static HtmlPostGettor getDefaultInstance()
        {
                return DEFAULT_INSTANCE;
        }

        public static HtmlPostGettor getInstance()
        {
                return new HtmlPostGettor(new DefaultHttpClient());
        }

        /**
         * 获取命名的实例。相同的名称，第一次调用时创建，之后缓存。 名字相同的实例共享cookie
         * 
         * @param name
         * @return
         */
        public static HtmlPostGettor getInstance(String name)
        {
                HtmlPostGettor instance = instances.get(name);
                if (instance == null)
                {
                        instance = new HtmlPostGettor(new DefaultHttpClient());
                        instances.put(name, instance);
                }
                return instance;
        }
        
        /**
         * 
         * @return 每次都返回一个独立cookie的htmlPostGettor
         */
        public static HtmlPostGettor getIndependentInstance()
        {
                return new HtmlPostGettor(new DefaultHttpClient());
        }

        private HtmlPostGettor(HttpClient httpclient)
        {
                this.httpclient = httpclient;
        }


        private HttpClient httpclient;

        public String getPostHtmlContent(String host, String path, String encode,
                        List<NameValuePair> paramGet, List<NameValuePair> paramPost)
                        throws Exception
        {
                long time1 = System.currentTimeMillis();

                URI uri = URIUtils.createURI("http", host, -1, path,
                                URLEncodedUtils.format(paramGet, encode), null);
                HttpPost post = new HttpPost(uri);

                post.setEntity(new UrlEncodedFormEntity(paramPost, encode));
                HttpResponse response = httpclient.execute(post);

                // 处理重定向
                StatusLine s = response.getStatusLine();
                // StatusLine获得相应状态
                // System.out.println(s.getStatusCode());

                HttpEntity entity = response.getEntity();

                String result = null;
                if (entity != null)
                {
                        InputStream instream = entity.getContent();
                        result = inputStreamToString(instream, encode);
                        // System.out.println(result);
                }
                // log.error(s.getStatusCode());
                http_access_log.info("获取" + host + "/" + path + "Html代码完成，耗时" + (System.currentTimeMillis() - time1)
                                + "毫秒");
                http_access_log.debug(result);
                
                if( s.getStatusCode() == 500 )
                {
                        throw new ServerInternalErrorException(500, result, host, path, encode, paramGet, paramPost);
                }
                
                if( s.getStatusCode() == 503 )
                {
                        throw new Exception("发生503错误");
                }
                        

                // 必须清空inputStream之后才能执行下一次html请求
                if (s.getStatusCode() == 302)
                {
                        http_access_log.info("redirect to: " + response.getHeaders("Location")[0].toString());
                        String newUrl = response.getHeaders("Location")[0].toString().replaceFirst(
                                        "Location:\\s+http://", "");
                        http_access_log.debug("重定向到：" + newUrl);
                        return getPostHtmlContent(host, newUrl.replaceFirst(host, ""), encode,
                                        paramGet, paramPost);
                        // TODO
                        // 登录成功后，此处重定向到了/index.jsp;jsessionid=CCA1D56130708876BDE6F7B3F6C5EDEE
                        // 在浏览器中直接输入此地址，正确显示，但此处程序跳转到了404页面，原因未知
                }

                return result;
        }

        /**
         * 将InputStream转换为String的工具函数 当发生IOException异常时，会将前面正确的字符串返回
         * 
         * @param stream
         * @return
         */
        private static String inputStreamToString(InputStream is, String encoding)
        {
                int i = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try
                {
                        while ((i = is.read()) != -1)
                        {
                                baos.write(i);
                        }
                } catch (IOException e)
                {
                        throw new NbmBaseRuntimeException("读取html内容的inputStream发生异常", e);
                }
                String content = null;
                try
                {
                        content = baos.toString(encoding);
                } catch (UnsupportedEncodingException e)
                {
                        log.error("不支持的字符集" + encoding);
                        throw new RuntimeException(e);
                }

                return content;

        }

        // public static void main(String[] args) throws Exception
        // {
        // Log4jInit.INSTANCE.getClass();
        // HtmlPostGettor getter = getDefaultInstance();
        // String[][] loginParam =
        // {
        // { "pwd", "111111" },
        // { "username", "T120109" },
        // { "method", "login" } };
        // getter.getHtmlContent("localhost:8090/tjjsw/", "loginAction.do",
        // "utf-8",
        // loginParam);
        //
        // String[][] p2 =
        // {
        // { "method", "detail" },
        // { "fnbm", "120109001001000001" } };
        // String result = getter.getHtmlContent("localhost:8090/tjjsw/",
        // "ylfnJbqk.do",
        // "utf-8", p2);
        // System.out.println(result);
        // }

}
