package com.nbm.commons.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.junit.BeforeClass;
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
        public void test()
                        throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException
        {
                DataSourceProvider.initSimple("org.h2.Driver", "jdbc:h2:~/.h2/testdb", "sa", "");

                DataSourceProvider.instance().runBatch("select 1");

                MybatisDao.INSTANCE.init();

                // SqlSessionProvider.openSession();
                // SqlSessionProvider.getSqlSession().getMapper(ModelDrivenTestModelMapper.class).selectByExample(new
                // ModelDrivenTestModelExample());
        }

        @Test
        public void testByConfig() throws SQLException
        {
                DataSourceProvider.initSimple();

                DataSourceProvider.instance().runBatch("select 1");

                MybatisDao.INSTANCE.scanAndInit();
        }

}
