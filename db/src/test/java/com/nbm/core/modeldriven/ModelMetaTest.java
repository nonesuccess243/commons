package com.nbm.core.modeldriven;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

}
