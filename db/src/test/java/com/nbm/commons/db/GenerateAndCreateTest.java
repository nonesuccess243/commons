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

public class GenerateAndCreateTest
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
	        CrudGenerator.GENERATE_FILE = false;
	        CrudGenerator.CREATE_TABLE = true;
		
		
		CrudGenerator generator = Db.MYSQL
		                .generate(ModelDrivenTestModel.class);



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
