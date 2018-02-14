package com.wayeasoft.modeldriven.utils;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 按照包名获取类的工具类
 * 
 * @author niyuzhe
 *
 */
public class PackageUtils
{

        /**
         * 获取某个包下面为某个类的子类的类
         * 
         * 不能获取Object的子类
         * @param packageName
         * @param superClass
         * @return
         */
        public static <T> Set<Class<? extends T>> getClasses(String packageName, Class<T> superClass)
        {
                
                Reflections reflections = new Reflections(packageName);

                Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(superClass);

                return subTypes;
        }
        
        /**
         * 获取某些包下面某个类的子类
         * 
         * 不能获取Object类的子类
         * @param packageNames
         * @param superClass
         * @return
         */
        public static <T> Set<Class<? extends T>> getClasses(Iterable<String> packageNames, Class<T> superClass)
        {
                Set<Class<? extends T>> result = new HashSet<>();
                for( String packageName : packageNames )
                {
                        result.addAll(getClasses(packageName, superClass));
                }
                return result;
        }

}
