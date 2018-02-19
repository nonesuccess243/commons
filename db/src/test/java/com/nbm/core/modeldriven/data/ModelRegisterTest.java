package com.nbm.core.modeldriven.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.data.exception.ModelNotRegisterException;
import com.wayeasoft.core.configuration.Cfg;


public class ModelRegisterTest
{
        @SuppressWarnings("unused")
        private final static Logger log = LoggerFactory.getLogger(ModelRegisterTest.class);
       
        @Test
        public void test() throws ModelNotRegisterException
        {
                Cfg.I.set(ModelRegister.CFG_KEY, ModelRegister.CFG_DEFAULT_VALUE);

                assertNotNull(ModelRegister.INSTANCE.get("TestModel"));
                assertNotNull(ModelRegister.INSTANCE.get("TestModel2"));

                try
                {
                        assertNotNull(ModelRegister.INSTANCE.get("ModelUnknow"));
                        fail("should not find ModelUnknow");
                } catch (ModelNotRegisterException e)
                {
                        // 执行到这里是正常的
                }
        }
}
