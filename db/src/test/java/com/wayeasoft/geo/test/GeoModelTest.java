package com.wayeasoft.geo.test;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.wayeasoft.core.modeldriven.dao.CommonDao;
import com.wayeasoft.spatial.shape.Point;
import com.wayeasoft.test.spring.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class GeoModelTest
{

        @Autowired
        private CommonDao dao;

        @Autowired
        private DataSource dataSource;

        @Before
        public void setUp() throws Exception
        {
                CrudGenerator.GENERATE_FILE = false;
                CrudGenerator generator = new CrudGenerator(GeoModel.class);
                generator.generate();

                dataSource.getConnection().createStatement()
                                .execute("DROP TABLE IF EXISTS " + ModelMeta
                                                .getModelMeta(GeoModel.class).getDbTypeName());
                dataSource.getConnection().createStatement()
                                .execute(generator.getCreateSqlContent());

        }

        @Test
        public void testInsertAndSelect()
        {

                //insert
                GeoModel model = new GeoModel();

                Point point = new Point();

                point.setX(117.2121);
                point.setY(115.23232);

                model.setPoint(point);

                dao.insert(model);
                
                assertNotNull(model.getId());
                
                //select
                Point point2 = dao.selectById(GeoModel.class, model.getId()).getPoint();
                assertNotNull(point2);
                assertEquals(point.getX(), point2.getX(), 0);
                assertEquals(point.getY(), point2.getY(), 0);
        }


        @Test
        public void testUpdate()
        {
                
                //insert
                GeoModel model = new GeoModel();

                Point point = new Point();

                point.setX(117.2121);
                point.setY(115.23232);

                model.setPoint(point);

                dao.insert(model);
                
                assertNotNull(model.getId());
                
                
                //update
                GeoModel model2 = new GeoModel();
                model2.setId(model.getId());
                Point point2 = new Point();

                point2.setX(217.2121);
                point2.setY(125.23232);

                model2.setPoint(point2);

                dao.updateById(model2);
                
                
                //select and compare
                
                Point point3 = dao.selectById(GeoModel.class, model.getId()).getPoint();
                assertNotNull(point3);
                assertEquals(point2.getX(), point3.getX(), 0);
                assertEquals(point2.getY(), point3.getY(), 0);
        }

}
