package com.younker.waf.dbgrid;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.WebUtil;

/**
 * 用于相应前台获取dbgrid数据的ajax请求，以Json形式返回。
 * 共监听两种形式的url-pattern，jsonlist返回数据，jsoncolumnlist返回表头
 * 
 * @author computer
 * 
 */
@WebServlet(name = "jqGridDbGridServlet", urlPatterns =
{ "*.jqmeta", "*.jqlist" })
public class JqGridServlet extends DBGridServlet
{

        /**
         * 
         */
        private static final long serialVersionUID = 6962937039180273943L;

        private final static Logger log = LoggerFactory.getLogger(JqGridServlet.class);

        private final static String JSON_URI = "jqlist";
        private final static String JSON_META_URI = "jqmeta";

        @Override
        public void init(ServletConfig config) throws ServletException
        {
                super.init(config);
        }

        @Override
        protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                        IOException
        {

                // 输出debug级别的request信息用于调试
                if (log.isDebugEnabled())
                {
                        log.debug(WebUtil.generateRequestDebugInfo(request));
                }

                // dispatch
                log.debug(request.getRequestURI());
                String uri = request.getRequestURI();
                if (uri.indexOf(JSON_URI) != -1)
                {
                        doData(request, response);

                } else if (uri.indexOf(JSON_META_URI) != -1)
                {
                        doMeta(request, response);
                } else
                {
                        log.error("request url unsopport[uri=" + uri + "].");
                        return;
                }
        }

        private void doMeta(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                        IOException
        {
                DBGrid dbgrid = extractParameter(request);
                if (dbgrid == null)
                        return;// TODO 增加错误处理

                String info = generateJsonInfo(dbgrid);
                // 访问控制-----2013-4-29
                judgePath(request, response, dbgrid);
                log.debug(info);

                // 根据路径判断决定是否允许访问(访问路径控制)

                response.setContentType("text/plain");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(info);

        }

        private String generateJsonInfo(DBGrid dbgrid)
        {
                List<DBGridItem> titles = dbgrid.getGridTitles();
                StringBuilder sb = new StringBuilder("[");

                for (Iterator<DBGridItem> iter = titles.iterator(); iter.hasNext();)
                {
                        DBGridItem item = iter.next();
                        sb.append(generateTitleJson(item));
                        if (iter.hasNext())
                        {
                                sb.append(",");
                        }
                }

                sb.append("]");

                StringBuilder result = new StringBuilder("{pageSize:\"" + dbgrid.getGridRowsPerPage()
                                + "\",lastpage:\"" + dbgrid.getPages() + "\",recNum:\"" + dbgrid.getRecordNum() + "\"");
                log.debug("title=" + dbgrid.getGridTitle());
                if (dbgrid.getGridTitle() != null)
                {
                        result.append(",title:\"" + dbgrid.getGridTitle() + "\"");
                }
                result.append(",columns: " + sb + "}");

                return result.toString();
        }

        private String generateTitleJson(DBGridItem item)
        {
                StringBuilder sb = new StringBuilder("{");
                sb.append("name:'").append(item.getField()).append("', ").append("index:'").append(item.getField())
                                .append("', ").append("title:'").append(item.getLabel()).append("'");

                if (item.getFormat().equalsIgnoreCase("h"))
                {
                        sb.append(", hidden:true, width:0");
                } else
                {
                        sb.append(", 'width':100, hidden:false");
                }

                sb.append("}");
                // System.out.println("\n"+sb);
                return sb.toString();

        }

        private void doData(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                        IOException
        {
                if (log.isDebugEnabled())
                {
                        log.debug(WebUtil.generateRequestDebugInfo(request));
                }

                DBGrid dbgrid = extractParameter(request);
                if (dbgrid == null)
                        return;// TODO 增加错误处理
                dbgrid.generateSQL();
                // 访问控制-----2013-4-29
                judgePath(request, response, dbgrid);
                List<DBGridRow> results = dbgrid.getData();
                if (log.isInfoEnabled())
                {
                        log.info(dbgrid.getGridName() + " SQL :\n" + dbgrid.getSQL());
                }
                List<DBGridItem> titles = dbgrid.getGridTitles();

                String result = generateJsonData(dbgrid, results, titles);

                log.debug(result);

                response.setContentType("text/plain");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(result);

        }

        private String generateJsonData(DBGrid dbgrid, List<DBGridRow> results, List<DBGridItem> titles)
        {
                if (results == null)
                        throw new IllegalArgumentException("查询结果为空");

                StringBuilder sb = new StringBuilder("{");

                sb.append("\"records\":" + dbgrid.getRecordNum() + ",");// 总条数

                sb.append("\"total\":" + dbgrid.getPages() + ",");// 总页数

                sb.append("\"page\":" + dbgrid.getCurrentPage() + ",");// 当前页数

                sb.append("\"rows\":[");
                for (Iterator<DBGridRow> iter = results.iterator(); iter.hasNext();)
                {

                        DBGridRow row = iter.next();
                        // 修改添加ID编号------------2013-4-12----------
                        sb.append("{\"id\":\"" + row.getId() + "\", \"cell\":[");
                        // ---------------------------------------------
                        List<DBGridItem> items = row.getItems();
                        for (Iterator<DBGridItem> itemsIter = items.iterator(), titlesIter = titles.iterator(); itemsIter
                                        .hasNext() && titlesIter.hasNext();)
                        {
                                DBGridItem item = itemsIter.next();
                                if (item.getLabel() == null)
                                {
                                        item.setLabel("&nbsp;");
                                }
                                DBGridItem title = titlesIter.next();
                                sb.append("\"" + formatStringJson(item.getLabel()) + "\"");
                                if (itemsIter.hasNext() && titlesIter.hasNext())
                                        sb.append(",");
                        }
                        sb.append("]}");
                        if (iter.hasNext())
                                sb.append(",");
                }

                sb.append("]");

                sb.append("}");
                return sb.toString();

        }

        /**
         * 对字符串进行转义，用以符合前台datagrid中的json格式 对\,",\r\n,\n进行了转义，\r\n和\n转换为html的换行br
         * 
         * @param param
         * @return
         */
        public String formatStringJson(String param)
        {
                if (param == null)
                        return null;
                return param.replace("\\", "\\\\").replace("\"", "\\\"").replace("\\r\\n", "</br>")
                                .replace("\\n", "</br>");
        }

        /**
         * 根据DBGrid的访问名判断访问判断
         * 
         * @throws IOException
         * 
         */
        public boolean judgePath(HttpServletRequest request, HttpServletResponse resopnse, DBGrid dbgrid)
                        throws IOException
        {
                String url = request.getRequestURI();
                String gName = "";
                if (dbgrid.getGridName().indexOf("/") >= 0)
                {
                        gName = dbgrid.getGridName().substring(0, dbgrid.getGridName().lastIndexOf("/") + 1);
                } else
                        // 正常访问
                        return true;
                if (url.indexOf(gName) >= 0)
                {
                        // 正常访问
                        return true;
                } else
                {
                        // 拒绝访问
                        reject(resopnse);
                        return false;
                }
        }

        /**
         * 拒绝访问 FIXME 临时测试用, 实现方式可再讨论
         * 
         * @param response
         * @throws IOException
         */
        public void reject(HttpServletResponse response) throws IOException
        {
                log.info("拒绝访问");
                // 跳到404, 可设置其他方式拒绝访问
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

}
