package com.wayeasoft.core.spring;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wayeasoft.core.spring.SpringContextUtil;
import com.wayeasoft.core.spring.test.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class SpringContextUtilTest
{

        @Test
        public void test()
        {
                SpringTestBean bean = SpringContextUtil.getInstance().getBean(SpringTestBean.class);
                assertNotNull(bean);
        }

}
