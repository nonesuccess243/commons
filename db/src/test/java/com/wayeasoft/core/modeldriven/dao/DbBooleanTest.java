package com.wayeasoft.core.modeldriven.dao;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.enums.YesOrNo;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;
import com.wayeasoft.test.spring.RootConfig;
import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.db.mybatis.CommonDao;
import com.younker.waf.db.mybatis.CommonExample;
import com.younker.waf.db.mybatis.MybatisDao;
import com.younker.waf.db.mybatis.SqlSessionProvider;

public class DbBooleanTest
{

        @BeforeClass
        public static void setUpBeforeClass() throws Exception
        {
                DataSourceProvider.initSimple();
                MybatisDao.INSTANCE.scanAndInit();
                SqlSessionProvider.openSession();

        }

        @AfterClass
        public static void tearDownAfterClass() throws Exception
        {
                SqlSessionProvider.getSqlSession().commit();
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
                // TODO
                //new SomeModel
                //someModel.setBooleanValue(true)
                //save
                //select
                //assertTrue(someModel.getBooleanValue)
        }

}
