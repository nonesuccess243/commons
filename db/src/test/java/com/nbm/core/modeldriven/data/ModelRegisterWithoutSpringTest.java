package com.nbm.core.modeldriven.data;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nbm.core.modeldriven.data.exception.ModelNotRegisterException;

public class ModelRegisterWithoutSpringTest
{

        @Test
        public void test()
        {
                ModelRegister register = new ModelRegister();
                register.warmUp("com.nbm.core.modeldriven.data");


                assertNotNull(register.get("TestModel"));
                assertNotNull(register.get("TestModel2"));

                try
                {
                        assertNotNull(register.get("ModelUnknow"));
                        fail("should not find ModelUnknow");
                } catch (ModelNotRegisterException e)
                {
                        // 执行到这里是正常的
                }
        }

}
