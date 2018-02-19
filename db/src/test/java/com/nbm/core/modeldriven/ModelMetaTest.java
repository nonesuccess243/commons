package com.nbm.core.modeldriven;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nbm.core.modeldriven.test.model.FkModel;
import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;

public class ModelMetaTest
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
                assertEquals("测试Model",ModelMeta.getModelMeta(ModelDrivenTestModel.class).getDisplayName());
        }
        @Test
        public void testDiscoverAndSimpleName()
        {
                ModelMeta.discover("com.nbm.core.modeldriven.test.model");
                assertNotNull(ModelMeta.getModelMetaBySimpleName("ModelDrivenTestModel"));
                
        }
        @Test
        public void testFk()
        {
//                ModelMeta.discover("com.nbm");
                
                ModelMeta.getModelMeta(FkModel.class);
//                
                
                assertTrue(ModelMeta.getModelMeta(FkModel.class).getField("modelDrivenTestModelId").isFk());
                assertEquals(ModelMeta.getModelMeta(FkModel.class).getField("modelDrivenTestModelId").getForeign(), ModelDrivenTestModel.class);

                
                assertTrue(ModelMeta.getModelMeta(FkModel.class).getField("otherModelDrivenTestModelId").isFk());
                assertEquals(ModelMeta.getModelMeta(FkModel.class).getField("otherModelDrivenTestModelId").getForeign(), ModelDrivenTestModel.class);
//                
        }

}
