/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:    Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.nbm.commons.code.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.commons.code.Code;
import com.nbm.commons.code.CodeUtils;

/**
 * 传入tyoecode直接生成下拉列表，省去配置options.properties的工作
 */
public class SelectCodeTag extends SimpleTagSupport
{
        private final static Logger log = LoggerFactory.getLogger(SelectCodeTag.class);

        private String typecode;
        private String value;

        private String id;
        private String clazz;
        private String title;
        private String style;
        private String tabindex;
        private String accesskey;
        private String disabled;
        private String multiple;
        private String name;
        private String size;

        private String other;
        
        private String onChange;

      

        @Override
        public void doTag() throws JspException, IOException
        {
                StringBuilder sb = new StringBuilder();

                List<Code> codes = CodeUtils.getInstance().getCodesByTypeCode(typecode);

                log.debug("selectCodeTag:" + typecode);
                sb.append("<select ");
                appendHtmlAttribute(sb);
                sb.append(">\n");
                sb.append("<option></option>\n");
                for (Code code : codes)
                {
                        sb.append("<option value='" + code.getCode() + "'");
                        if (code.getCode().equals(value))
                        {
                                sb.append(" selected='selected' ");
                        }

                        sb.append(">" + code.getName() + "</option>\n");
                }

                sb.append("</select >\n");

                getJspContext().getOut().write(sb.toString());
        }

        private void appendHtmlAttribute(StringBuilder sb)
        {
                if (!StringUtils.isEmpty(id))
                        sb.append("id='" + id + "' ");

                if (!StringUtils.isEmpty(clazz))
                        sb.append("class='" + clazz + "' ");

                if (!StringUtils.isEmpty(title))
                        sb.append("title='" + title + "' ");

                if (!StringUtils.isEmpty(style))
                        sb.append("style='" + style + "' ");

                if (!StringUtils.isEmpty(tabindex))
                        sb.append("tabindex='" + tabindex + "' ");

                if (!StringUtils.isEmpty(accesskey))
                        sb.append("accesskey='" + accesskey + "' ");

                if (!StringUtils.isEmpty(disabled))
                        sb.append("disabled='" + disabled + "' ");

                if (!StringUtils.isEmpty(multiple))
                        sb.append("multiple='" + multiple + "' ");

                if (!StringUtils.isEmpty(name))
                        sb.append("name='" + name + "' ");

                if (!StringUtils.isEmpty(size))
                        sb.append("size='" + size + "' ");
                if(!StringUtils.isEmpty(onChange))
                {
                        sb.append("onchange='" + onChange + "' ");
                }
                if (!StringUtils.isEmpty(other))
                {
                        sb.append(other);
                }
               
        }

        public void setTypecode(String typecode)
        {
                this.typecode = typecode;
        }

        public void setValue(String value)
        {
                this.value = value;
        }

        public void setId(String id)
        {
                this.id = id;
        }

        public void setClazz(String clazz)
        {
                this.clazz = clazz;
        }

        public void setTitle(String title)
        {
                this.title = title;
        }

        public void setStyle(String style)
        {
                this.style = style;
        }

        public void setTabindex(String tabindex)
        {
                this.tabindex = tabindex;
        }

        public void setAccesskey(String accesskey)
        {
                this.accesskey = accesskey;
        }

        public void setDisabled(String disabled)
        {
                this.disabled = disabled;
        }

        public void setMultiple(String multiple)
        {
                this.multiple = multiple;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public void setSize(String size)
        {
                this.size = size;
        }

        public String getOther()
        {
                return other;
        }

        public void setOther(String other)
        {
                this.other = other;
        }
        public String getOnChange()
        {
                return onChange;
        }

        public void setOnChange(String onChange)
        {
                this.onChange = onChange;
        }
}
