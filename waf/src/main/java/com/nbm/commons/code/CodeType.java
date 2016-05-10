package com.nbm.commons.code;

public class CodeType
{
        private Long id;
        private String code;
        private String name;
        private String remark;

        public CodeType()
        {
                id = 0L;
        }

        public Long getId()
        {
                return id;
        }

        public void setId(Long id)
        {
                this.id = id;
        }

        public String getCode()
        {
                return code;
        }

        public void setCode(String code)
        {
                this.code = code;
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public String getRemark()
        {
                return remark;
        }

        public void setRemark(String remark)
        {
                this.remark = remark;
        }
}
