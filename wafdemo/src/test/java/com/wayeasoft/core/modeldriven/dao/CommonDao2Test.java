package com.wayeasoft.core.modeldriven.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wayeasoft.springmvc.config.RootConfig;
import com.wayeasoft.waf.demo.DemoModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class CommonDao2Test
{
        
        @Autowired
        private CommonDao2 dao;

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
        public void testSelectById()
        {
                assertNotNull(dao.selectById(DemoModel.class, 1l));
        }

        @Test
        public void testSelectByName()
        {
                assertNotNull(dao.selectByName(DemoModel.class, "abc"));

        }

        @Test
        public void testSelectByExample()
        {
                dao.selectByExample(DemoModel.class,
                                new CommonExample().createCriteria().andIdEqualTo(1l).andNameBetween("1", "2").finish()
                                                .or().andBetween("NAME", "1", "2").finish().orderBy("NAME")
                                                .distinct(true));
        }

        @Test
        public void testCountByExample()
        {
                assertNotNull(dao.countByExample(DemoModel.class, null));
                
                assertEquals(new Integer(0),
                                dao.countByExample(DemoModel.class, new CommonExample().createCriteria()
                                                .andIdEqualTo(1l).andNameBetween("1", "2").finish().or()
                                                .andBetween("NAME", "1", "2").finish().orderBy("NAME").distinct(true)));
        }

        @Test
        public void testInsert()
        {
                DemoModel model = new DemoModel();

                model.setCount(1);
                model.setName("abc");

                Long result = dao.insert(model);
                
                System.out.println("result = " + result);
                assertNotNull(result);
        }

        @Test
        public void testUpdateById()
        {
                DemoModel model = new DemoModel();

                model.setCount(1);
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
                dao.deleteById(DemoModel.class, 4l);
        }

        @Test
        public void testDeleteByExample()
        {
                dao.deleteByExample(DemoModel.class, new CommonExample().createCriteria()
                                .andIdEqualTo(1l).andNameBetween("1", "2").finish().or()
                                .andBetween("NAME", "1", "2").finish());
        }

}
