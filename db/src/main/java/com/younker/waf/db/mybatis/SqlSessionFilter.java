package com.younker.waf.db.mybatis;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class SqlSessionFilter
 */
public class SqlSessionFilter implements Filter
{

        private final static Logger log = LoggerFactory.getLogger(SqlSessionFilter.class);

        /**
         * Default constructor.
         */
        public SqlSessionFilter()
        {
        }

        /**
         * @see Filter#destroy()
         */
        public void destroy()
        {
        }

        /**
         * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
         */
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                        throws IOException, ServletException
        {
                SqlSession sqlSession = SqlSessionProvider.openSession();
                try
                {
                        chain.doFilter(request, response);
                        sqlSession.commit();
                } catch (Exception e)
                {
                        try
                        {
                                log.error("发生异常，回滚", e);
                                sqlSession.rollback();

                        } catch (Exception e1)
                        {
                                log.error("数据库回滚发生异常", e1);
                                throw e1;
                        }
                        //throw e;
                } finally
                {
                        SqlSessionProvider.closeSession();
                }
        }

        /**
         * @see Filter#init(FilterConfig)
         */
        public void init(FilterConfig fConfig) throws ServletException
        {
                // TODO Auto-generated method stub
        }

}
