package com.wayeasoft.waf.demo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wayeasoft.springmvc.config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class })
public class CommonDataControllerTest
{
        @Autowired
        private CommonDataController commonDataController;

        @Test
        public void testQueryById()
        {
                commonDataController.queryById("", 1l);
                
        }

}
