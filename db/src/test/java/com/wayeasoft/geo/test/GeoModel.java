package com.wayeasoft.geo.test;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.PureModel;
import com.wayeasoft.spatial.shape.LineString;
import com.wayeasoft.spatial.shape.Point;

public class GeoModel extends PureModel
{
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private Point point;
        private LineString lineString;
        

        public Point getPoint()
        {
                return point;
        }

        public void setPoint(Point point)
        {
                this.point = point;
        }
        
        public static void main(String[] args)
        {
                Db.MYSQL.generate(GeoModel.class);
        }

}
