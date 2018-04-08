package com.wayeasoft.core.modeldriven.dao;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.data.ModelRegister;
import com.nbm.core.modeldriven.generator.CrudGenerator;
import com.nbm.exception.NbmBaseRuntimeException;
import com.wayeasoft.core.configuration.Cfg;
import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.db.mybatis.CommonDao;
import com.younker.waf.db.mybatis.MybatisDao;
import com.younker.waf.db.mybatis.SqlSessionProvider;

public class DbBooleanTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DataSourceProvider.initSimple();
		MybatisDao.INSTANCE.scanAndInit();
		SqlSessionProvider.openSession();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SqlSessionProvider.getSqlSession().commit();
	}

	@Before
	public void setUp() throws Exception {
		try (Connection connection = DataSourceProvider.instance().getDataSource().getConnection()) {
			boolean autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);

			// 创建表
			for (Iterator<ModelMeta> iter = ModelRegister.INSTANCE.getAllModel().iterator(); iter.hasNext();) {
				ModelMeta meta = iter.next();
				try {

					CrudGenerator.GENERATE_FILE = false;
					CrudGenerator.db = Db.getByProductName(DataSourceProvider.instance().getDatabaseProductName());
					CrudGenerator generator = new CrudGenerator(meta.getModelClass());
					generator.generate();

					ResultSet tableMetaResultSet = connection.getMetaData().getTables(null, null, meta.getDbTypeName(),
							null);
					if (tableMetaResultSet.next())// 找到这张表了
					{

					} else// 不存在这张表
					{
						connection.createStatement().execute(generator.getCreateSqlContent());
						connection.commit();
					}
				} catch (Exception e) {
					// throw new NbmBaseRuntimeException("DB
					// boot 发生异常", e).set("model",
					// meta.getModelClass());
					continue;
				}

			}

			// 执行sql
			String[] sqlPaths = Cfg.I.get("commons.db_booter.sql", String[].class, new String[] {});
			for (String sqlPath : sqlPaths) {

				try {
					String sql = new String(Files
							.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource(sqlPath).toURI())));

					DataSourceProvider.instance().runBatch(sql);
				} catch (Exception e) {
				}
			}
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e1) {
			throw new NbmBaseRuntimeException("DB boot 发生异常", e1);
		}
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test() {
		CommonDao dao = CommonDao.get();
		BoolTest boolTest = new BoolTest();
		boolTest.setBooltest(false);
		Long id = dao.insert(boolTest);
		BoolTest result = dao.selectById(BoolTest.class, id);
		assertEquals(result.isBooltest(),boolTest.isBooltest());
		BoolTest boolTest2 = new BoolTest();
		boolTest2.setBooltest(true);
		Long id2 = dao.insert(boolTest2);
		BoolTest result2 = dao.selectById(BoolTest.class, id2);
		assertEquals(result2.isBooltest(),boolTest2.isBooltest());
	}

}
