/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:    Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EncodingFilter
 */
public class EncodingFilter implements Filter
{

        private FilterConfig config = null;
        private String targetEncoding = "ASCII";
        private static final Logger log = LoggerFactory.getLogger(EncodingFilter.class);

        public void init(FilterConfig config) throws ServletException
        {
                this.config = config;
                this.targetEncoding = config.getInitParameter("encoding");
                if (log.isInfoEnabled())
                        log.info("EncodingFilter initialized");
        }

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                        ServletException
        {

                HttpServletRequest srequest = (HttpServletRequest) request;
                srequest.setCharacterEncoding(targetEncoding);
                HttpServletResponse sresponse = (HttpServletResponse) response;
                sresponse.setCharacterEncoding(targetEncoding);
                // move on to the next
                chain.doFilter(request, response);
        }

        public void destroy()
        {

                this.config = null;
                this.targetEncoding = null;
        }

        public FilterConfig getFilterConfig()
        {

                return this.config;
        }

        public void setFilterConfig(FilterConfig config)
        {
                this.config = config;
        }
}
