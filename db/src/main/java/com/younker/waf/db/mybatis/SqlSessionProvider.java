package com.younker.waf.db.mybatis;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlSessionProvider
{
        private final static Logger log = LoggerFactory.getLogger(SqlSessionProvider.class);

        private static ThreadLocal<SqlSession> sqlThreadSessionMap = new ThreadLocal<SqlSession>();

        public static synchronized SqlSession getSqlSession()
        {
                if (sqlThreadSessionMap.get() == null)
                {
                        throw new RuntimeException("当前线程中无sqlsession");
                }

                return sqlThreadSessionMap.get();

        }

        public static Connection getConnecetion()
        {
                return SqlSessionProvider.getSqlSession().getConnection();
        }

        public static SqlSession openSession()
        {

                return putSqlSessionInThreadMap();
        }

        private static SqlSession putSqlSessionInThreadMap()
        {
                if (sqlThreadSessionMap.get() != null)
                {
                        return sqlThreadSessionMap.get();
                        // throw new
                        // BhjsBasicRuntimeException("已有一个sqlsession与当前thread相关");
                }
                SqlSession session = MybatisDao.INSTANCE.getSession(Thread.currentThread());
                sqlThreadSessionMap.set(session);
                return session;
        }

        public static void closeSession()
        {
                try
                {

                        SqlSession session = getSqlSession();
                        MybatisDao.INSTANCE.closeSession(session, Thread.currentThread());
                        sqlThreadSessionMap.remove();
                } catch (Exception e)
                {
                        log.error("关闭sqlsession发生异常", e);
                }
        }
}
