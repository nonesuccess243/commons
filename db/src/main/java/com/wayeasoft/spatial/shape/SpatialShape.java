package com.wayeasoft.spatial.shape;

import com.nbm.core.modeldriven.ComplexDbType;

public abstract class SpatialShape extends ComplexDbType 
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        public abstract String getSqlString();

}
