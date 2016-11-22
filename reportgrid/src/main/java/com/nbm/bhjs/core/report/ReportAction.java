package com.nbm.bhjs.core.report;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.waf.core.BasicAction;


public class ReportAction extends BasicAction
{

        private final static Logger log = LoggerFactory.getLogger(ReportAction.class);
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private int total;
        private List<Map<String, ?>> rows;
        private List<Map<String, ?>> footer;
        private boolean showFooter = false;

        @Override
        public String execute() throws ClassNotFoundException, InstantiationException,
                        IllegalAccessException
        {
                rows = GridDataProvider.getProvider(handleName(ServletActionContext
                                .getRequest().getServletPath())).queryData();

                if(showFooter)
                {
                        footer = GridDataProvider.getProvider(handleName(ServletActionContext
                                        .getRequest().getServletPath())).queryFootData();
                }
               
                total = rows.size();
                return "data";
        }
        
        
        /**
         * 导出excel的处理函数，未完成
         * @return
         */
        public String exportExcel()
        {
                
                //TODO 完成此函数
                return "success";
        }

        public int getTotal()
        {
                return total;
        }

        public void setTotal(int total)
        {
                this.total = total;
        }

        public List<Map<String, ?>> getRows()
        {
                return rows;
        }

        public void setRows(List<Map<String, ?>> rows)
        {
                this.rows = rows;
        }

        public List<Map<String, ?>> getFooter()
        {
                return footer;
        }

        public void setFooter(List<Map<String, ?>> footer)
        {
                this.footer = footer;
        }
        
        private static String handleName(String origin)
        {
                if (origin.startsWith("/"))
                        origin = origin.substring(1);
                origin = origin.replace("/report.action", "");
                origin = origin.replaceAll("/", ".");
                origin = handleJavaName(origin.toUpperCase());
                origin = "com.nbm.bhjs." + origin + "Report";

                int lastIndex = origin.length() - 1;
                while (lastIndex >= 0)
                {
                        if (origin.charAt(lastIndex) == '.')
                        {
                                break;
                        }
                        lastIndex--;
                }

                origin = origin.substring(0, lastIndex).toLowerCase() + "."
                                + Character.toUpperCase(origin.charAt(lastIndex + 1))
                                + origin.substring(lastIndex + 2);

                return origin;
        }

        /**
         * 下划线分割的命名转换为首字母大写的驼峰命名法。 如SOME_TABLE转换为SomeTable
         */
        private static String handleJavaName(String tableName)
        {
                char[] chars = tableName.toCharArray();

                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < chars.length; i++)
                {
                        if (chars[i] == '_')
                                continue;
                        if (i == 0 || (i > 0 && chars[i - 1] == '_'))
                        {
                                sb.append(Character.toUpperCase(chars[i]));
                        } else
                        {
                                sb.append(Character.toLowerCase(chars[i]));
                        }
                }
                return sb.toString();
        }

        public static void main(String[] args) throws ClassNotFoundException,
                        InstantiationException, IllegalAccessException
        {
                String name = handleName("/woman_manage/search_info/work_summary/first_child/report.action");

        }

        public boolean isShowFooter()
        {
                return showFooter;
        }

        public void setShowFooter(boolean showFooter)
        {
                this.showFooter = showFooter;
        }
}
