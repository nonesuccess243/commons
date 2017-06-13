package com.nbm.core.modeldriven;


/**
 * 用于表示数据库列类型的枚举类
 * 
 * TODO 用枚举则不支持扩展，在程序需要Java中的类类型对某列时不好扩展
 * 
 * @author niyuzhe
 *
 */
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
                public boolean simple()
                {
                        return false;
                }
        },
        LINESTRING
        {
                @Override
                public boolean simple()
                {
                        return false;
                }
        },
        POLYGON
        {
                @Override
                public boolean simple()
                {
                        return false;
                }
        },
        MULTIPOINT
        {
                @Override
                public boolean simple()
                {
                        return false;
                }
        },
        MULTILINESTRING
        {
                @Override
                public boolean simple()
                {
                        return false;
                }
        },
        MULTIPOLYGON
        {
                @Override
                public boolean simple()
                {
                        return false;
                }
        },
        GEOMETRYCOLLECTION
        {
                @Override
                public boolean simple()
                {
                        return false;
                }
        }
        ;
        
//        @Override
//        public String toString()
//        {
//                return "";
//        }
        
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
        
        
       
}
