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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.enums.YesOrNo;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;
import com.wayeasoft.test.spring.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class CommonDaoTest
{
        
        @Autowired
        private CommonDao dao;
        
        @Autowired
        private DataSource dataSource;

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
                CrudGenerator.GENERATE_FILE = false;
                CrudGenerator generator = new CrudGenerator(ModelDrivenTestModel.class);
                generator.generate();
                
                
                dataSource.getConnection().createStatement().execute("DROP TABLE IF EXISTS "+ ModelMeta.getModelMeta(ModelDrivenTestModel.class).getDbTypeName());
                dataSource.getConnection().createStatement().execute(generator.getCreateSqlContent());
                
        }

        @After
        public void tearDown() throws Exception
        {
        }

        @Test
        public void testSelectById()
        {
                dao.selectById(ModelDrivenTestModel.class, 1l);
        }

        @Test
        public void testSelectByName()
        {
                dao.selectByName(ModelDrivenTestModel.class, "abc");

        }

        @Test
        public void testSelectByExample()
        {
                dao.selectByExample(ModelDrivenTestModel.class,
                                new CommonExample().createCriteria().andIdEqualTo(1l).andNameBetween("1", "2").finish()
                                                .or().andBetween("NAME", "1", "2").finish().orderBy("NAME")
                                                .distinct(true));
        }

        @Test
        public void testCountByExample()
        {
                assertNotNull(dao.countByExample(ModelDrivenTestModel.class, null));
                
                assertEquals(new Integer(0),
                                dao.countByExample(ModelDrivenTestModel.class, new CommonExample().createCriteria()
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

                Long result = dao.insert(model);
                
                assertNotNull(result);
                
                assertNotNull(model.getId());
                
                ModelDrivenTestModel model2 = dao.selectById(ModelDrivenTestModel.class, model.getId());
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
                
                dao.updateById(model);
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
//                dao.updateByExample(model, new CommonExample().createCriteria()
//                                .andIdEqualTo(1l).andNameBetween("1", "2").finish().or()
//                                .andBetween("NAME", "1", "2").finish());
//        }

        @Test
        public void testDeleteById()
        {
                dao.deleteById(ModelDrivenTestModel.class, 4l);
        }

        @Test
        public void testDeleteByExample()
        {
                dao.deleteByExample(ModelDrivenTestModel.class, new CommonExample().createCriteria()
                                .andIdEqualTo(1l).andNameBetween("1", "2").finish().or()
                                .andBetween("NAME", "1", "2").finish());
        }

}
