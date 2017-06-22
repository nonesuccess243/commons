package com.wayeasoft.waf.demo;

import java.io.Serializable;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.anno.DbIgnore;
import com.nbm.core.modeldriven.anno.NameCol;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.younker.waf.db.DataSourceProvider;

public class DemoModel extends PureModel implements Serializable
{
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        @NameCol
        private String name;
        
        private int count;
        
        @DbIgnore
        private String[] remarks;
        
        public String[] getRemarks()
        {
                return remarks;
        }
        public void setRemarks(String[] remarks)
        {
                this.remarks = remarks;
        }
        public String getName()
        {
                return name;
        }
        public void setName(String name)
        {
                this.name = name;
        }
        public int getCount()
        {
                return count;
        }
        public void setCount(int count)
        {
                this.count = count;
        }
        
        public static void main(String[] args)
        {
                CrudGenerator.GENERATE_FILE = true;
                Db.MYSQL.generateAll("com.wayeasoft.waf.demo");
        }

}
