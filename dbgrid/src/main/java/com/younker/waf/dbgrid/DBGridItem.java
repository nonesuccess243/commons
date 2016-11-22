/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import java.io.Serializable;

/**
 * Item of DBGrid Table
 */
public class DBGridItem implements Serializable
{
        /**
         * 
         */
        private static final long serialVersionUID = 1214228509620653222L;
        public static final String STYLE_NONE = "";
        public static final String STYLE_HIDE = "display:none";
        public static final String LABEL_NULL = "&nbsp;";

        public static final String NUMBER = "number";
        public static final String DATE = "date";
        public static final String STRING = "string";
        public static final String CASE_INSENSITIVE_STRING = "caseInsensitive";

        // html display style(formate)
        private String style;
        // the display name of the column
        private String label;
        // the real name of the column
        private String column;
        // the date type of the column
        private String type;

        private String field;

        private String format;

        private boolean frozen;

        public boolean isFrozen()
        {
                return frozen;
        }

        public void setFrozen(boolean frozen)
        {
                this.frozen = frozen;
        }

        public String getLabel()
        {
                return label;
        }

        public String getStyle()
        {
                return style;
        }

        public void setLabel(String s)
        {
                label = s;
        }

        public void setStyle(String s)
        {
                style = s;
        }

        public String getColumn()
        {
                return column;
        }

        public void setColumn(String s)
        {
                column = s;
        }

        public String getType()
        {
                return type;
        }

        public void setType(String s)
        {
                type = s;
        }

        @Override
        public String toString()
        {
                return "DBGridItem [" + (style != null ? "style=" + style + ", " : "")
                                + (label != null ? "label=" + label + ", " : "")
                                + (column != null ? "column=" + column + ", " : "")
                                + (type != null ? "type=" + type + ", " : "")
                                + (field != null ? "field=" + field + ", " : "")
                                + (format != null ? "format=" + format + ", " : "") + "frozen="
                                + frozen + "]";
        }

        public String getField()
        {
                return field;
        }

        public void setField(String field)
        {
                this.field = field;
        }

        public String getFormat()
        {
                return format;
        }

        public void setFormat(String format)
        {
                this.format = format;
        }
}
