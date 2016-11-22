/***********************************************
 * Title:       DateFormatNotInStandardException.java
 * Description: DateFormatNotInStandardException.java
 * Create Date: 2013-1-27
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.younker.waf.utils.date;

import com.nbm.exception.NbmBaseRuntimeException;


/**
 *
 */
public class DateNotInStandardException extends NbmBaseRuntimeException
{
        
        /**
         * 
         */
        private static final long serialVersionUID = 7928902397166584363L;
        private String dateString;
        private String errorMessage;
        
        public DateNotInStandardException( String dateString, String errorMessage )
        {
                super("日期不符合规范[" + errorMessage + "]");
                this.dateString = dateString;
                this.errorMessage = errorMessage;
        }

        /**
         * @return the dateString
         */
        public String getDateString()
        {
                return dateString;
        }
        
        public String getErrorMessage()
        {
                return errorMessage;
        }

}
