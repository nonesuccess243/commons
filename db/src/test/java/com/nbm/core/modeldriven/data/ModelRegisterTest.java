package com.nbm.core.modeldriven.data;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbm.core.modeldriven.data.ModelRegister;
import com.nbm.core.modeldriven.data.exception.ModelNotRegisterException;
import com.wayeasoft.test.spring.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class ModelRegisterTest
{
        @SuppressWarnings("unused")
        private final static Logger log = LoggerFactory.getLogger(ModelRegisterTest.class);

        @Autowired
        ModelRegister modelRegister;

        @Test
        public void test() throws ModelNotRegisterException
        {
                assertNotNull(modelRegister);

                assertNotNull(modelRegister.get("TestModel"));
                assertNotNull(modelRegister.get("TestModel2"));

                try
                {
                        assertNotNull(modelRegister.get("ModelUnknow"));
                        fail("should not find ModelUnknow");
                } catch (ModelNotRegisterException e)
                {
                        // 执行到这里是正常的
                }
        }

        
}
