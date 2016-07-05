/**
 * 
 */
package com.nbm.core.modeldriven.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;
import com.nbm.core.modeldriven.test.model.dao.ModelDrivenTestModelMapper;
import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.db.mybatis.MybatisDao;
import com.younker.waf.db.mybatis.SqlSessionProvider;

/**
 * @author 玉哲
 *
 */
public class CommonDaoTest
{

        private final static Logger log = LoggerFactory.getLogger(CommonDaoTest.class);

        /**
         * @throws java.lang.Exception
         */
        @BeforeClass
        public static void setUpBeforeClass() throws Exception
        {
                DataSourceProvider.initSimple("org.h2.Driver", "jdbc:h2:./src/test/resources/testdb;AUTO_SERVER=TRUE",
                                "sa", "");
                MybatisDao.INSTANCE.init();

        }

        /**
         * @throws java.lang.Exception
         */
        @AfterClass
        public static void tearDownAfterClass() throws Exception
        {
        }

        /**
         * @throws java.lang.Exception
         */
        @Before
        public void setUp() throws Exception
        {
                SqlSessionProvider.openSession();
        }

        /**
         * @throws java.lang.Exception
         */
        @After
        public void tearDown() throws Exception
        {
                SqlSessionProvider.getSqlSession().commit();
                SqlSessionProvider.getSqlSession().close();
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#selectById(java.lang.Class, java.lang.Object)}
         * .
         */
        @Test
        public void testSelectById()
        {
                String testName = "test name";

                ModelDrivenTestModel model = new ModelDrivenTestModel();

                model.setName("test name");
                model.setModelCreateTime(new Date());

                SqlSessionProvider.getSqlSession().getMapper(ModelDrivenTestModelMapper.class).insert(model);

                model = SqlSessionProvider.getSqlSession().getMapper(ModelDrivenTestModelMapper.class)
                                .selectByPrimaryKey(model.getId());

                log.debug(model.toString());

                ModelDrivenTestModel result = CommonDao.INSTANCE.selectById(ModelDrivenTestModel.class, model.getId());

                assertNotNull(result);
                
                assertEquals(testName, result.getName());
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#selectByName(java.lang.String)}
         * .
         */
        @Test
        public void testSelectByName()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#selectByExample(com.nbm.waf.core.modeldriven.Example)}
         * .
         */
        @Test
        public void testSelectByExample()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#countByExample(com.nbm.waf.core.modeldriven.Example)}
         * .
         */
        @Test
        public void testCountByExample()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#insert(java.lang.Object)}
         * .
         */
        @Test
        public void testInsert()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#insertSelective(java.lang.Object)}
         * .
         */
        @Test
        public void testInsertSelective()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#updateByExample(java.lang.Object, com.nbm.waf.core.modeldriven.Example)}
         * .
         */
        @Test
        public void testUpdateByExample()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#updateByExampleSelective(java.lang.Object, com.nbm.waf.core.modeldriven.Example)}
         * .
         */
        @Test
        public void testUpdateByExampleSelective()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#updateByPrimaryKeySelective(java.lang.Object)}
         * .
         */
        @Test
        public void testUpdateByPrimaryKeySelective()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#updateByPrimaryKey(java.lang.Object)}
         * .
         */
        @Test
        public void testUpdateByPrimaryKey()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#deleteByExample(com.nbm.waf.core.modeldriven.Example)}
         * .
         */
        @Test
        public void testDeleteByExample()
        {
                fail("Not yet implemented");
        }

        /**
         * Test method for
         * {@link com.nbm.core.modeldriven.dao.CommonDao#deleteByPrimaryKey(java.lang.Long)}
         * .
         */
        @Test
        public void testDeleteByPrimaryKey()
        {
                fail("Not yet implemented");
        }

}
