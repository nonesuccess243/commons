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
@WebServlet(urlPatterns =
{ "*.jsonlist", "*.jsonmeta" })
public class JsonDBGridServlet extends DBGridServlet
{

        /**
         * 
         */
        private static final long serialVersionUID = 6962937039180273943L;

        private final static Logger log = LoggerFactory.getLogger(JsonDBGridServlet.class);

        private final static Logger dbgrid_log = LoggerFactory.getLogger("dbgrid");

        private final static String JSON_URI = "jsonlist";
        private final static String JSON_META_URI = "jsonmeta";

        @Override
        public void init(ServletConfig config) throws ServletException
        {
                super.init(config);
        }

        @Override
        protected void process(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
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

        private void doMeta(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
        {
                DBGrid dbgrid = extractParameter(request);
                if (dbgrid == null)
                        return;// TODO 增加错误处理

                String info = generateJsonInfo(dbgrid);

                log.debug(info);

                response.setContentType("text/plain");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(info);

        }

        private String generateJsonInfo(DBGrid dbgrid)
        {
                List<DBGridItem> titles = dbgrid.getGridTitles();
                StringBuilder sb = new StringBuilder("[[");

                for (Iterator<DBGridItem> iter = titles.iterator(); iter.hasNext();)
                {
                        DBGridItem item = iter.next();
                        sb.append(generateTitleJson(item));
                        if (iter.hasNext())
                        {
                                sb.append(",");
                        }
                }

                sb.append("]]");

                StringBuilder result = new StringBuilder("{pageSize:\""
                                + dbgrid.getGridRowsPerPage() + "\"");
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
                sb.append("field:'").append(item.getField()).append("',title:'")
                                .append(item.getLabel()).append("'");
                if (item.getFormat().equalsIgnoreCase("h"))
                {
                        sb.append(", hidden:true");
                } else
                {
                        sb.append(", 'width':20");
                }

                sb.append("}");
                return sb.toString();

        }

        private void doData(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException
        {
                if (log.isDebugEnabled())
                {
                        log.debug(WebUtil.generateRequestDebugInfo(request));
                }

                DBGrid dbgrid = extractParameter(request);
                if (dbgrid == null)
                        return;// TODO 增加错误处理
                List<DBGridRow> results = dbgrid.getData();

                dbgrid_log.debug("dbgrid[{}], sessionId[{}]  SQL:\n{}", dbgrid.getGridName(),request.getSession().getId(),
                                dbgrid.getSQL());

                List<DBGridItem> titles = dbgrid.getGridTitles();

                String result = generateJsonData(dbgrid.getRecordNum(), results, titles);

                dbgrid_log.debug("recordNum: " + dbgrid.getRecordNum());

                response.setContentType("text/plain");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(result);

        }

        private String generateJsonData(int recordNum,
                        List<DBGridRow> results,
                        List<DBGridItem> titles)
        {
                if (results == null)
                        throw new IllegalArgumentException("查询结果为空");

                StringBuilder sb = new StringBuilder("{");

                sb.append("\"total\":" + recordNum);

                sb.append(",\"rows\":[");

                for (Iterator<DBGridRow> iter = results.iterator(); iter.hasNext();)
                {
                        sb.append("{");
                        DBGridRow row = iter.next();
                        List<DBGridItem> items = row.getItems();
                        for (Iterator<DBGridItem> itemsIter = items.iterator(), titlesIter = titles
                                        .iterator(); itemsIter.hasNext() && titlesIter.hasNext();)
                        {
                                DBGridItem item = itemsIter.next();
                                if (item.getLabel() == null)
                                {
                                        item.setLabel("&nbsp;");
                                }
                                DBGridItem title = titlesIter.next();
                                sb.append("\"" + title.getField() + "\":").append(
                                                "\"" + formatStringJson(item.getLabel()) + "\"");
                                if (itemsIter.hasNext() && titlesIter.hasNext())
                                        sb.append(",");
                        }
                        sb.append("}");
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
                return param.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("\\r\\n", "</br>")
                                .replaceAll("\\n", "</br>");
        }
        
        public static void main(String[] args)
        {
                String s = "\"\"徐静\r\n\r\n\r\n徐静";
                s = s.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("\\r\\n", "</br>")
                .replaceAll("\\n", "</br>");
                
                System.out.println(s);
        }
        

}
