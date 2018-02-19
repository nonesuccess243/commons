package com.nbm.core.modeldriven.data;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.PureModel;

public class PackageUtilsTest
{
        
        private final static Logger log = LoggerFactory.getLogger(PackageUtilsTest.class);

        @Test
        public void test()
        {
                assertTrue( PackageUtils.getClasses("com.nbm.core.modeldriven.data", PureModel.class).contains(TestModel2.class));
                assertTrue( PackageUtils.getClasses("com.nbm.core.modeldriven.data", PureModel.class).contains(TestModel.class));
                assertTrue( PackageUtils.getClasses("com.nbm.core.modeldriven", PureModel.class).contains(TestModel2.class));
 
                
                assertFalse( PackageUtils.getClasses("com.nbm.core.modeldriven", Object.class).contains(TestModel2.class));
                
                
                
                assertTrue( PackageUtils.getClasses(
                                Arrays.asList("com.nbm.core.modeldriven.data"), 
                                PureModel.class).contains(TestModel.class));
                
        }

}
