package com.wayeasoft.waf.demo;

import java.io.Serializable;
import java.util.Date;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.anno.DbIgnore;
import com.nbm.core.modeldriven.anno.NameCol;
import com.nbm.core.modeldriven.generator.CrudGenerator;

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


        //两个时间相关的字段原本命名为currentDate和currentTime，但在h2中测试时，这两个单词是关键词，为避免未知问题，改掉。

        private Date someDate;
        
        private Date someTime;
        
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
        public Date getSomeDate()
        {
                return someDate;
        }
        public void setSomeDate(Date someDate)
        {
                this.someDate = someDate;
        }
        public Date getSomeTime()
        {
                return someTime;
        }
        public void setSomeTime(Date someTime)
        {
                this.someTime = someTime;
        }

}
