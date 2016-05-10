package com.nbm.httpclient;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.nbm.exception.NbmBaseRuntimeException;


public interface XmlDocumentProvider
{
        Document getDocument(String configPath);

//        /**
//         * 把传入的路径视作文件系统的绝对路径或者相对路径进行处理
//         */
//        public static XmlDocumentProvider FILE_PROVIDER = new XmlDocumentProvider()
//        {
//
//                @Override
//                public Document getDocument(String configPath)
//                {
//                        try
//                        {
//                                return new SAXReader().read(configPath);
//                        } catch (DocumentException e)
//                        {
//                                throw new NbmBaseRuntimeException("TjjseBaseService初始化失败", e);
//                        }
//                }
//        };
        
        /**
         * 从classpath中查找
         */
        public static XmlDocumentProvider CLASS_PATH_PROVIDER = new XmlDocumentProvider()
        {

                @Override
                public Document getDocument(String configPath)
                {
                        try
                        {
                                return new SAXReader().read(this.getClass().getClassLoader().getResource(configPath));
                        } catch (Exception e)
                        {
                                throw new NbmBaseRuntimeException("TjjseBaseService初始化失败", e).set("configPath", configPath);
                        }
                }
        };
}
