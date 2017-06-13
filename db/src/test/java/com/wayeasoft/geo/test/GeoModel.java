package com.wayeasoft.geo.test;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.enums.YesOrNo;
import com.wayeasoft.spatial.shape.Point;

public class GeoModel extends PureModel
{
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private Point point;
        
        private String name;
        private Integer number;
        private Double doubleNumber;
        private YesOrNo yesOrNo;
        

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public Integer getNumber()
        {
                return number;
        }

        public void setNumber(Integer number)
        {
                this.number = number;
        }

        public Double getDoubleNumber()
        {
                return doubleNumber;
        }

        public void setDoubleNumber(Double doubleNumber)
        {
                this.doubleNumber = doubleNumber;
        }

        public YesOrNo getYesOrNo()
        {
                return yesOrNo;
        }

        public void setYesOrNo(YesOrNo yesOrNo)
        {
                this.yesOrNo = yesOrNo;
        }

        public Point getPoint()
        {
                return point;
        }

        public void setPoint(Point point)
        {
                this.point = point;
        }
        
        @Override
        public String toString()
        {
                return "GeoModel [point=" + point + ", name=" + name + ", number=" + number + ", doubleNumber="
                                + doubleNumber + ", yesOrNo=" + yesOrNo + "]";
        }
        
        public static void main(String[] args)
        {
                Db.MYSQL.generate(GeoModel.class);
        }

}
