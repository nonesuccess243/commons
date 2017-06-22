package com.wayeasoft.core.modeldriven.dao;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.PureModel;


public class CommonDaoTestModel extends PureModel
{
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String name;
        
        private int age;
        
        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public int getAge()
        {
                return age;
        }

        public void setAge(int age)
        {
                this.age = age;
        }

        public static void main(String[] args)
        {
                Db.MYSQL.generate(CommonDaoTestModel.class);
        }
        
}
