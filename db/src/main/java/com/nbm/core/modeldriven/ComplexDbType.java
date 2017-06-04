package com.nbm.core.modeldriven;

import java.io.Serializable;

import org.apache.commons.beanutils.Converter;

public abstract class ComplexDbType implements Serializable
{
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public abstract Converter getConverter();

}
