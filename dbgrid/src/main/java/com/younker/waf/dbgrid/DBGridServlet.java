/******************************************************************************
 * Title:     Younker Web Application Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:        Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.dbgrid.utils.DBGridUtils;
import com.younker.waf.utils.WebUtil;

/**
 * DBGridServlet is the enter point of data list
 */
public class DBGridServlet extends HttpServlet
{
        static final long serialVersionUID = 1L;
        public static final String DATALIST = "datalist";
        public static final String DBGRID = "dbgrid";
        private static final String CONFIG_FILE_PATH_KEY = "configFilePath";
        private final static String DEFAULT_CONFIG_FILE_PATH = "/WEB-INF/config/dbgrids.xml";

        protected DBGrids dbGrids;

        private static final String[] urls =
        { "/component/dbgrid/dbgrid.jsp", "/component/dbgrid/dbgrid1.jsp", "/component/dbgrid/dbgrid2.jsp" };
        private static final Logger log = LoggerFactory.getLogger(DBGridServlet.class);

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
         */
        @Override
        public void init(ServletConfig config) throws ServletException
        {
                super.init(config);
                DBGridGenerator.setCtx(config.getServletContext());// 这句话只在调试时dbgrid作为一个项目有用，打成jar包后，不需要这句话
                generateDbGrids(config);
                logInfo();
        }

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                        IOException
        {
                this.process(request, response);
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                        IOException
        {

                this.process(request, response);
        }

        /**
         * 根据ctx传入的参数解析配置文件，生成dbgrid对象
         */
        protected void generateDbGrids(ServletConfig cfg)
        {

                String configFilePath = cfg.getInitParameter(CONFIG_FILE_PATH_KEY);
                if (WebUtil.isNull(configFilePath))
                {
                        log.info("config file path unset, use default.");
                        configFilePath = DEFAULT_CONFIG_FILE_PATH;
                }
                dbGrids = DBGridEngine.getDefaultInstance().getDBGrids();
        }

        /**
         * log打印初始化成功的提示信息
         */
        protected void logInfo()
        {
                if (log.isInfoEnabled())
                        log.info("DBGrid engine initialized ");
        }

        protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                        IOException
        {
                // 输出debug级别的request信息用于调试
                if (log.isDebugEnabled())
                {
                        log.debug(WebUtil.generateRequestDebugInfo(request));
                }

                DBGrid dbgrid = extractParameter(request);
                if (dbgrid == null)
                {
                        return;
                } else
                {
                        listDataAndForward(dbgrid, request, response);
                }
        }

        /**
         * 如果指定了dbgrid的forwardURL，则跳转到该URL。否则而根据gridtype跳转到相应的页面
         */
        protected void forward(DBGrid dbgrid, HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
        {
                // 2009.02.14 肖 add
                if (dbgrid.getForwardURL() != null)
                        request.getRequestDispatcher(dbgrid.getForwardURL() + generateParameter(dbgrid)).forward(
                                        request, response);
                else
                        request.getRequestDispatcher(urls[dbgrid.getGridType()] + generateParameter(dbgrid)).forward(
                                        request, response);
        }

        /**
         * 根据request中名称为dbgrid的参数名称，将此名称存储在session中并返回该名称。
         * 如果存在名称为dbgrid的非空参数，则设置到session的属性CurrentGridName中。
         * 如果不存在此参数，或者此参数为空，则使用原session中的CurrentGridName值。
         */
        @Deprecated
        protected String extractGridName(HttpServletRequest request, HttpSession hs)
        {
                String gridName = DBGridUtils.INSTANCE.getDbgridName(request.getRequestURI());
                log.debug("dbgridName=" + gridName);
                return gridName;
        }

        /**
         * @return dbgrid中的currpage、recnums、startindex三个参数组成的url参数格式的字符串
         */
        protected String generateParameter(DBGrid dbgrid)
        {
                StringBuffer tsb = new StringBuffer();
                tsb.append("?pages=").append(dbgrid.getPages()).append("&currpage=").append(dbgrid.getCurrentPage())
                                .append("&recnums=").append(dbgrid.getRecordNum()).append("&startindex=")
                                .append(dbgrid.getStartRecordNum());
                return tsb.toString();
        }

        /**
         * 根据当前上下文和传入的参数生成此次请求需要的dbgrid
         */
        protected DBGrid extractParameter(HttpServletRequest request)
        {

                
                int curpage;
                String isSameWhere = WebUtil.nvl(request.getParameter("samewhere"), "false");
                String page = WebUtil.nvl(request.getParameter("page"), "1");
                String orderby = request.getParameter("orderby");
                String queryType = request.getParameter("queryType");

                HttpSession hs = request.getSession(true);
                String gridName = DBGridUtils.INSTANCE.getDbgridName(request.getRequestURI());
                DBGrid dbgrid = dbGrids.getDBGrid(gridName);
                
                if (dbgrid == null)
                {
                        log.error("No such dbgrid :" + gridName);
                        return null;
                }
                
                log.debug(dbgrid.getWhereSQL());
                
                log.debug("根据前台传入的参数，显示第{}页", page);
                
                try
                {
                        curpage = Integer.parseInt(page);
                } catch (NumberFormatException ex)
                {
                        log.error("传入的数字格式有误", ex);
                        curpage = 1;
                }
                if (queryType != null)
                        dbgrid.setGridQueryType(queryType);
                if (orderby != null)
                        dbgrid.setGridOrderBy(orderby);
                dbgrid.setCurrentPage(curpage);

                if (dbgrid.isReparse())
                        parseGrid(request, dbgrid);

                /**
                 * "false" means the incoming request contains a new dbgrid
                 * different with the one stored in the current session.There
                 * are two conditions: one is the new dbgrid with a different
                 * name;the other is the new dbgrid with the same name but
                 * different parameters. Both all need regenerate the
                 * "WHERE SQL",that is reparsing.
                 */
                if ("false".equals(isSameWhere))
                {
                        parseGrid(request, dbgrid);
                } else
                {
                        dbgrid.setWhereSQL(WebUtil.nvl((String) hs.getAttribute("currentWhere")));
                }

                

                return dbgrid;
        }

        private void parseGrid(HttpServletRequest request, DBGrid dbgrid)
        {

                DBGridParser parser = new DBGridParser();
                parser.parse(dbgrid, request);
                parser = null;
                request.getSession().setAttribute("CurrentWhere", dbgrid.getWhereSQL());
        }

//        /**
//         * 在数据库中读取相关的数据
//         * 
//         * @param dbgrid
//         * @return
//         * @throws SQLException
//         */
//        protected  List<DBGridRow> getData(DBGrid dbgrid)
//        {
//                return dbgrid.getData();
//        }

        private void listDataAndForward(DBGrid dbgrid, HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
        {
                try
                {
                        Object result = dbgrid.getData();
                        request.setAttribute("dbgrid", dbgrid);
                        request.setAttribute("datalist", result);
                        log.debug(result.toString());
                        if (log.isInfoEnabled())
                        {
                                log.info(dbgrid.getGridName() + " SQL :\n" + dbgrid.getSQL());
                        }
                } finally
                {
                        forward(dbgrid, request, response);
                }
        }

}
