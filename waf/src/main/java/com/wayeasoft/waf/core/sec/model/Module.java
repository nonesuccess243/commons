package com.wayeasoft.waf.core.sec.model;

import java.util.List;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.anno.DbIgnore;
import com.nbm.core.modeldriven.anno.ModelInfo;

@ModelInfo(tableName = "S_MODULE")
public class Module extends PureModel
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String code;

        private String name;

        private Long subsysId;

        private Integer orderby;

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

        public Long getSubsysId()
        {
                return subsysId;
        }

        public void setSubsysId(Long subsysId)
        {
                this.subsysId = subsysId;
        }

        public Integer getOrderby()
        {
                return orderby;
        }

        public void setOrderby(Integer orderby)
        {
                this.orderby = orderby;
        }
        
        @DbIgnore
        private List<ModuleItem> grantedModuleItems;

        /**
         * @return the grantedModuleItems
         */
        public List<ModuleItem> getGrantedModuleItems()
        {
                return grantedModuleItems;
        }

        /**
         * @param grantedModuleItems the grantedModuleItems to set
         */
        public void setGrantedModuleItems(List<ModuleItem> grantedModuleItems)
        {
                this.grantedModuleItems = grantedModuleItems;
        }
        
        @DbIgnore
        public ModuleItem getModuleItemByCode( String moduleItemCode )
        {
                for( ModuleItem moduleItem : grantedModuleItems )
                {
                        if( moduleItem.getCode().equalsIgnoreCase(moduleItemCode) )
                        {
                                return moduleItem;
                        }
                }
                return null;
        }
        
        @DbIgnore
        public boolean isShow()
        {
                for( ModuleItem moduleItem : grantedModuleItems )
                {
                        if( moduleItem.isShow() )
                        {
                                return true;
                        }
                }
                return false;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
                return "Module [id=" + getId() + ", code=" + code + ", name=" + name + ", subsysId=" + subsysId
                                + ", orderby=" + orderby + ", grantedModuleItems=" + grantedModuleItems + "]";
        }
        
        public static void main(String[] args) throws Exception
        {
                Db.ORACLE.generate(Module.class);
        }
}