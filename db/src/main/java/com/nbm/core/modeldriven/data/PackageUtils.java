package com.nbm.core.modeldriven.data;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 * 按照包名获取类的工具类
 * 
 * 原来有一个同名的工具类，此类是后来写的，但是引用了更先进的工具包因此更简洁。
 * 
 * TODO 与原有的同名工具类整合并开放使用。
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
                //配置一个拦截器
                FilterBuilder filter = new FilterBuilder();
                //拦截以包名开头以.class结尾的文件
                filter.include(packageName.replace(".","\\.")+".*"+"\\.class");
                //添加拦截器
                ConfigurationBuilder config = ConfigurationBuilder.build(packageName).filterInputsBy(filter);
                //添加扫描器
                config.addScanners(new SubTypesScanner(),new TypeAnnotationsScanner());
                config.setUrls(ClasspathHelper.forPackage(packageName));
                Reflections reflections = new Reflections(config);
                /*Reflections reflections = new Reflections(packageName);*/
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
