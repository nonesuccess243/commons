package com.wayeasoft.waf.webinfo;

import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.anno.Fk;

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;

public class WebInfo extends Model
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        @Fk(foreign=WebInfoType.class)
        private Long webInfoTypeId;
        
        private String name;
        
        private String code;
        
        private String summaryInfo;
        
        private String detailedInfo;
        
        private String imagePath;
        
        private boolean available;
        
        private boolean clickable;
        
        private String url;
        
        /**
         * 关联webinfoid，可用于显示webinfo元素时，点击该元素的跳转动作
         */
        private Long associationWebInfoId;

        public Long getWebInfoTypeId()
        {
                return webInfoTypeId;
        }

        public void setWebInfoTypeId(Long webInfoTypeId)
        {
                this.webInfoTypeId = webInfoTypeId;
        }

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

        public String getSummaryInfo()
        {
                return summaryInfo;
        }

        public void setSummaryInfo(String summaryInfo)
        {
                this.summaryInfo = summaryInfo;
        }

        public String getDetailedInfo()
        {
                return detailedInfo;
        }

        public void setDetailedInfo(String detailedInfo)
        {
                this.detailedInfo = detailedInfo;
        }

        public String getImagePath()
        {
                return imagePath;
        }

        public void setImagePath(String imagePath)
        {
                this.imagePath = imagePath;
        }

        public boolean isAvailable()
        {
                return available;
        }

        public void setAvailable(boolean available)
        {
                this.available = available;
        }

        public boolean isClickable()
        {
                return clickable;
        }

        public void setClickable(boolean clickable)
        {
                this.clickable = clickable;
        }

        public String getUrl()
        {
                return url;
        }

        public void setUrl(String url)
        {
                this.url = url;
        }

        public Long getAssociationWebInfoId()
        {
                return associationWebInfoId;
        }

        public void setAssociationWebInfoId(Long associationWebInfoId)
        {
                this.associationWebInfoId = associationWebInfoId;
        }
        
        
}
