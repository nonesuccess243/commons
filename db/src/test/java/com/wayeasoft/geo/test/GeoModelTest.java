package com.wayeasoft.geo.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wayeasoft.core.modeldriven.dao.CommonDao;
import com.wayeasoft.spatial.shape.Point;
import com.wayeasoft.test.spring.RootConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class })
public class GeoModelTest
{
        

        @Autowired
        private CommonDao dao;
        

        @Test
        public void testSelect()
        {
                Point point = dao.selectById(GeoModel.class, 1l).getPoint();
                assertNotNull(point);
        }
        
        @Test
        public void testInsert()
        {
                GeoModel model = new GeoModel();
                
                Point point = new Point();
                
                point.setX(117.2121);
                point.setY(115.23232);
                
                model.setPoint(point);
                
                dao.insert(model);
        }
        
        @Test
        public void testUpdate()
        {
                GeoModel model = new GeoModel();
                model.setId(1l);
                Point point = new Point();
                
                point.setX(217.2121);
                point.setY(125.23232);
                
                model.setPoint(point);
                
                dao.updateById(model);
        }

}
