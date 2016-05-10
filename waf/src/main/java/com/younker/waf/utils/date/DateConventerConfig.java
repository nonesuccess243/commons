package com.younker.waf.utils.date;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.config.ConfigurableWebUtil;

public class DateConventerConfig implements ConfigurableWebUtil
{

        Logger log = LoggerFactory.getLogger(DateConventerConfig.class);

        @Override
        public void config(ServletContext context, Map<String, String> paramMap) throws Exception
        {
                ConvertUtils.register(new DateConverter(), java.util.Date.class);
                if (log.isInfoEnabled())
                        log.info("Date converter is registered");

        }

}
