/***********************************************
 * Title:       ReponseModel.java
 * Description: ReponseModel.java
 * Create Date: 2012-12-29
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.model;

import java.util.Map;

/**
 *
 */
public class ResponseModel
{
        /**
         * 解析的变量组
         */
        private Map<String, String> vars;

        /**
         * 响应正文内容
         */
        private String resultContent;

        @Override
        public String toString()
        {
                return "ResponseModel [" + (vars != null ? "vars=" + vars + ", " : "")
                                + (resultContent != null ? "resultContent=" + resultContent : "")
                                + "]";
        }

        /**
         * @return the vars
         */
        public Map<String, String> getVars()
        {
                return vars;
        }

        /**
         * @param vars
         *                the vars to set
         */
        public void setVars(Map<String, String> vars)
        {
                this.vars = vars;
        }

        /**
         * @return the resultContent
         */
        public String getResultContent()
        {
                return resultContent;
        }

        /**
         * @param resultContent
         *                the resultContent to set
         */
        public void setResultContent(String resultContent)
        {
                this.resultContent = resultContent;
        }

}
