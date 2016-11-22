/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.WebUtil;

/**
 * DropDownListServlet is the enter point of dropdown list
 */
public class DropDownListServlet extends DBGridServlet
{
        private static final Logger log = LoggerFactory.getLogger(DropDownListServlet.class);
        static final long serialVersionUID = 1L;

        protected void generateDbGrids(ServletConfig config)
        {
                ServletContext ctx = config.getServletContext();
                try
                {
                        URL ruleURL = ctx.getResource("/WEB-INF/config/DBGridRule.xml");
                        URL gridURL = ctx.getResource("/WEB-INF/config/dropdownlists.xml");
                        Digester digester = DigesterLoader.createDigester(ruleURL);
                        dbGrids = (DBGrids) digester.parse(gridURL.toString());
                } catch (Exception ex)
                {
                        log.error("load or parse the dropdownlist configuration file error", ex);
                }
        }

        protected void logInfo()
        {
                if (log.isInfoEnabled())
                        log.info("DropdownList engine initialized ");
        }

        protected void forward(DBGrid dbgrid, HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
        {
                request.getRequestDispatcher("/component/dropdownlist/dbgrid.jsp" + generateParameter(dbgrid)).forward(
                                request, response);
        }

        protected String extractGridName(HttpServletRequest request, HttpSession hs)
        {
                String gridName = WebUtil.nvl(request.getParameter("dbgrid"));

                if (!gridName.equals(""))
                {
                        if (hs.getAttribute("dropDownListName") != null)
                        {
                                hs.removeAttribute("dropDownListName");
                        }
                        hs.setAttribute("dropDownListName", gridName);
                }
                gridName = (String) hs.getAttribute("dropDownListName");
                return gridName;
        }
}
