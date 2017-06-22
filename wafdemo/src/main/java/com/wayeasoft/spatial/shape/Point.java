package com.wayeasoft.spatial.shape;

import com.nbm.core.modeldriven.DbType;
import com.nbm.core.modeldriven.anno.DbTypeAnno;

@DbTypeAnno(DbType.POINT)
public class Point extends Shape
{
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        
        private double x;
        private double y;

        
        public Point(){}
        
        public Point(String pointString)
        {
                String[] s = pointString.replace("POINT", "").replaceAll("(", "").replaceAll(")", "").split(" ");
                this.x = Double.parseDouble(s[0]);
                this.y = Double.parseDouble(s[1]);
                
        }
        
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

}
