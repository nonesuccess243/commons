package com.nbm.commons.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.core.modeldriven.h2.test.TestModelOracle;
import com.nbm.core.modeldriven.h2.test.dao.TestModelOracleMapper;
import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;
import com.nbm.core.modeldriven.test.model.dao.ModelDrivenTestModelExample;
import com.nbm.core.modeldriven.test.model.dao.ModelDrivenTestModelMapper;
import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.db.mybatis.MybatisDao;
import com.younker.waf.db.mybatis.SqlSessionProvider;

public class H2Test
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testFile() throws SQLException
	{
		
		//文件保存在~下，基本上不会有权限问题。.h2目录如果不存在，可以自动创建
		//AUTO_SERVER=TRUE的连接参数. 第一个打开数据库文件的进程，将自觉兼任H2 Server的角色，并把自己的地址和端口写到lock文件里， 第二个连进来的进程，发现lock文件存在，就会读取并通过网络方式来访问H2
		DataSourceProvider.initSimple("org.h2.Driver", "jdbc:h2:~/.h2/testdb;AUTO_SERVER=TRUE", "sa", "");
		DataSourceProvider.instance().runBatch("select 1");
	}
	

	@Test
	public void testMem() throws SQLException
	{
		//数据库完全在内存中。
		//参数DB_CLOSE_DELAY是要求最后一个正在连接的连接断开后，不要关闭DB，因为下一个case可能还会有新连接进来。
		DataSourceProvider.initSimple("org.h2.Driver", "jdbc:h2:mem:DBName;DB_CLOSE_DELAY=-1", "sa", "");
		DataSourceProvider.instance().runBatch("select 1");
	}
	
	@Test
	public void testMybatis() throws SQLException, IOException
	{
		DataSourceProvider.initSimple("org.h2.Driver", "jdbc:h2:./src/test/resources/testdb;AUTO_SERVER=TRUE", "sa", "");
		
		
		
		CrudGenerator generator = new CrudGenerator(TestModelOracle.class);
		
		DataSourceProvider.instance().runBatch(
		                "DROP TABLE IF EXISTS " + generator.getMeta().getDbTypeName());
		DataSourceProvider.instance().runBatch(
		                "DROP SEQUENCE IF EXISTS " + generator.getMeta().getDbTypeName() + "_SEQ");
		
		DataSourceProvider.instance().runBatch(
		                Paths.get("./src/test/resources/com/nbm/core/modeldriven/h2/test/dao/" + generator.getMeta().getDbTypeName() + ".sql"));
		
		MybatisDao.INSTANCE.initAuto("com.nbm.core.modeldriven.h2.test.dao");
		
		SqlSessionProvider.openSession();
		Long result = SqlSessionProvider.getSqlSession().getMapper(TestModelOracleMapper.class).insert(new TestModelOracle());
		
		assertNotEquals(new Long(0l), result);
		
		SqlSessionProvider.getSqlSession().commit();
		
		ModelDrivenTestModelExample example = new ModelDrivenTestModelExample();
		
		example.createCriteria().andIdIsNull().andNameBetween("abc", "bbc");
		
//		List<ModelDrivenTestModel> result = SqlSessionProvider.getSqlSession().getMapper(ModelDrivenTestModelMapper.class).selectByExample(example);
		
		
		//恢复现场
		
		DataSourceProvider.instance().runBatch(
		                "DROP SEQUENCE " + generator.getMeta().getDbTypeName() + "_SEQ");
		
		DataSourceProvider.instance().runBatch(
		                "DROP TABLE " + generator.getMeta().getDbTypeName());
		
		SqlSessionProvider.getSqlSession().commit();
		SqlSessionProvider.getSqlSession().close();
		
	}

}
