package com.younker.waf.dbgrid;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

import com.younker.waf.dbgrid.utils.DBGridUtils;

/**
 * Servlet implementation class DbgridExportServlet
 */
@WebServlet("*.dbxls")
public class DbgridExportServlet extends HttpServlet
{
        private static final long serialVersionUID = 1L;

        /**
         * @see HttpServlet#HttpServlet()
         */
        public DbgridExportServlet()
        {
                super();
                // TODO Auto-generated constructor stub
        }

        /**
         * @see HttpServlet#doGet(HttpServletRequest request,
         *      HttpServletResponse response)
         */
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
        {
                doPost(request, response);
        }

        /**
         * @see HttpServlet#doPost(HttpServletRequest request,
         *      HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
        {
                
                DBGrid dbgrid = DBGridUtils.INSTANCE.getParsedDbgrid(DBGridUtils.INSTANCE.getDbgridName(request.getRequestURI()));
                
                String fileName = StringUtils.defaultIfBlank(dbgrid.getGridTitle(), "报表导出") + "-" + 
//                                RuntimeUserUtils.INSTANCE.getCurrentEmployee().getOrg().getName() + "-" +  
                                new LocalDate().toString() + ".xls";
//                
                //String fileName = "辖区内育龄妇女列表-大港区-2014-05-23.xls";
                try
                {
                        // 清空response
                        response.reset();
                        // 设置response的Header
                        response.addHeader("Content-Disposition", "attachment;filename="
                                        + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
                        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                        response.setContentType("application/vnd.ms-excel");
                        
                        dbgrid.generateExcel(toClient);
                       
                        toClient.flush();
                        toClient.close();
                } catch (IOException ex)
                {
                        //TODO
                        ex.printStackTrace();
                }
        }
}
