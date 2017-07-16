package com.wayeasoft.springmvc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wayeasoft.core.modeldriven.dao.CommonDao;
import com.wayeasoft.springmvc.config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class SpringContextUtilTest
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
        public void test()
        {
                assertNotNull(SpringContextUtil.getInstance().getBean(CommonDao.class));
        }

}
