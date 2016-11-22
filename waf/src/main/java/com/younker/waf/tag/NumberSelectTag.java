/***********************************************
 * Title:       YearTag.java
 * Description: YearTag.java
 * Author:      stu
 * Create Date: 2010-3-12
 * CopyRight:   CopyRight(c)@2009
 * Company:     TJUSCS
 * Version:     1.0
 ***********************************************
 */
package com.younker.waf.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.ResponseUtils;

/**
 * @author stu
 * 
 */
public class NumberSelectTag extends TagSupport
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private Logger log = LoggerFactory.getLogger(this.getClass());

        private final static String nullOption = "<option></option>\n";

        /**
         * 排序方式为降序
         */
        private final static String S_DESC = "desc";
        /**
         * 排序方式为升序
         */
        private final static String S_ASCE = "asce";

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
                return "NumberSelectTag [disable=" + disable + ", end=" + end + ", id=" + id + ", length=" + length
                                + ", multiple=" + multiple + ", name=" + name + ", size=" + size + ", start=" + start
                                + ", style=" + style + ", value=" + value + "]";
        }

        /**
         * 元素名称
         */
        private String name;
        /**
         * 元素id
         */
        private String id;
        /**
         * 是否为多选框
         */
        private String multiple;

        private String tabindex;
        /**
         * 多选框的尺寸
         */
        private String size;
        /**
         * 元素样式
         */
        private String style;
        /**
         * 显示字符串的前缀
         */
        private String prefix;
        /**
         * 显示字符串的后缀
         */
        private String suffix;
        /**
         * 是否禁用
         */
        private String disable;
        /**
         * 开始数字
         */
        private String start;
        /**
         * 结束数字
         */
        private String end;
        /**
         * 开始结束之间的长度
         */
        private String length;
        /**
         * 默认值
         */
        private String value;

        /**
         * 排序方式
         */
        private String sort = S_DESC;

        private int _start;
        private int _end;
        private int _length;
        private int _value;

        private StringBuffer result = new StringBuffer();

        private String onChange;

        /**
         * @return the onChange
         */
        public String getOnChange()
        {
                return onChange;
        }

        /**
         * @param onChange
         *                the onChange to set
         */
        public void setOnChange(String onChange)
        {
                this.onChange = onChange;
        }

        private int current;

        /*
         * (non-Javadoc)
         * 
         * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
         */
        @Override
        public int doStartTag() throws JspException
        {
                log.debug(this.toString());

                result = new StringBuffer();// TagSupport类会缓存，需要重新定义空StringBuffer

                generateHead();

                _start = getInt(this.start);
                _end = getInt(this.end);
                _length = getInt(this.length);
                _value = getInt(this.value);

                // 目前只支持升序降序两种方法，将不为升序的sort字符串赋值为降序
                if (!S_ASCE.equals(sort))
                {
                        sort = S_DESC;
                }

                gererateAttribute();

                result.append(nullOption);

                // 初始化current
                if (S_DESC.equals(sort))
                {
                        current = _end;
                } else
                {
                        current = _start;
                }

                if (_length == 0)
                {
                        _length = _end - _start + 1;
                }

                for (int i = _start; i != _end;)
                {
                        generateBody(i);
                        if (_start > _end)
                        {
                                i--;
                        } else
                        {
                                i++;
                        }
                }

                result.append("</select>");

                ResponseUtils.write(pageContext, result.toString());

                return EVAL_BODY_INCLUDE;
        }

        /**
         * 当数字为n时，生成option主体
         */
        private void generateBody(int number)
        {
                result.append("<option value=\"" + number);

                result.append("\"");
                if (_value == number)
                {
                        result.append(" selected ");
                }
                result.append(">");

                if (prefix != null)
                {
                        result.append(prefix);
                }

                result.append(number);

                if (suffix != null)
                {
                        result.append(suffix);
                }

                result.append("</option>\n");
        }

        /**
         * 生成html标签的前一部分
         */
        private void generateHead()
        {
                result.append("<select ");
                if (name != null)
                {
                        result.append("name=\"" + name + "\" ");
                }

                if (id != null)
                {
                        result.append("id=\"" + id + "\" ");
                }

                if (multiple != null)
                {
                        result.append("mutiple=\"" + multiple + "\" ");
                }

                if (tabindex != null)
                {
                        result.append("tabindex=\"" + tabindex + "\" ");
                }
                if (size != null)
                {
                        result.append("size=\"" + size + "\" ");
                }

                if (style != null)
                {
                        result.append("style=\"" + style + "\"");
                }

                if (disable != null)
                {
                        result.append("style=\"" + disable + "\"");
                }

                if (onChange != null)
                {
                        result.append("onChange=\"" + onChange + "\"");
                }

                result.append(">\n");
        }

        /**
         * 后面的处理逻辑使用start、end，不是用length。
         * 因此在start和end指定其一并且指定了length的情况下，采用本函数计算未指定的属性。
         * 逻辑为根据在start和end中，根据排序方式用不为0的计算为0的。
         */
        private void gererateAttribute()
        {
                log.debug("before generate: start=" + _start + ";end=" + _end + ";length=" + _length);

                // start和end都指定的情况下直接退出
                if (_start != 0 && _end != 0)
                        return;

                // start和end都未指定的情况下，视为错误
                if (_start == 0 && _end == 0)
                {
                        setErrorValue();
                }

                if (_start == 0 && _end != 0)
                {
                        if (S_DESC.equals(this.sort))
                                _start = _end + _length;
                        else
                                _start = _end - _length;
                } else if (_start != 0 && _end == 0)
                {
                        if (S_DESC.equals(this.sort))
                                _end = _start - _length;
                        else
                                _end = _start + _length;
                }
                log.debug("after generate: start=" + _start + ";end=" + _end + ";length=" + _length);
        }

        private void setErrorValue()
        {
                _start = 0;
                _end = 0;
        }

        public int getNext()
        {
                if (S_ASCE.equals(sort))
                {
                        return current++;
                } else
                        return current--;
        }

        /**
         * 用于将字符串转化为整数的函数。主要封装字符串为null或者为空串时返回0的逻辑
         * 
         * @param source
         * @return
         */
        private int getInt(String source)
        {
                try
                {
                        return Integer.parseInt(source);
                } catch (NumberFormatException e)
                {
                        return 0;
                }
        }

        /**
         * @return the multiple
         */
        public String getMultiple()
        {
                return multiple;
        }

        /**
         * @param multiple
         *                the multiple to set
         */
        public void setMultiple(String multiple)
        {
                this.multiple = multiple;
        }

        /**
         * @return the style
         */
        public String getStyle()
        {
                return style;
        }

        /**
         * @param style
         *                the style to set
         */
        public void setStyle(String style)
        {
                this.style = style;
        }

        /**
         * @return the size
         */
        public String getSize()
        {
                return size;
        }

        /**
         * @param size
         *                the size to set
         */
        public void setSize(String size)
        {
                this.size = size;
        }

        /**
         * @return the disable
         */
        public String getDisable()
        {
                return disable;
        }

        /**
         * @param disable
         *                the disable to set
         */
        public void setDisable(String disable)
        {
                this.disable = disable;
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
         * @return the id
         */
        public String getId()
        {
                return id;
        }

        /**
         * @param id
         *                the id to set
         */
        public void setId(String id)
        {
                this.id = id;
        }

        /**
         * @return the start
         */
        public String getStart()
        {
                return start;
        }

        /**
         * @param start
         *                the start to set
         */
        public void setStart(String start)
        {
                this.start = start;
        }

        public void setStart(Integer start)
        {
                this.start = start.toString();
        }

        /**
         * @return the end
         */
        public String getEnd()
        {
                return end;
        }

        /**
         * @param end
         *                the end to set
         */
        public void setEnd(String end)
        {
                this.end = end;
        }

        public void setEnd(Integer end)
        {
                this.end = end.toString();
        }

        /**
         * @return the length
         */
        public String getLength()
        {
                return length;
        }

        /**
         * @param length
         *                the length to set
         */
        public void setLength(String length)
        {
                this.length = length;
        }

        public void setLength(Integer length)
        {
                this.length = length.toString();
        }

        /**
         * @return the value
         */
        public String getValue()
        {
                return value;
        }

        /**
         * @param value
         *                the value to set
         */
        public void setValue(String value)
        {
                this.value = value;
        }

        /**
         * @return the suffix
         */
        public String getSuffix()
        {
                return suffix;
        }

        /**
         * @param suffix
         *                the suffix to set
         */
        public void setSuffix(String suffix)
        {
                this.suffix = suffix;
        }

        /**
         * @return the prefix
         */
        public String getPrefix()
        {
                return prefix;
        }

        /**
         * @param prefix
         *                the prefix to set
         */
        public void setPrefix(String prefix)
        {
                this.prefix = prefix;
        }

        /**
         * @return the sort
         */
        public String getSort()
        {
                return sort;
        }

        /**
         * @param sort
         *                the sort to set
         */
        public void setSort(String sort)
        {
                this.sort = sort;
        }

        /**
         * @return the tabindex
         */
        public String getTabindex()
        {
                return tabindex;
        }

        /**
         * @param tabindex
         *                the tabindex to set
         */
        public void setTabindex(String tabindex)
        {
                this.tabindex = tabindex;
        }

}
