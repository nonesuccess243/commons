//package com.nbm.commons.db;
//
//import static org.junit.Assert.*;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.ibatis.mapping.Environment;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.ibatis.transaction.TransactionFactory;
//import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
//import com.nbm.core.modeldriven.ModelMeta;
//import com.nbm.core.modeldriven.dao.CommonDao;
//import com.nbm.core.modeldriven.dao.CommonMapper;
//import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;
//import com.nbm.core.modeldriven.test.model.dao.ModelDrivenTestModelMapper;
//import com.younker.waf.db.mybatis.MybatisDao;
//import com.younker.waf.db.mybatis.SqlSessionProvider;
//
//public class WithDbTest
//{
//        
//        private final static Logger log = LoggerFactory.getLogger(WithDbTest.class);
//        
//        @BeforeClass
//        public static void setUpBeforeClass()
//        {
//                
//                MysqlDataSource dataSource = new MysqlDataSource();
//                dataSource.setUser("root");
//                dataSource.setURL("jdbc:mysql://localhost:3306/test");
//
//                TransactionFactory transactionFactory = new JdbcTransactionFactory();
//                Environment environment = new Environment("development", transactionFactory,
//                                dataSource);
//                Configuration configuration = new Configuration(environment);
//                // configuration.addMapper(BlogMapper.class);
//
////                configuration.addLoadedResource(
////                                "com/nbm/core/modeldriven/test/model/dao/ModelDrivenTestModelMapper.xml");
//
//                 configuration.addMapper(CommonMapper.class);
//
//                
//                MybatisDao.INSTANCE.initFactory(new SqlSessionFactoryBuilder()
//                                .build(configuration));
//                
//                SqlSessionProvider.openSession();
//        }
//
//        @Test
//        public void test() throws InstantiationException, IllegalAccessException, InvocationTargetException
//        {
//
//                
//
////                ModelDrivenTestModel result = session.getMapper(ModelDrivenTestModelMapper.class)
////                                .selectByPrimaryKey(new ModelMeta(ModelDrivenTestModel.class), 2l);
////                session.select(statement, parameter, handler);
//                
//                ModelDrivenTestModel result = CommonDao.INSTANCE.selectById(ModelDrivenTestModel.class, 1l);
//                
//                assertNotNull(result);
//                assertEquals((Long)1l, result.getId());
//                assertNotNull( result.getModelCreateTime());
//        }
//
//}
