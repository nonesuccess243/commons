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

public class SingleSelectTag extends SimpleTagSupport
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

        @Override
        public void doTag() throws JspException, IOException
        {
                StringBuilder sb = new StringBuilder();
                List<Code> codes = CodeUtils.getInstance().getCodesByTypeCode(typecode);
                generitSigleSelectTag(sb, codes);

                getJspContext().getOut().write(sb.toString());

        }

        public void generitSigleSelectTag(StringBuilder sb, List<Code> codes) throws IOException
        {
                for (Code code : codes)
                {
                        sb.append("<label><input type='radio'");
                        if (code.getCode().equals(this.getValue()))
                        {
                                sb.append("checked='checked'");
                        }
                        sb.append("value='").append(code.getCode()).append("'");
                        this.appendHtmlAttribute(sb);
                        sb.append("/>").append(code.getName()).append("</label>");
                }

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

                if (!StringUtils.isEmpty(name))
                        sb.append("name='" + name + "' ");

                if (!StringUtils.isEmpty(size))
                        sb.append("size='" + size + "' ");
        }

        public String getTypecode()
        {
                return typecode;
        }

        public void setTypecode(String typecode)
        {
                this.typecode = typecode;
        }

        public String getValue()
        {
                return value;
        }

        public void setValue(String value)
        {
                this.value = value;
        }

        public String getId()
        {
                return id;
        }

        public void setId(String id)
        {
                this.id = id;
        }

        public String getClazz()
        {
                return clazz;
        }

        public void setClazz(String clazz)
        {
                this.clazz = clazz;
        }

        public String getTitle()
        {
                return title;
        }

        public void setTitle(String title)
        {
                this.title = title;
        }

        public String getStyle()
        {
                return style;
        }

        public void setStyle(String style)
        {
                this.style = style;
        }

        public String getTabindex()
        {
                return tabindex;
        }

        public void setTabindex(String tabindex)
        {
                this.tabindex = tabindex;
        }

        public String getAccesskey()
        {
                return accesskey;
        }

        public void setAccesskey(String accesskey)
        {
                this.accesskey = accesskey;
        }

        public String getDisabled()
        {
                return disabled;
        }

        public void setDisabled(String disabled)
        {
                this.disabled = disabled;
        }

        public String getMultiple()
        {
                return multiple;
        }

        public void setMultiple(String multiple)
        {
                this.multiple = multiple;
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public String getSize()
        {
                return size;
        }

        public void setSize(String size)
        {
                this.size = size;
        }

        public static Logger getLog()
        {
                return log;
        }

}
