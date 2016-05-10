/***********************************************
 * Title:       XmlClientBuilder.java
 * Description: XmlClientBuilder.java
 * Create Date: 2012-9-6
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.model;

import java.net.URI;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;
import com.nbm.httpclient.XmlDocumentProvider;
import com.nbm.httpclient.model.request.RequestInstance;

/**
 * 读取配置文件，生成ClientInstance实例的工具类。
 */
public class XmlClientBuilder
{
        private static Logger log = LoggerFactory.getLogger(XmlClientBuilder.class);

//        private String configPath;
        // private File configFile;
        
        private URI uri;

        private XmlDocumentProvider xmlDocumentProvider;

//        public XmlClientBuilder(String configPath, XmlDocumentProvider xmlDocumentProvider)
//                        throws Exception
//        {
//                this.configPath = configPath;
//                this.xmlDocumentProvider = xmlDocumentProvider;
//                // this.configFile = configFile;
//        }
//        
        
        /**
         * 传入classpath下的相对路径
         * @param uri
         */
        public XmlClientBuilder(URI uri)
        {
        	if( uri == null )
        	{
        		throw new NbmBaseRuntimeException("构建xmlClientBuilder时传入了为null的uri");
        	}
        	this.uri = uri;
        	this.xmlDocumentProvider = XmlDocumentProvider.CLASS_PATH_PROVIDER;
        }
        
        public ClientInstance build() throws Exception
        {
        	
        	if( xmlDocumentProvider == null )
        	{
        		throw new NbmBaseRuntimeException("xmlDocumentProvider is null");
        	}
        	

                ClientInstance instance = new ClientInstance();

                Document document;
                
                if( uri != null )
                {
                	log.debug("build by uri[{}]", uri);
                	document = new SAXReader().read(uri.toURL());
                }else
                {
//                	log.debug("build by configPath[{}]", configPath);
//                	
//                	document = xmlDocumentProvider.getDocument(configPath);
                	
                	throw new NbmBaseRuntimeException("传入的uri为null");
                }
                
                Element root = document.getRootElement();

                // 设置instance的name和host。如果配置文件中没有指定name，则使用host作为name
                instance.setName(root.attributeValue("name"));
                instance.setHost(root.attributeValue("host"));
                
                if( "true".equals(root.attributeValue("open")))
                {
                        instance.setOpen(true);
                }else
                {
                        instance.setOpen(false);
                }

                if (instance.getName() == null)
                {
                        instance.setName(instance.getHost());
                }

                instance.setEncoding(root.attributeValue("default-encoding"));

                for (@SuppressWarnings("unchecked")
                Iterator<Element> i = root.elementIterator(); i.hasNext();)
                {
                        Element r = i.next();
                        switch (r.getName())
                        {
                        case "request":
                                RequestInstance request = handleRequestElement(r);
                                instance.addRequestInstance(request);
                                break;
                        case "import":
//                                String path = r.getTextTrim();
//                                instance.merge(new XmlClientBuilder(path, xmlDocumentProvider)
//                                                .build());
                        	//改为FolderXmlClientBuilder目录方式后，已经不需要再处理import
                        	log.info("忽略import[{}]", r.getTextTrim());                        	
                                break;
                        case "properties":
                                instance.setProperties(r.attributeValue("name"),
                                                r.attributeValue("value"));
                                break;
                        default:
                                break;
                        }
                }

                log.info("根据配置文件[path=" + uri + "]生成ClientInstance实例[name="
                                + instance.getName() + "].");

                return instance;
        }

        /**
         * 处理配置文件中的request请求
         * 
         * @param r
         * @return
         */
        private RequestInstance handleRequestElement(Element r)
        {
                RequestInstance request = new RequestInstance();
                request.setName(r.attributeValue("name"));
                request.setUrl(r.attributeValue("url"));
                request.setEncoding(r.attributeValue("encoding"));
                request.setSourcePath(uri.toString());

                for (@SuppressWarnings("unchecked")
                Iterator<Element> ri = r.elementIterator(); ri.hasNext();)
                {
                        Element son = ri.next();
                        if ("default-param".equals(son.getName()))
                        {
                                request.addDefaultParam(son.attributeValue("name"),
                                                son.attributeValue("value"));
                        } else if ("pattern".equals(son.getName()))
                        {
                                request.addPattern(son.getTextTrim().replace("\n", "")
                                                .replace("\t", "").replace(" ", ""));
                        }
                }
                return request;
        }

}
