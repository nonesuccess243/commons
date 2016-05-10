package com.younker.waf.dbgrid;

public interface DBGridToken
{
        public static final String SEPARATOR = ",";
        /**
         * escape char of the separator(',')
         */
        public static final String ESCAPE_TOKEN = "^";

        public static final String FORMAT_TOKEN = "$";
        public static final String PARAMETER_TOKEN = "@";
        public static final String ATTRIBUTE_TOKEN = "#";

        public static final String NUMERIC_TOKEN = "n";
        public static final String DATE_TOKEN = "d";

        public static final int STRING = 1;
        public static final int NUMERIC = 2;
        public static final int DATE = 3;

        public static final int OP_EQUAL = 1;
        public static final int OP_NE = 2;
        public static final int OP_GREATER = 3;
        public static final int OP_GE = 4;
        public static final int OP_LESS = 5;
        public static final int OP_LE = 6;
        public static final int OP_BETWEEN = 7;
        public static final int OP_LEFT_LIKE = 8;
        public static final int OP_RIGHT_LIKE = 9;
        public static final int OP_BOTH_LIKE = 10;

        public static final String[] OPS = new String[]
        { "", "=", "!=", ">", ">=", "<", "<=" };

        public static final int GRID_DEFAULT = 0;
        public static final int GRID_CHECKBOX = 1;
        public static final int GRID_RADIO = 2;

        static final String GROUPBY = " group by ";
        static final String ORDERBY = " order by ";
        static final String STR_BLANK = "";
}
