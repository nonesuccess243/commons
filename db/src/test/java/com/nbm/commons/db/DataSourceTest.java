package com.nbm.commons.db;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.db.mybatis.MybatisDao;

public class DataSourceTest
{

        private final static Logger log = LoggerFactory.getLogger(DataSourceTest.class);

        @BeforeClass
        public static void setUpBeforeClass()
        {

        }

        @Test
        public void testByConfig() throws SQLException
        {
                DataSourceProvider.initSimple();

                DataSourceProvider.instance().runBatch("select 1");
                
                assertEquals("H2", DataSourceProvider.instance().getDatabaseProductName());

//                MybatisDao.INSTANCE.scanAndInit();
        }

}
