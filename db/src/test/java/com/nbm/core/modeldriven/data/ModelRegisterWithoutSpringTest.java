package com.nbm.core.modeldriven.data;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nbm.core.modeldriven.data.exception.ModelNotRegisterException;

public class ModelRegisterWithoutSpringTest
{

        @Test
        public void test()
        {
//                ModelRegister.INSTANCE.warmUp("com.nbm.core.modeldriven.data");


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
