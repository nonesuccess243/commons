package com.younker.waf.db.mybatis;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;
import com.wayeasoft.test.spring.RootConfig;
import com.younker.waf.db.DataSourceProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ RootConfig.class })
public class MybatisDaoTest
{
        @Autowired
        private DataSource dataSource;

        
        @Before
        public void setUp() throws Exception
        {
                DataSourceProvider.initInstance(dataSource);
                MybatisDao.INSTANCE.initAuto();
                SqlSessionProvider.openSession();
                
                CrudGenerator.GENERATE_FILE = false;
                CrudGenerator generator = new CrudGenerator(ModelDrivenTestModel.class);
                generator.generate();
                
                
                SqlSessionProvider.getConncetion().createStatement().execute("DROP TABLE IF EXISTS "+ ModelMeta.getModelMeta(ModelDrivenTestModel.class).getDbTypeName());
                SqlSessionProvider.getConncetion().createStatement().execute(generator.getCreateSqlContent());
                
        }
        
        @Test
        public void testInitAuto()
        {
                
                
                
                
                CommonDao.get().selectByExample(ModelDrivenTestModel.class,
                                new CommonExample().createCriteria().andIdEqualTo(1l).andNameBetween("1", "2").finish()
                                .or().andBetween("NAME", "1", "2").finish().orderBy("NAME")
                                .distinct(true));
                
                SqlSessionProvider.getSqlSession().commit();
        }
        
        @Test
        public void testResource()
        {
              //scan urls that contain 'my.package', include inputs starting with 'my.package', use the default scanners
                Reflections reflections = new Reflections("com", new ResourcesScanner());

                Set<String> properties = 
                                reflections.getResources(Pattern.compile(".*Mapper.xml"));
                
                System.out.println("find " + properties);
        }

}
