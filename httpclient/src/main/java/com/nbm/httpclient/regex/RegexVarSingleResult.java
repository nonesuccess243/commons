/***********************************************
 * Title:       RegexVarResult.java
 * Description: RegexVarResult.java
 * Create Date: 2012-9-8
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.regex;

import java.util.Map;

/**
 *
 */
public class RegexVarSingleResult
{
        private int startIndex;
        private int endIndex;
        private String resultString;
        private Map<String, String> result;

        /**
         * @return the startIndex
         */
        public int getStartIndex()
        {
                return startIndex;
        }

        /**
         * @param startIndex
         *                the startIndex to set
         */
        public void setStartIndex(int startIndex)
        {
                this.startIndex = startIndex;
        }

        /**
         * @return the endIndex
         */
        public int getEndIndex()
        {
                return endIndex;
        }

        /**
         * @param endIndex
         *                the endIndex to set
         */
        public void setEndIndex(int endIndex)
        {
                this.endIndex = endIndex;
        }

        /**
         * @return the resultString
         */
        public String getResultString()
        {
                return resultString;
        }

        /**
         * @param resultString
         *                the resultString to set
         */
        public void setResultString(String resultString)
        {
                this.resultString = resultString;
        }

        /**
         * @return the result
         */
        public Map<String, String> getResult()
        {
                return result;
        }

        /**
         * @param result
         *                the result to set
         */
        public void setResult(Map<String, String> result)
        {
                this.result = result;
        }

}
