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
        POINT,
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
        public DbType getMybatisDbType()
        {
                return this;
        }
}
