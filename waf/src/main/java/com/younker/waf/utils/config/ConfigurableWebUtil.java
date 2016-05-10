package com.younker.waf.utils.config;

import java.util.Map;

import javax.servlet.ServletContext;

/**
 * 在Servlet工程中，配置工具类统一实现本接口。 通过config函数的ServletConfig参数，可以调用当前的Servlet上下文使用。
 */
public interface ConfigurableWebUtil
{
        /**
         * 在本函数中实现读取配置文件等逻辑
         */
        public void config(ServletContext context, Map<String, String> params) throws Exception;
}
