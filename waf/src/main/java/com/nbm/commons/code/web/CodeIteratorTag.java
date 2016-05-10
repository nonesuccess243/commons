package com.nbm.commons.code.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 进一步扩展ywaf的select标签，通过在option.properties中指定sql.xxx的相关语句，在页面中指定sql语句的名称，
 * 循环执行标签主题的内容。
 * 
 */
public class CodeIteratorTag extends TagSupport
{
        private String sql;
        private String code;
        private String _code;
        private String name;
        private String _name;
        private List<Object[]> resultList;
        private Iterator<Object[]> results;

        /**
         * 
         */
        private static final long serialVersionUID = -5236394187227315527L;

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
         */
        @Override
        public int doStartTag() throws JspException
        {
                resultList = CodeSQLConfig.getInstance().getResultList(sql);
                if (resultList.size() == 0)
                        return Tag.SKIP_BODY;
                else
                {
                        results = resultList.iterator();

                        // 此处左值必须为Object[]，不能为String[]，否则会产生ClassCastException。但results的类型为List<String[]>，诡异。是否与java泛型只是一种编译期手段有关？
                        Object[] temp = results.next();

                        if (temp.length == 1)
                        {
                                _code = temp[0].toString();
                                _name = temp[0].toString();
                        } else if (temp.length == 2)
                        {
                                _code = temp[0].toString();
                                _name = temp[1].toString();
                        }
                        save();
                        return Tag.EVAL_BODY_INCLUDE;
                }

        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.jsp.tagext.TagSupport#doAfterBody()
         */
        @Override
        public int doAfterBody() throws JspException
        {
                if (results.hasNext())
                {
                        Object[] temp = results.next();
                        if (temp.length == 1)
                        {
                                _code = temp[0].toString();
                                _name = temp[0].toString();
                        } else if (temp.length == 2)
                        {
                                _code = temp[0].toString();
                                _name = temp[1].toString();
                        }
                        save();
                        return EVAL_BODY_AGAIN;
                } else
                {
                        return Tag.SKIP_BODY;
                }
        }

        private void save()
        {
                if (code == null)
                {
                        pageContext.removeAttribute(code, PageContext.PAGE_SCOPE);
                        pageContext.removeAttribute(name, PageContext.PAGE_SCOPE);
                } else
                {
                        pageContext.setAttribute(code, _code);
                        pageContext.setAttribute(name, _name);
                }
        }

        /**
         * @return the sql
         */
        public String getSql()
        {
                return sql;
        }

        /**
         * @param sql
         *                the sql to set
         */
        public void setSql(String sql)
        {
                this.sql = sql;
        }

        /**
         * @return the code
         */
        public String getCode()
        {
                return code;
        }

        /**
         * @param code
         *                the code to set
         */
        public void setCode(String code)
        {
                this.code = code;
        }

        /**
         * @return the name
         */
        public String getName()
        {
                return name;
        }

        /**
         * @param name
         *                the name to set
         */
        public void setName(String name)
        {
                this.name = name;
        }

}
