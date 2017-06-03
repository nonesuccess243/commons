package com.wayeasoft.spatial.shape;

import java.io.Serializable;

public abstract class Shape implements Serializable
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        public abstract String getSqlString();

}
