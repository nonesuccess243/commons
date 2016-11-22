/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

/**
 * DBGridException.java
 */
public class DBGridException extends RuntimeException
{

        /**
         * 
         */
        private static final long serialVersionUID = -6328803751559787010L;

        public DBGridException()
        {
        }

        public DBGridException(String desc)
        {
                super(desc);
        }
}
