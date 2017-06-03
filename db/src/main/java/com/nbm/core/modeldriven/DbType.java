package com.nbm.core.modeldriven;

public enum DbType
{
        DECIMAL,
        VARCHAR,
        INT,
        BIGINT,
        SERIAL,
        VARCHAR2
        {

                @Override
                public DbType getMybatisDbType()
                {
                        return VARCHAR;
                }
                
        },
        TIMESTAMP,
        CHAR, DATE_TIME,
        DATE, TIME, DATETIME,
        FLOAT,
        DOUBLE,
        
        //空间信息相关的字段
        GEOMETRY,
        POINT
        {
                @Override
                public String getPopulatePrefix()
                {
                        return "ST_GeomFromText(";
                }

                @Override
                public String getPopulateSuffix()
                {
                        return ")";
                }

                @Override
                public boolean simple()
                {
                        return false;
                }

                @Override
                public String getFetchPrefix()
                {
                        return "ST_AsText(";
                }

                @Override
                public String getFetchSuffix()
                {
                        return ")";
                }
                
                
                
        },
        LINESTRING,
        POLYGON,
        MULTIPOINT,
        MULTILINESTRING,
        MULTIPOLYGON,
        GEOMETRYCOLLECTION
        ;
        
        
        /**
         * Mybatis閰嶇疆鏂囦欢涓紝鏈夐儴鍒嗛渶瑕佹寚瀹氬弬鏁扮殑jdbctype灞炴�锛岃�姝ゅ睘鎬т笉鏀寔varchar2锛岀浉搴旂殑浣嶇疆瑕佸～鍐檝archar
         * @return
         */
        @Deprecated
        public DbType getMybatisDbType()
        {
                return this;
        }
        
        /**
         * 是否为简单类型
         * 
         * 如果不是简单类型，意味着在mybatis通用配置文件或其它位置，有更多复杂的处理
         * @return
         */
        public boolean simple()
        {
                return true;
        }
        
        /**
         * Mybatis通用配置文件中的前后缀
         * @return
         */
        public String getPopulatePrefix()
        {
                return "";
        }
        
        public String getPopulateSuffix()
        {
                return "";
        }
        
        /**
         * Mybatis通用配置文件中的前后缀
         * @return
         */
        public String getFetchPrefix()
        {
                return "";
        }
        
        public String getFetchSuffix()
        {
                return "";
        }
        
       
}
