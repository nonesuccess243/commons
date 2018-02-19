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

public class CommonDaoTest
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
                
                
                CrudGenerator.GENERATE_FILE = false;
                CrudGenerator generator = new CrudGenerator(ModelDrivenTestModel.class);
                generator.generate();
                
                SqlSessionProvider.getConncetion().createStatement().execute("DROP TABLE IF EXISTS "+ ModelMeta.getModelMeta(ModelDrivenTestModel.class).getDbTypeName());
                SqlSessionProvider.getConncetion().createStatement().execute(generator.getCreateSqlContent());
                SqlSessionProvider.getConncetion().commit();
                
        }

        @After
        public void tearDown() throws Exception
        {
                
        }

        @Test
        public void testSelectById()
        {
                
                CommonDao.get().selectById(ModelDrivenTestModel.class, 1l);
        }

        @Test
        public void testSelectByName()
        {
                CommonDao.get().selectByName(ModelDrivenTestModel.class, "abc");

        }

        @Test
        public void testSelectByExample()
        {
                CommonDao.get().selectByExample(ModelDrivenTestModel.class,
                                new CommonExample().createCriteria().andIdEqualTo(1l).andNameBetween("1", "2").finish()
                                                .or().andBetween("NAME", "1", "2").finish().orderBy("NAME")
                                                .distinct(true));
        }

        @Test
        public void testCountByExample()
        {
                assertNotNull(CommonDao.get().countByExample(ModelDrivenTestModel.class, null));
                
                assertEquals(new Integer(0),
                                CommonDao.get().countByExample(ModelDrivenTestModel.class, new CommonExample().createCriteria()
                                                .andIdEqualTo(1l).andNameBetween("1", "2").finish().or()
                                                .andBetween("NAME", "1", "2").finish().orderBy("NAME").distinct(true)));
        }

        @Test
        public void testInsert()
        {
                ModelDrivenTestModel model = new ModelDrivenTestModel();

                model.setRemark("remark");
                model.setName("abc");
                model.setYesOrNo(YesOrNo.NO);

                Long result = CommonDao.get().insert(model);
                
                
                assertNotNull(result);
                
                assertNotNull(model.getId());
                
                ModelDrivenTestModel model2 = CommonDao.get().selectById(ModelDrivenTestModel.class, model.getId());
                assertNotNull(model2);
                assertEquals(model.getName(), model2.getName());
                assertEquals(model.getYesOrNo(), model2.getYesOrNo());
        }

        @Test
        public void testUpdateById()
        {
                ModelDrivenTestModel model = new ModelDrivenTestModel();

                model.setId(1l);
                model.setRemark("after update remark");
                model.setName("after update");
                
                CommonDao.get().updateById(model);
        }

//        @Test
//        public void testUpdateByExample()
//        {
//                
//                DemoModel model = new DemoModel();
//                model.setId(1l);
//                model.setCount(1);
//                model.setName("after update");
//                
//                CommonDao.get().updateByExample(model, new CommonExample().createCriteria()
//                                .andIdEqualTo(1l).andNameBetween("1", "2").finish().or()
//                                .andBetween("NAME", "1", "2").finish());
//        }

        @Test
        public void testDeleteById()
        {
                CommonDao.get().deleteById(ModelDrivenTestModel.class, 4l);
        }

        @Test
        public void testDeleteByExample()
        {
                CommonDao.get().deleteByExample(ModelDrivenTestModel.class, new CommonExample().createCriteria()
                                .andIdEqualTo(1l).andNameBetween("1", "2").finish().or()
                                .andBetween("NAME", "1", "2").finish());
        }

}
