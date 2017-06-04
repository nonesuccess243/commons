package com.nbm.core.modeldriven;

import java.io.Serializable;

import org.apache.commons.beanutils.Converter;

public abstract class ComplexDbType implements Serializable
{
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        
        /**
         * 复杂类型在数据库中以一个字符串形式作为查询结果，再采用converter进行转换
         * @return
         */
        public abstract Converter getConverter();

}
