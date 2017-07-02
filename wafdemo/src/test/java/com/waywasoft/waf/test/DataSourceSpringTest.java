package com.waywasoft.waf.test;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import com.wayeasoft.springmvc.config.RootConfig;
import com.younker.waf.db.DataSourceProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class DataSourceSpringTest
{

        private final static Logger log = LoggerFactory
                        .getLogger(DataSourceSpringTest.class);

        @Autowired
        private DataSource dataSource;

        @BeforeClass
        public static void setUpBeforeClass()
        {

        }

        @Before
        public void setUp() throws Exception
        {
                DataSourceProvider.initInstance(dataSource);
        }

        @Test
        public void test()
                        throws InstantiationException, IllegalAccessException,
                        InvocationTargetException, SQLException
        {
                
                assertNotNull(dataSource);
                
                DataSourceProvider.instance();
                assertNotNull(DataSourceProvider.instance().getDataSource());
        }

}