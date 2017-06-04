package com.wayeasoft.spatial.shape;

import org.apache.commons.beanutils.Converter;

import com.nbm.core.modeldriven.DbType;
import com.nbm.core.modeldriven.anno.DbTypeAnno;
import com.nbm.core.modeldriven.anno.DbTemplate;

@DbTypeAnno(DbType.POINT)
@DbTemplate(fetchPrefix="ST_AsText(", 
        fetchSuffix=")", 
        populatePrefix = "ST_GeomFromText(", 
        populateSuffix=")")
public class Point extends Shape
{
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        
        private double x;
        private double y;

        
        public Point(){}
        
        
        public double getX()
        {
                return x;
        }

        public void setX(double x)
        {
                this.x = x;
        }

        public double getY()
        {
                return y;
        }

        public void setY(double y)
        {
                this.y = y;
        }

        @Override
        public String getSqlString()
        {
                return "POINT(" + x + " " + y + ")";
        }


        @Override
        public Converter getConverter()
        {
                return new Converter()
                {
                        @Override
                        public Object convert(Class type, Object value)
                        {
                                Point point = new Point();
                                String[] s = ((String)value).replace("POINT", "").replace("(", "").replace(")", "").split(" ");
                                point.setX(Double.parseDouble(s[0]));
                                point.setY(Double.parseDouble(s[1]));
                                return point;
                        }
                };
        }

}
