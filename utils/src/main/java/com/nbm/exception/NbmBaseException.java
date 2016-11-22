/***********************************************
 * Title:       NbmBaseException.java
 * Description: NbmBaseException.java
 * Create Date: 2013-2-2
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.exception;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class NbmBaseException extends Exception
{

        /**
         * 
         */
        private static final long serialVersionUID = 3539429403333019147L;

        public NbmBaseException()
        {
                super();
        }

        public NbmBaseException(String errorMessage)
        {
                super(errorMessage);
                this.errorMessage = errorMessage;
        }

        protected String errorMessage;

        private Map<String, Object> infos = new HashMap<String, Object>(10);

        public NbmBaseException set(String key, Object value)
        {
                infos.put(key, value);
                return this;
        }

        public Object get(String key)
        {
                return infos.get(key);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Throwable#getMessage()
         */
        @Override
        public final String getMessage()
        {
                StringBuilder sb = new StringBuilder(errorMessage + "[");
                for (Map.Entry<String, Object> entries : infos.entrySet())
                {
                        sb.append(entries.getKey() + ": " + entries.getValue() + ", ");
                }
                sb.append("].");

                return sb.toString();
        }
        
        public final String getErrorMessage()
        {
                return errorMessage;
        }

}
