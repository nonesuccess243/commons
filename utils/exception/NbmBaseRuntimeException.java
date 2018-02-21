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
public class NbmBaseRuntimeException extends RuntimeException
{

        /**
         * 
         */
        private static final long serialVersionUID = 3539429403333019147L;

        public NbmBaseRuntimeException()
        {
                super();
        }

        public NbmBaseRuntimeException(String errorMessage)
        {
                super(errorMessage);
                this.errorMessage = errorMessage;
        }

        /**
         * @param message
         * @param cause
         */
        public NbmBaseRuntimeException(String message, Throwable cause)
        {
                super(message, cause);
                this.errorMessage = message;
        }

        protected String errorMessage;

        private Map<String, Object> infos = new HashMap<String, Object>(10);

        public NbmBaseRuntimeException set(String key, Object value)
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

}
