/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.nbm.commons.code.web;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.utils.ResponseUtils;

/**
 * Tag for creating multiple &lt;select&gt; options from a ResultSet.
 * 
 * <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or later) platform.
 * 
 * @see org.apache.struts.taglib.html.optionsCollectionTag
 */

public class OptionsTag extends TagSupport
{

        private static final Logger log = LoggerFactory.getLogger(OptionsTag.class);

        /**
         * SQL query string
         */
        private String sql;

        /**
         * The style associated with this tag.
         */
        private String style = null;
        /**
         * The named style class associated with this tag.
         */
        private String styleClass = null;

        public String getStyle()
        {
                return style;
        }

        public void setStyle(String style)
        {
                this.style = style;
        }

        public String getStyleClass()
        {
                return styleClass;
        }

        public void setStyleClass(String styleClass)
        {
                this.styleClass = styleClass;
        }

        public String getSql()
        {
                return OptionsConfig.getString(sql);
        }

        public void setSql(String string)
        {
                sql = string;
        }

        /**
         * Process the start of this tag.
         * 
         * @exception JspException
         *                    if a JSP exception has occurred
         */

        public int doStartTag() throws JspException
        {
                return SKIP_BODY;
        }

        /**
         * Process the end of this tag.
         * 
         * @exception JspException
         *                    if a JSP exception has occurred
         */
        public int doEndTag() throws JspException
        {

                // Acquire the select tag we are associated with
                SelectTag selectTag = (SelectTag) this.getParent();// (SelectTag)
                                                                   // pageContext.getAttribute(SelectTag.SELECT_KEY);
                if (selectTag == null)
                {

                }
                StringBuffer sb = new StringBuffer();
                // query the database
                QueryRunner run = new QueryRunner(DataSourceProvider.instance().getDataSource());
                ResultSetHandler h = new ArrayListHandler();
                List rs = null;
                try
                {
                        log.debug("run sql[" + getSql() + "].");
                        rs = (List) run.query(getSql(), h);
                        log.debug("result:" + rs);
                } catch (SQLException ex)
                {
                        ex.printStackTrace();
                        log.error("Generating option error, sql: \n" + getSql(), ex);
                }

                if (rs != null)
                {
                        addOption(sb, "", "", false);
                        for (int i = 0; i < rs.size(); i++)
                        {
                                Object[] row = (Object[]) rs.get(i);
                                String label, value;
                                if (row.length == 1)
                                {
                                        value = String.valueOf(row[0]);
                                        label = String.valueOf(row[0]);
                                } else if (row.length > 1)
                                {
                                        value = String.valueOf(row[0]);
                                        label = String.valueOf(row[1]);
                                } else
                                {
                                        label = "";
                                        value = "";
                                }
                                addOption(sb, value, label, selectTag.isMatched(value));
                        }
                }
                // Render this element to our writer
                ResponseUtils.write(pageContext, sb.toString());
                // Evaluate the remainder of this page
                return EVAL_PAGE;
        }

        /**
         * Release any acquired resources.
         */
        public void release()
        {

                super.release();
                style = null;
                styleClass = null;
        }

        // ------------------------------------------------------ Protected
        // Methods

        /**
         * Add an option element to the specified StringBuffer based on the
         * specified parameters.
         * <p>
         * Note that this tag specifically does not support the
         * <code>styleId</code> tag attribute, which causes the HTML
         * <code>id</code> attribute to be emitted. This is because the HTML
         * specification states that all "id" attributes in a document have to
         * be unique. This tag will likely generate more than one
         * <code>option</code> element element, but it cannot use the same
         * <code>id</code> value. It's conceivable some sort of mechanism to
         * supply an array of <code>id</code> values could be devised, but that
         * doesn't seem to be worth the trouble.
         * 
         * @param sb
         *                StringBuffer accumulating our results
         * @param value
         *                Value to be returned to the server for this option
         * @param label
         *                Value to be shown to the user for this option
         * @param matched
         *                Should this value be marked as selected?
         */
        protected void addOption(StringBuffer sb, String value, String label, boolean matched)
        {

                StringBuilder result = new StringBuilder();
                result.append("<option value=\"");
                result.append(value);
                result.append("\"");
                if (matched)
                {
                        result.append(" selected=\"selected\"");
                }
                if (style != null)
                {
                        result.append(" style=\"");
                        result.append(style);
                        result.append("\"");
                }
                if (styleClass != null)
                {
                        result.append(" class=\"");
                        result.append(styleClass);
                        result.append("\"");
                }
                result.append(">");
                result.append(label);
                result.append("</option>\r\n");
                log.debug("add option[" + result + "].");
                sb.append(result);
        }

}
