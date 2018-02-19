package com.wayeasoft.core.configuration;

import java.util.HashMap;
import java.util.Map;

public enum Cfg
{
        /**
         * I for INSTANCE
         */
        I;
        
        private Map<String, Object> infos = new HashMap<String, Object>();
        
        public <T> T get(String key, Class<T> claz )
        {
                //TODO 第一次get时，判断infos是否为空，如果为空则用某个配置文件进行初始化
                return claz.cast(infos.get(key));
        }
        
        public <T> T get( String key, Class<T> claz, T defaultValue)
        {
                return get(key, claz) != null ? get(key, claz) : defaultValue;
        }
        
        public Cfg set( String key, Object value)
        {
                infos.put(key, value);
                return I;
        }

}
