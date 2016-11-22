package com.nbm.commons.code;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table("S_CODE")
public class Code
{
        @Column("ID")
        private Long id;

        @Column("CODE")
        private String code;

        @Column("NAME")
        private String name;

        @Column("MNEMONIC")
        private String mnemonic;

        @Column("REMARK")
        private String remark;
        
        @Column("SHOW_ORDER")
        private String showOrder;

        @Column("TYPE_CODE")
        private String typeCode;

        public Code()
        {
                id = 0L;
                typeCode = "";
        }

        /**
         * @return the id
         */
        public Long getId()
        {
                return id;
        }

        /**
         * @param id
         *                the id to set
         */
        public void setId(Long id)
        {
                this.id = id;
        }

        /**
         * @return the code
         */
        public String getCode()
        {
                return code;
        }

        /**
         * @param code
         *                the code to set
         */
        public void setCode(String code)
        {
                this.code = code;
        }

        /**
         * @return the name
         */
        public String getName()
        {
                return name;
        }

        /**
         * @param name
         *                the name to set
         */
        public void setName(String name)
        {
                this.name = name;
        }

        /**
         * @return the mnemonic
         */
        public String getMnemonic()
        {
                return mnemonic;
        }

        /**
         * @param mnemonic
         *                the mnemonic to set
         */
        public void setMnemonic(String mnemonic)
        {
                this.mnemonic = mnemonic;
        }

        /**
         * @return the remark
         */
        public String getRemark()
        {
                return remark;
        }

        /**
         * @param remark
         *                the remark to set
         */
        public void setRemark(String remark)
        {
                this.remark = remark;
        }

        /**
         * @return the typeCode
         */
        public String getTypeCode()
        {
                return typeCode;
        }

        /**
         * @param typeCode
         *                the typeCode to set
         */
        public void setTypeCode(String typeCode)
        {
                this.typeCode = typeCode;
        }

        public String getShowOrder()
        {
                return showOrder;
        }

        public void setShowOrder(String showOrder)
        {
                this.showOrder = showOrder;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
                return "Code [id=" + id + ", code=" + code + ", name=" + name + ", mnemonic=" + mnemonic + ", remark="
                                + remark + ", typeCode=" + typeCode + "]";
        }

}
