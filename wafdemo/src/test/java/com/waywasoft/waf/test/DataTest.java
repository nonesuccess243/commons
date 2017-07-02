package com.waywasoft.waf.test;

import com.wayeasoft.core.modeldriven.dao.CommonDao;
import com.wayeasoft.springmvc.config.RootConfig;
import com.wayeasoft.waf.demo.DemoModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by niyuzhe on 7/2/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
        { RootConfig.class })
public class DataTest
{

        @Autowired
        private CommonDao dao;


        @Test
        public void test() throws Exception
        {
                for( int i = 0; i<100; i++)
                {
                        DemoModel model = new DemoModel();
                        model.setName("DEMO_MODEL_" +i);
                        model.setCount((int)System.currentTimeMillis()%100);
                        model.setSomeDate(new Date());
                        model.setSomeTime(new Date());
                        dao.insert(model);
                }

        }

}
