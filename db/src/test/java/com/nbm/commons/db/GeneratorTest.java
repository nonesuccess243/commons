package com.nbm.commons.db;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.core.modeldriven.test.model.ModelDrivenTestModel;
import com.younker.waf.db.DataSourceProvider;

public class GeneratorTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		DataSourceProvider.initSimple("org.h2.Driver",
		                "jdbc:h2:~/.h2/modeldriven_test;AUTO_SERVER=TRUE", "sa", "");
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
	public final void test() throws Exception
	{
		
		
		DataSourceProvider.instance().runBatch(
		                "DROP TABLE IF EXISTS TEST_MODEL");
		
		CrudGenerator generator = Db.MYSQL
		                .generate(ModelDrivenTestModel.class);

//		DataSourceProvider.instance().runBatch(
//		                generator.getCreateSqlContent());
		

		DataSourceProvider.instance().runBatch(
		                "DROP TABLE TEST_MODEL");

		generator = Db.ORACLE.generate(ModelDrivenTestModel.class);

//		DataSourceProvider.instance().runBatch(
//		                generator.getCreateSqlContent());
//		
//		DataSourceProvider.instance().runBatch(
//		                "DROP TABLE TEST_MODEL");
//		DataSourceProvider.instance().runBatch("DROP SEQUENCE TEST_MODEL_SEQ");

		// Db.MYSQL.generate(ModelDrivenTestModel.class);
		//
		// CrudGenerator.db = Db.ORACLE;
		// new CrudGenerator(ModelDrivenTestModel.class).generate();
	}

	// @Test
	// public final void testGenericDao()
	// {
	// Dao.getMapperByModelClass(ModelDrivenTestModel.class);
	// Dao.getMapperByModelClass(ModelDrivenTestModel.class);
	// Dao.getMapperByModelClass(ModelDrivenTestModel.class);
	// Dao.getMapperByModelClass(ModelDrivenTestModel.class);
	//
	//
	// }
}
