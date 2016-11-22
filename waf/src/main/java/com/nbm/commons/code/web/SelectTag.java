/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:    Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.nbm.commons.code.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.ResponseUtils;

/**
 * Custom tag that represents an HTML select element.
 * 
 * @see org.apache.struts.taglib.html.SelectTag
 * 
 *      2008.11.04 肖： 增加了对运行期动态计算的支持， 直接借助ApacheGroup对JSTL的实现，当前版本1.0
 *      当WEB-INF/lib/standard.jar升级的时候，须注意版本的兼容性。
 */
public class SelectTag extends BodyTagSupport
{
        /**
         * 
         */
        private static final long serialVersionUID = 5323387121879657949L;

        private static final Logger log = LoggerFactory.getLogger(SelectTag.class);

        public static final String BEAN_KEY = "com.younker.waf.html.BEAN";
        // public static final String SELECT_KEY =
        // "com.younker.waf.html.SELECT";//2013年马俊峰
        // OptionsTag中可以直接取得父标签select，不用依赖servletContext

        /**
         * Should multiple selections be allowed. Any non-null value will
         * trigger rendering this.
         */
        protected String multiple = null;
        /**
         * The name of the select tag
         */
        protected String name = BEAN_KEY;

        protected String tabindex = null;

        protected String disabled = null;

        protected String id = null;
        /**
         * The saved body content of this tag.
         */
        protected String saveBody = null;
        /**
         * The value to compare with for marking an option selected.
         */
        protected String value_ = null;
        protected Object value = null;

        /**
         * The style associated with this tag.
         */
        private String style = null;

        /**
         * The named style class associated with this tag.
         */
        private String styleClass = null;

        private String other = null;

        public String getOther()
        {
                return other;
        }

        public void setOther(String other)
        {
                this.other = other;
        }

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

        public String getMultiple()
        {
                return (this.multiple);
        }

        public void setMultiple(String multiple)
        {
                this.multiple = multiple;
        }

        public String getName()
        {
                return (this.name);
        }

        public void setName(String name)
        {
                this.name = name;
        }

        /**
         * Sets the tabIndex value.
         */
        public void setTabindex(String tabIndex)
        {
                this.tabindex = tabIndex;
        }

        /**
         * Returns the tabIndex value.
         */
        public String getTabindex()
        {
                return (this.tabindex);
        }

        /**
         * Return the comparison value.
         */
        public String getValue()
        {
                return this.value_;
        }

        /**
         * Set the comparison value.
         * 
         * @param value
         *                The new comparison value
         */
        public void setValue(String value_)
        {
                this.value_ = value_;
        }

        /**
         * Does the specified value match one of those we are looking for?
         * 
         * @param value
         *                Value to be compared.
         */
        public boolean isMatched(String value)
        {
                if ((this.value == null) || (value == null))
                        return false;
                // if (this.value.equals(value)
                // 2009.12.9倪玉哲修改，按照原来的写法，若this.value为非字符串时，比较必然返回false，参见String类对equals的实现
                // 这样修改不会但来错误，但是是否能解决问题有待验证
                if (this.value.toString().equals(value.toString()))
                        return true;
                else
                        return false;
        }

        /**
         * Render the beginning of this select tag.
         * 
         * @exception JspException
         *                    if a JSP exception has occurred
         */
        public int doStartTag() throws JspException
        {

                evaluateExpressions();
                ResponseUtils.write(pageContext, renderSelectStartElement());
                // Store this tag itself as a page attribute
                // pageContext.setAttribute(SELECT_KEY, this);//2013年马俊峰
                // OptionsTag中可以直接取得父标签select，不用依赖servletContext
                return (EVAL_BODY_BUFFERED);
        }

        private void evaluateExpressions()
        {
                if (this.value_ != null && this.value_.length() > 0)
                {
                        try
                        {
                                this.value = ExpressionUtil.evalNotNull("select", "value", this.value_, Object.class,
                                                this, pageContext);
                        } catch (Exception e)
                        {
                                this.value = null;
                                // 如果值为空的话，将打印错误日志；某些情况下，可能存在大量的空值，继续输出日志会影响其可读性
                                // log.error("evaluate select tag expression error:"+e.getMessage());
                        }
                }
        }

        /**
         * Create an appropriate select start element based on our parameters.
         * 
         * @throws JspException
         */
        protected String renderSelectStartElement() throws JspException
        {
                StringBuffer results = new StringBuffer("<select");

                results.append(" name=\"");
                results.append(name);
                results.append("\"");

                if (id != null)
                {
                        results.append(" id=\"" + id + "\"");
                }

                if (multiple != null)
                {
                        results.append(" multiple=\"multiple\"");
                }
                if (style != null)
                {
                        results.append(" style=\"");
                        results.append(style);
                        results.append("\"");
                }
                if (styleClass != null)
                {
                        results.append(" class=\"");
                        results.append(styleClass);
                        results.append("\"");
                }

                if (tabindex != null)
                {
                        results.append(" tabindex=\"");
                        results.append(tabindex);
                        results.append("\"");
                }

                if (disabled != null)
                {
                        results.append(" disabled=\"");
                        results.append(disabled);
                        results.append("\"");

                }

                if (other != null)
                {
                        results.append(" " + other + " ");
                }

                results.append(">");

                return results.toString();
        }

        /**
         * Save any body content of this tag, which will generally be the
         * option(s) representing the values displayed to the user.
         * 
         * @exception JspException
         *                    if a JSP exception has occurred
         */
        public int doAfterBody() throws JspException
        {

                if (bodyContent != null)
                {
                        String value = bodyContent.getString();
                        if (value == null)
                        {
                                value = "";
                        }
                        this.saveBody = value.trim();
                }
                return (SKIP_BODY);
        }

        /**
         * Render the end of this form.
         * 
         * @exception JspException
         *                    if a JSP exception has occurred
         */
        public int doEndTag() throws JspException
        {

                // Remove the page scope attributes we created
                // pageContext.removeAttribute(SELECT_KEY); //2013年马俊峰
                // OptionsTag中可以直接取得父标签select，不用依赖servletContext

                // Render a tag representing the end of our current form
                StringBuffer results = new StringBuffer();
                if (saveBody != null)
                {
                        results.append(saveBody);
                }
                results.append("</select>");
                ResponseUtils.write(pageContext, results.toString());
                // 2011.01.07倪玉哲添加release();否则会导致莫名的value值错误………………
                release();
                return (EVAL_PAGE);
        }

        /**
         * Release any acquired resources.
         */
        public void release()
        {

                super.release();
                multiple = null;
                name = BEAN_KEY;
                saveBody = null;
                style = null;
                styleClass = null;
                value_ = null;
                value = null;
        }

        /**
         * @return the disable
         */
        public String getDisabled()
        {
                return disabled;
        }

        /**
         * @param disable
         *                the disable to set
         */
        public void setDisabled(String disabled)
        {
                this.disabled = disabled;
        }

        /**
         * @return the id
         */
        public String getId()
        {
                return id;
        }

        /**
         * @param id
         *                the id to set
         */
        public void setId(String id)
        {
                this.id = id;
        }

}
