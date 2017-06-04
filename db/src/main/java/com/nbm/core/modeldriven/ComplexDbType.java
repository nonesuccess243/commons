package com.nbm.core.modeldriven;

import java.io.Serializable;

import org.apache.commons.beanutils.Converter;

public abstract class ComplexDbType implements Serializable
{
        
        public abstract Converter getConverter();

}
