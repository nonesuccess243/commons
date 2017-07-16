package com.wayeasoft.waf.webinfo;

import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.anno.NameCol;

public class WebInfoType extends Model
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * 名称
         */
        @NameCol
        private String name;
        
        /**
         * 代码
         */
        private String code;

        /**
         * 备注
         */
        private String remark;
        
        /**
         * 是否可用
         */
        private boolean available;

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public String getCode()
        {
                return code;
        }

        public void setCode(String code)
        {
                this.code = code;
        }

        public String getRemark()
        {
                return remark;
        }

        public void setRemark(String remark)
        {
                this.remark = remark;
        }

        public boolean isAvailable()
        {
                return available;
        }

        public void setAvailable(boolean available)
        {
                this.available = available;
        }
}
