package com.wayeasoft.core.modeldriven.dao;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.h2.util.New;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableMap;
import com.wayeasoft.geo.test.GeoModel;
import com.wayeasoft.test.spring.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
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
