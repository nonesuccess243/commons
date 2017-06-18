package com.nbm.commons;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.commons.packageutilstest.PackageUtilsTestClass;
import com.nbm.commons.packageutilstest.PackageUtilsTestClassGrandSun;
import com.nbm.commons.packageutilstest.PackageUtilsTestClassSub;
import com.nbm.commons.packageutilstest.sub.PackageUtilsTestClass2;

public class PackageUtils2Test
{
        private final static Logger Log = LoggerFactory.getLogger(PackageUtils2Test.class);

        @BeforeClass
        public static void setUpBeforeClass() throws Exception
        {
        }

        @AfterClass
        public static void tearDownAfterClass() throws Exception
        {
        }

        @Before
        public void setUp() throws Exception
        {
        }

        @After
        public void tearDown() throws Exception
        {
        }

        @Test
        public void test()
        {
                Set<Class<?>> result = PackageUtils2.getClassesByPackagename("com.nbm.commons.packageutilstest");
                
                
                assertNotNull(result);
                
                assertNotEquals(0, result);
                
                assertTrue(result.contains(PackageUtilsTestClass.class));
                assertTrue(result.contains(PackageUtilsTestClassSub.class));
                assertTrue(result.contains(PackageUtilsTestClass2.class));
                
                
                
        }
        @Test
        public void testGeneric()
        {
                Set<Class<? extends PackageUtilsTestClass>> result = PackageUtils2.getClassesByPackagenameAndGenericClass("com.nbm.commons.packageutilstest", PackageUtilsTestClass.class);
                
                
                assertNotNull(result);
                
                assertNotEquals(0, result);
                
                assertFalse(result.contains(PackageUtilsTestClass.class));
                assertTrue(result.contains(PackageUtilsTestClassSub.class));
                assertFalse(result.contains(PackageUtilsTestClass2.class));
                assertTrue(result.contains(PackageUtilsTestClassGrandSun.class));
                
        }
        @Test
        public void testAll()
        {
                Set<Class<?>> result = PackageUtils2.getClassesByPackagename("com.nbm");
                
                
                assertNotNull(result);
                
                assertNotEquals(0, result);
                
                assertTrue(result.contains(PackageUtilsTestClass.class));
                assertTrue(result.contains(PackageUtilsTestClassSub.class));
                assertTrue(result.contains(PackageUtilsTestClass2.class));
                assertTrue(result.contains(PackageUtilsTestClassGrandSun.class));
                
        }

}
