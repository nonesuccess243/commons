/***********************************************
 * Title:       DisplayableUtils.java
 * Description: DisplayableUtils.java
 * Create Date: 2013-7-7
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.commons;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用开源包实现的获取某包下所有类的工具类
 * 
 * 原版的工具类在各种条件下会偶发问题。
 */
public class PackageUtils2
{
        
        private final static Logger log = LoggerFactory.getLogger(PackageUtils2.class);



        /**
         * 获取某包下某类的所有子类，不包括genericClass本身
         * @param packageName
         * @param genericClass
         * @return
         */
        public static <T> Set<Class<? extends T>> getClassesByPackagenameAndGenericClass(String packageName, Class<T> genericClass)
        {
                
                
                List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
                classLoadersList.add(ClasspathHelper.contextClassLoader());
                
                FilterBuilder builder = new FilterBuilder();
                
                builder.include(FilterBuilder.prefix(packageName));
                
                Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new SubTypesScanner(
                                false /* don't exclude Object.class */), new ResourcesScanner())
                                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                                .filterInputsBy(builder));

                
                Set<Class<? extends T>> result =  reflections.getSubTypesOf(genericClass);
                
                log.debug("在[{}]包下共发现[{}]的子类[{}]个:{}", packageName, genericClass, result.size(), result);
                
                return result;
                
        }
        
        public static Set<Class<?>> getClassesByPackagename(String packageName)
        {
                return getClassesByPackagenameAndGenericClass(packageName, Object.class);
        }
}
