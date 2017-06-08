package com.wayeasoft.waf.core.sec.model;

import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.anno.ModelInfo;

@ModelInfo(tableName = "S_ROLE")
public class Role extends Model
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String code;

        private String name;

        private String remark;


        public String getCode()
        {
                return code;
        }

        public void setCode(String code)
        {
                this.code = code == null ? null : code.trim();
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name == null ? null : name.trim();
        }

        public String getRemark()
        {
                return remark;
        }

        public void setRemark(String remark)
        {
                this.remark = remark == null ? null : remark.trim();
        }
}