package com.younker.waf.db.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.data.PackageUtils;
import com.nbm.exception.NbmBaseRuntimeException;
import com.nbm.waf.core.modeldriven.Mapper;
import com.wayeasoft.core.configuration.Cfg;
import com.wayeasoft.core.configuration.Cfgable;
import com.younker.waf.db.DataSourceProvider;

/**
 * 通用Dao，底层调用Mybatis相关工具。 依赖MybatisConfig类的初始化工作。
 * 
 * @see com.younker.waf.db.mybatis.MybatisConfig</br>
 * 
 *      采用public static方式的简单单例，可以采用反射方式破解。
 */
@Cfgable(key = MybatisDao.PACKAGE_CFG_KEY, type = String[].class, defaultValue =
{ "com.nbm", "com.wayeasoft" }, description = "mybatis自动初始化时扫描的包，可支持配置多个，逗号分隔")
@Cfgable(key = MybatisDao.MAPPER_PATTERN_CFG_KEY, type = String[].class, defaultValue = ".*Mapper.xml", description = "扫描mapper文件的模式，正则表达式")
public enum MybatisDao
{
        INSTANCE;

        private final static Logger log = LoggerFactory.getLogger(MybatisDao.class);

        public final static String PACKAGE_CFG_KEY = "commons.mybatis.packages";
        private final static String[] DEFAULT_PACKAGE = new String[]
        { "com.nbm", "com.wayeasoft" };

        public final static String MAPPER_PATTERN_CFG_KEY = "commons.mybatis.mapper_pattern";
        private final static String DEFAULT_MAPPER_PATTERN = ".*Mapper.xml";

        static AtomicInteger sessionCount = new AtomicInteger(0);

        private MybatisDao()
        {
        }

        // public static final MybatisDao INSTANCE = new MybatisDao();

        // private Map<Thread, SqlSession> sqlSessionByThread = new HashMap<>();

        // private ThreadLocal<SqlSessionInfo> sqlSessionInfoByThread = new
        // ThreadLocal<>();

        // private Map<HttpServletRequest, SqlSession> sqlSessionByRequest = new
        // HashMap<>();

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
                        sessionFactory = new SqlSessionFactoryBuilder().build(configFile.openStream());
                        log.info("Mybatis初始化成功");
                } catch (IOException e)
                {
                        // 发生配置文件不能获取的异常后，此处无法解决问题，直接转换为运行时异常抛出
                        log.error("初始化Mybatis发生异常，将造成项目整体不能运行[configFile=" + configFile + "].", e);
                        throw new RuntimeException(e);
                }

        }

        /**
         * 在classpath中找mybatis-config.xml
         * 
         * 逐步过渡到scanAndInit方式进行初始化
         * 
         * @throws IOException
         */
        @Deprecated
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
                        log.error("初始化Mybatis发生异常，将造成项目整体不能运行[configFile=mybatis-config.xml].", e);
                        throw new RuntimeException(e);
                }

        }

        /**
         * 自动搜索所有的配置文件并初始化
         * 
         * 当自动载入mapper类和mapper xml文件发生错误时，会打印错误信息，并继续进行
         * 
         * @param packageName
         */
        public void scanAndInit()
        {
                // 初始化基本参数
                TransactionFactory transactionFactory = new JdbcTransactionFactory();
                Environment environment = new Environment("development", transactionFactory,
                                DataSourceProvider.instance().getDataSource());
                Configuration configuration = new Configuration(environment);
                //下面这句必须写在这里，因为后面处理mapper xml文件时已经会用到databaseId了
                configuration.setDatabaseId(Cfg.I.get("commons.mybatis.package", String.class, calcDatabaseId()));


//                 注册commonMapper
                try(InputStream inputStream
                                = Resources
                                .getResourceAsStream(CommonDao.MAPPER_PATH))
                {
                        new XMLMapperBuilder(inputStream, configuration, "com/younker/waf/db/mybatis/CommonMapper.xml", configuration.getSqlFragments()).parse();
                } catch (IOException e)
                {
                        //should not happen
                        throw new NbmBaseRuntimeException("找不到commonmapper配置文件", e);
                }

                // 根据配置信息，注册各种Mapper
                String[] packageNames = Cfg.I.get("commons.mybatis.package", String[].class, DEFAULT_PACKAGE);

                for (String packageName : packageNames)
                {
                        for (@SuppressWarnings("rawtypes")/*不清楚此处的泛型怎么写才能没有警告*/
                                Class<? extends Mapper> modelClass : PackageUtils.getClasses(packageName, Mapper.class))
                        {
                                if (Mapper.class.isAssignableFrom(modelClass) && !modelClass.isAnonymousClass()
                                                && !modelClass.equals(Mapper.class)
                                )
                                {
                                        log.debug("find Mapper and add: {}", modelClass);
                                        configuration.addMapper(modelClass);
                                }
                        }
                }
                
                for (String packageName : packageNames)
                {
                        Reflections reflections = new Reflections(packageName, new ResourcesScanner());

                        Set<String> properties = 
                                        reflections.getResources(Pattern.compile(Cfg.I.get(MAPPER_PATTERN_CFG_KEY, String.class, DEFAULT_MAPPER_PATTERN)));
                        for( String mapperResourcePath : properties )
                        {
                                try(InputStream inputStream
                                                = Resources
                                                .getResourceAsStream(mapperResourcePath))
                                {
                                        log.info("找到mybatis配置文件[file={}]", mapperResourcePath);
                                        new XMLMapperBuilder(inputStream, configuration, mapperResourcePath, configuration.getSqlFragments()).parse();
                                } catch (IOException e)
                                {
                                        //should not happen
                                        throw new NbmBaseRuntimeException("找不到配置文件", e).set("mapperResourcePath", mapperResourcePath);
                                }catch(Exception e )
                                {
                                        log.error("解析mybatis配置文件发生错误[mapperResourcePath" + mapperResourcePath + "]." + e.getMessage());
                                        continue;
                                }
                        }
                }
                
                
                MybatisDao.INSTANCE.initFactory(new SqlSessionFactoryBuilder().build(configuration));

        }

        private String calcDatabaseId()
        {
                return Db.getByProductName(DataSourceProvider.instance().getDatabaseProductName()).toString();
        }

        /**
         * 获取SessionFactory。可考虑在以后的重构中，将此函数改为private，并在Dao类中提供session的相关方法
         */
        synchronized SqlSession getSession()
        {

                if (sessionFactory == null)
                {
                        throw new IllegalArgumentException("SqlSessionFactory为空，Mybatis尚未初始化");
                }

                SqlSession sqlsession = sessionFactory.openSession();
                sessionCount.getAndIncrement();
                log.debug("新打开一个sqlsession，当前数量为：" + sessionCount.get());
                return sqlsession;
        }

        // synchronized SqlSession getSession(Thread thread)
        // {
        //
        //
        // SqlSession session = getSession();
        //// sqlSessionByThread.put(thread, session);
        //
        // return session;
        //
        // }

        // synchronized SqlSession getSession(HttpServletRequest request)
        // {
        //
        // SqlSession session = getSession();
        // sqlSessionByRequest.put(request, session);
        //
        // return session;
        //
        // }

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

        void closeSession(SqlSession session)
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

        // void closeSession(SqlSession session, Thread thread)
        // {
        // closeSession(session);
        //// sqlSessionByThread.remove(thread);
        // }

        void closeSession(SqlSession session, HttpServletRequest request)
        {
                try
                {
                        closeSession(session);
                        // sqlSessionByRequest.remove(request);
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
