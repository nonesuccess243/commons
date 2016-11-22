package com.nbm.commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;


public class Cache<K,V>
{
        private Map<K,V> cacheMap = new HashMap<K,V>();
        
        private Function<K,V> howToGet;
        
        public Cache( Function<K,V> howToGet )
        {
                this.howToGet = howToGet;
        }
        
        public V get( K key )
        {
                V result = cacheMap.get(key);
                if( result == null )
                {
                        result = howToGet.get(key);
                        cacheMap.put(key, result);
                }
                return result;
        }
        
        interface Function<K,V>
        {
                public V get(K key);
        }

}
