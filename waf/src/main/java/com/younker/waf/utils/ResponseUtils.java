package com.younker.waf.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtils
{
        private static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

        public static void write(PageContext pctx, String content) throws JspException
        {
                try
                {
                        pctx.getOut().print(content);
                } catch (IOException ioe)
                {
                        log.error("pagecontext print error: ", ioe);
                }
        }

}
