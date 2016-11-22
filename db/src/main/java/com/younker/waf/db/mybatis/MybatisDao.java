package com.younker.waf.db.mybatis;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.nbm.exception.NbmBaseRuntimeException;
import com.nbm.waf.core.modeldriven.Mapper;
import com.younker.waf.db.DataSourceProvider;

/**
 * 通用Dao，底层调用Mybatis相关工具。 依赖MybatisConfig类的初始化工作。
 * 
 * @see com.younker.waf.db.mybatis.MybatisConfig</br>
 * 
 *      采用public static方式的简单单例，可以采用反射方式破解。
 */
public enum MybatisDao
{
	INSTANCE;

	private final static Logger log = LoggerFactory
	                .getLogger(MybatisDao.class);

	static AtomicInteger sessionCount = new AtomicInteger(0);

	private MybatisDao()
	{
	}

	// public static final MybatisDao INSTANCE = new MybatisDao();

	private Map<Thread, SqlSession> sqlSessionByThread = new HashMap<>();

	private Map<HttpServletRequest, SqlSession> sqlSessionByRequest = new HashMap<>();

	private SqlSessionFactory sessionFactory;

	/**
	 * 逐步向默认配置文件路径的方向过渡
	 * 
	 * @param configFile
	 * @throws IOException
	 */
	@Deprecated
	public void init(URL configFile) throws IOException
	{
		try
		{
			sessionFactory = new SqlSessionFactoryBuilder()
			                .build(configFile.openStream());
			log.info("Mybatis初始化成功");
		} catch (IOException e)
		{
			// 发生配置文件不能获取的异常后，此处无法解决问题，直接转换为运行时异常抛出
			log.error("初始化Mybatis发生异常，将造成项目整体不能运行[configFile="
			                + configFile + "].", e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * 在classpath中找configuration.xml
	 * 
	 * @throws IOException
	 */
	public void init()
	{
		try
		{
			sessionFactory = new SqlSessionFactoryBuilder()
			                .build(Resources.getResourceAsReader("mybatis-config.xml"));
			log.info("Mybatis初始化成功");
		} catch (IOException e)
		{
			// 发生配置文件不能获取的异常后，此处无法解决问题，直接转换为运行时异常抛出
			log.error("初始化Mybatis发生异常，将造成项目整体不能运行[configFile=mybatis-config.xml].",
			                e);
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * 自动搜索所有的配置文件并初始化
	 * @param packageName
	 */
	public void initAuto(String packageName)
	{
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development",
		                transactionFactory, DataSourceProvider
		                                .instance().getDataSource());
		Configuration configuration = new Configuration(environment);

		try
                {
	                for (ClassInfo classInfo : ClassPath.from(getClass().getClassLoader()).getTopLevelClassesRecursive(packageName))
	                {
	                	Class<?> modelClass = classInfo.load();
	                	// log.info("start class[{}]", className.getName());
	                	if (Mapper.class.isAssignableFrom(modelClass)
	                	                && !modelClass.isAnonymousClass() 
	                	                && !modelClass.equals(Mapper.class)
	                	                //&& !modelClass.isInterface()
	                	                //&& !Modifier.isAbstract(modelClass.getModifiers())
	                	                )
	                	{
	                		log.debug("find Mapper and add: {}", modelClass);
	                		configuration.addMapper(modelClass);
	                	}
	                }
                } catch (IOException e)
                {
	                throw new NbmBaseRuntimeException("", e);
                }
		
		 MybatisDao.INSTANCE.initFactory(new SqlSessionFactoryBuilder()
		 	.build(configuration));

	}
	

	/**
	 * 获取SessionFactory。可考虑在以后的重构中，将此函数改为private，并在Dao类中提供session的相关方法
	 */
	private synchronized SqlSession getSession()
	{

		if (sessionFactory == null)
		{
			throw new IllegalArgumentException(
			                "SqlSessionFactory为空，Mybatis尚未初始化");
		}

		SqlSession sqlsession = sessionFactory.openSession();
		sessionCount.getAndIncrement();
		log.debug("新打开一个sqlsession，当前数量为：" + sessionCount.get());
		return sqlsession;
	}

	synchronized SqlSession getSession(Thread thread)
	{

		SqlSession session = getSession();
		sqlSessionByThread.put(thread, session);

		return session;

	}

	synchronized SqlSession getSession(HttpServletRequest request)
	{

		SqlSession session = getSession();
		sqlSessionByRequest.put(request, session);

		return session;

	}

	private void debugSessionInfo(String info)
	{
		// try
		// {
		// StringBuilder sb = new StringBuilder("sqlsession信息\n" + info
		// + "\n");
		// sb.append(StringUtil.getString("thread sqlsession池中共有session{}个",
		// sqlSessionByThread.size())).append("\n");
		// for (Entry<Thread, SqlSession> entry :
		// Collections.unmodifiableMap(
		// sqlSessionByThread).entrySet())
		// {
		// sb.append(StringUtil.getString("线程id[{}]， 线程状态[{}]",
		// entry.getKey()
		// .toString(), entry.getKey().getState())).append(
		// "\n");
		// if (entry.getKey().getState() == Thread.State.TERMINATED)
		// {
		// sb.append("sqlsession泄露\n");
		// }
		// }
		//
		// sb.append(StringUtil.getString("request sqlsession池中共有session{}个",
		// sqlSessionByRequest.size())).append("\n");
		// for (Entry<HttpServletRequest, SqlSession> entry :
		// Collections
		// .unmodifiableMap(sqlSessionByRequest).entrySet())
		// {
		// User user = RuntimeUserUtils.INSTANCE.getUserByRequest(entry
		// .getKey());
		// String loginname = user == null ? "" : user.getLoginname();
		// sb.append(StringUtil.getString("request[{}]，user[{}]", entry
		// .getKey().getRequestURI(), loginname)).append("\n");
		// }
		//
		// log.debug(sb.toString());
		// } catch (Exception e)
		// {
		// log.error("打印异常调试信息时出现异常", e);
		// }
	}

	// public SqlSession getBatchSession()
	// {
	// if (sessionFactory == null)
	// {
	// throw new
	// IllegalArgumentException("SqlSessionFactory为空，Mybatis尚未初始化");
	// }
	//
	// return sessionFactory.openSession(ExecutorType.BATCH);
	// }

	private void closeSession(SqlSession session)
	{
		try
		{
			session.commit();
		} catch (Exception e)
		{
			log.error("提交到数据库发生错误", e);
		} finally
		{
			session.close();
			sessionCount.getAndDecrement();
			log.debug("关闭一个sqlsession，当前数量为：" + sessionCount.get());
		}
	}

	void closeSession(SqlSession session, Thread thread)
	{
		closeSession(session);
		sqlSessionByThread.remove(thread);
	}

	void closeSession(SqlSession session, HttpServletRequest request)
	{
		try
		{
			closeSession(session);
			sqlSessionByRequest.remove(request);
		} catch (Exception e)
		{
			log.error("", e);
		}
	}

	public void doTransact(TransactionHelper helper)
	{
		SqlSession session = getSession();
		try
		{
			helper.transact(session);
		} catch (Exception e)
		{
			log.error("执行数据库操作发生异常", e);
			session.rollback();
		} finally
		{
			closeSession(session);
		}
	}

	public interface TransactionHelper
	{
		public void transact(SqlSession session);

	}

	public void initTestFactory()
	{
		try
		{
			sessionFactory = new SqlSessionFactoryBuilder()
			                .build(Resources.getResourceAsReader("configuration_test.xml"));
		} catch (IOException e)
		{
			log.error("", e);
		}

		log.info("mybatis初始化完成");
	}

	/**
	 * 直接使用外部初始化的sessionFactory
	 * 
	 * @param sessionFactory
	 */
	private void initFactory(SqlSessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public static void main(String[] args)
	{
		MybatisDao.INSTANCE.initTestFactory();

	}

}
