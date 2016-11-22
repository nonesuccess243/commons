package com.nbm.commons.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取系统xml格式配置文件工具类
 * 
 * @author Administrator
 * 
 */
public class SystemConfig
{
        private XMLConfiguration config;
        private static final Logger log = LoggerFactory.getLogger(SystemConfig.class);

        private SystemConfig()
        {

        }

        public static final SystemConfig INSTANCE = new SystemConfig();

        public void init(URL configFile)
        {
                if (configFile != null)
                {
                        try
                        {
                                config = new XMLConfiguration(configFile);
                                log.info("初始化系统配置成功");
                        } catch (ConfigurationException e)
                        {
                                log.error("初始化系统配置失败", e);
                        }
                } else
                {
                        log.error("初始化系统配置失败,文件不存在");
                }

        }

        /**
         * 获取系统启动时配置相关的需要初始化的类
         * 
         * @return
         */
        public LinkedHashMap<String, Map<String, String>> getClassMap()
        {
                LinkedHashMap<String, Map<String, String>> maps = null;
                List<String> classes = getStringList("configurable.class.name");
                maps = new LinkedHashMap<String, Map<String, String>>();
                for (int i = 0; i < classes.size(); i++)
                {
                        Map<String, String> paramMap = new HashMap<String, String>();

                        String namePath = "configurable.class(" + i + ")" + ".param.name";
                        String valuePath = "configurable.class(" + i + ")" + ".param.value";
                        List<String> paramName = getStringList(namePath);
                        List<String> paramValue = getStringList(valuePath);
                        for (int j = 0; j < paramName.size(); j++)
                        {
                                paramMap.put(paramName.get(j), paramValue.get(j));
                        }
                        maps.put(classes.get(i), paramMap);
                }
                return maps;
        }

        /**
         * 获取节点值
         * 
         * @param xpath
         *                节点路径
         * @return String
         */
        public String getStringValue(String xpath)
        {
                return config.getString(xpath);
        }

        /**
         * 获取节点值
         * 
         * @param xpath
         *                节点路径
         * @return Int
         */
        public int getIntValue(String xpath)
        {
                return config.getInt(xpath);
        }

        /**
         * 获取节点值
         * 
         * @param xpath
         *                节点路径
         * @return Double
         */
        public double getDoubleValue(String xpath)
        {
                return config.getDouble(xpath);
        }

        /**
         * 获取节点值
         * 
         * @param xpath
         *                节点路径
         * @return List<String>
         */
        @SuppressWarnings("unchecked")
        public List<String> getStringList(String xpath)
        {
                List<Object> result_ = config.getList(xpath);
                List<String> result = new ArrayList<String>(result_.size());

                for (Object obj : result_)
                {
                        result.add(obj.toString());
                }
                return result;
        }

        public void setConfig(XMLConfiguration config)
        {
                this.config = config;
        }

        public XMLConfiguration getConfig()
        {
                return config;
        }

        /**
         * 调试用，外部不应调用。
         * 
         * @param args
         */
        public static void main(String[] args)
        {
                SystemConfig INSTANCE1 = new SystemConfig();
                try
                {
                        INSTANCE1.config = new XMLConfiguration("configuration.xml");
                } catch (ConfigurationException e)
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                System.out.print(INSTANCE1.getClassMap().size());
                /*
                 * List<Map<String,String>> result =
                 * INSTANCE1.getClassListMap();
                 * 
                 * 
                 * for(String key : result.get(0).keySet()) {
                 * System.out.print(key+":");
                 * System.out.print(result.get(0).get(key)+"\n"); }
                 */
        }

}
