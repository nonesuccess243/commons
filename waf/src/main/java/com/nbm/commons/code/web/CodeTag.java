package com.nbm.commons.code.web;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.utils.ResponseUtils;

public class CodeTag extends TagSupport
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private static final Logger log = LoggerFactory.getLogger(CodeTag.class);

        private static final String QUERY_STR = "SELECT NAME FROM S_CODE WHERE TYPECODE='#TYPECODE#' AND CODE='#CODE#'";
        private static final String TYPECODE_STR = "#TYPECODE#";
        private static final String CODE_STR = "#CODE#";

        private String id;
        private String typeCode;
        private String code;

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
         */
        @Override
        public int doStartTag() throws JspException
        {
                String name = "";
                if (StringUtils.isBlank(code))
                {
                        name = "";
                } else
                {
                        QueryRunner run = new QueryRunner(DataSourceProvider.instance()
                                        .getDataSource());
                        ResultSetHandler h = new ArrayListHandler();
                        String sql = QUERY_STR.replace(TYPECODE_STR, typeCode).replace(CODE_STR,
                                        code);
                        try
                        {
                                List<Object[]> results = (List<Object[]>) run.query(sql, h);
                                if (results.size() == 0)
                                {
                                        log.error("查询标签结果为空[sql=" + sql + "].");
                                        name = "";
                                } else
                                {
                                        name = results.get(0)[0].toString();
                                }
                        } catch (SQLException e)
                        {
                                log.error("gereral result list error[sql=" + sql + "].", e);
                        }
                }
                String result = "<span id=\"" + id + "\" >" + name + "</span>";

                ResponseUtils.write(pageContext, result);

                return super.doStartTag();
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

        /**
         * @return the typeCode
         */
        public String getTypeCode()
        {
                return typeCode;
        }

        /**
         * @param typeCode
         *                the typeCode to set
         */
        public void setTypeCode(String typeCode)
        {
                this.typeCode = typeCode;
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

}
