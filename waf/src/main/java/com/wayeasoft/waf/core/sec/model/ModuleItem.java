package com.wayeasoft.waf.core.sec.model;

import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.anno.ModelInfo;

@ModelInfo(tableName = "S_MODULE_ITEM")
public class ModuleItem extends Model
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String code;

        private String name;

        private String url;

        private Long moduleId;

        private String ispublic;

        private String isshow;
        
        private String infoHandler;

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

        public String getUrl()
        {
                return url;
        }

        public void setUrl(String url)
        {
                this.url = url == null ? null : url.trim();
        }

        public Long getModuleId()
        {
                return moduleId;
        }

        public void setModuleId(Long moduleId)
        {
                this.moduleId = moduleId;
        }

        public String getIspublic()
        {
                return ispublic;
        }

        public void setIspublic(String ispublic)
        {
                this.ispublic = ispublic == null ? null : ispublic.trim();
        }

        public String getIsshow()
        {
                return isshow;
        }

        public void setIsshow(String isshow)
        {
                this.isshow = isshow == null ? null : isshow.trim();
        }

        public Integer getOrderby()
        {
                return orderby;
        }

        public void setOrderby(Integer orderby)
        {
                this.orderby = orderby;
        }
        
        public boolean isShow()
        {
                return "Y".equals(isshow);
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
                return "ModuleItem [id=" + getId() + ", code=" + code + ", name=" + name + ", url=" + url + ", moduleId="
                                + moduleId + ", ispublic=" + ispublic + ", isshow=" + isshow + ", orderby=" + orderby
                                + "]";
        }

        /**
         * @return the infoHandler
         */
        public String getInfoHandler()
        {
                return infoHandler;
        }

        /**
         * @param infoHandler the infoHandler to set
         */
        public void setInfoHandler(String infoHandler)
        {
                this.infoHandler = infoHandler;
        }
}