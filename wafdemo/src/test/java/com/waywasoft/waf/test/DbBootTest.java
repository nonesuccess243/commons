package com.waywasoft.waf.test;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbm.waf.boot.DbBooter;
import com.wayeasoft.springmvc.config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class DbBootTest
{
        private final static Logger log = LoggerFactory.getLogger(DbBootTest.class);
        
        @Autowired
        private DataSource dataSource;
        
        @Autowired
        private DbBooter dbBooter;

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
        public void test() throws Exception
        {
//                dbBooter.boot();
                
        }

}
