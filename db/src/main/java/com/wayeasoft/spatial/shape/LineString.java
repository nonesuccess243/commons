package com.wayeasoft.spatial.shape;


import org.apache.commons.beanutils.Converter;

import com.nbm.core.modeldriven.DbType;
import com.nbm.core.modeldriven.anno.DbTemplate;
import com.nbm.core.modeldriven.anno.DbTypeAnno;

@DbTypeAnno(DbType.LINESTRING)
@DbTemplate(fetchPrefix="ST_AsText(", 
        fetchSuffix=")", 
        populatePrefix = "ST_GeomFromText(", 
        populateSuffix=")")
public class LineString extends SpatialShape
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private Point[] points;
        
        public LineString( Point... points)
        {
                this.points = points;
        }

        @Override
        public String getSqlString()
        {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public Converter getConverter()
        {
                // TODO Auto-generated method stub
                return null;
        }

}
