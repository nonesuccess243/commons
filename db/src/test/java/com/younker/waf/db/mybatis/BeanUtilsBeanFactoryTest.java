package com.younker.waf.db.mybatis;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.h2.util.New;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableMap;
import com.wayeasoft.geo.test.GeoModel;
import com.wayeasoft.test.spring.RootConfig;
import com.younker.waf.db.mybatis.BeanUtilsBeanFactory;

public class BeanUtilsBeanFactoryTest
{

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
        public void test() throws IllegalAccessException, InvocationTargetException
        {
                BeanUtilsBeanFactory.populate(new GeoModel(), ImmutableMap.of("point", "POINT(1.1 2.2"));
        }

}
