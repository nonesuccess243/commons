package com.younker.waf.db.mybatis;

import java.sql.Connection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.StringUtil;

public class SqlSessionProvider
{
        private final static Logger log = LoggerFactory.getLogger(SqlSessionProvider.class);

        private static Map<Thread, SqlSession> sqlThreadSessionMap = new HashMap<>();
        
        private static Map<Thread, SqlSessionInfo> sqlSessionInfoMap = new HashMap<>();

        public static synchronized SqlSession getSqlSession()
        {
                if (sqlThreadSessionMap.get(Thread.currentThread()) == null)
                {
                        throw new RuntimeException("当前线程中无sqlsession");
                }

                return sqlThreadSessionMap.get(Thread.currentThread());

        }

        public static Connection getConncetion()
        {
                return SqlSessionProvider.getSqlSession().getConnection();
        }

        
        /**
         * 为了调试方便，传入开始session的uri
         * @param uri
         * @return
         */
        public static SqlSession openSession(String uri)
        {
                
                if (sqlThreadSessionMap.get(Thread.currentThread()) != null)
                {
                        return sqlThreadSessionMap.get(Thread.currentThread());
                        // throw new
                        // BhjsBasicRuntimeException("已有一个sqlsession与当前thread相关");
                }
                SqlSession session = MybatisDao.INSTANCE.getSession();
                sqlThreadSessionMap.put(Thread.currentThread(), session);
                
                SqlSessionInfo sqlSessionInfo = new SqlSessionInfo();
                sqlSessionInfo.setStartTime(new Date());
                sqlSessionInfo.setThread(Thread.currentThread());
                sqlSessionInfo.setUri(uri);
                
                sqlSessionInfoMap.put(Thread.currentThread(), sqlSessionInfo);
                
                debugSessionInfo("");
                return session;
        }
        
        public static SqlSession openSession()
        {
                return openSession("");
        }


        public static void closeSession()
        {
                
                SqlSessionInfo info = new SqlSessionInfo();//防止空指针
                
                try
                {
                        SqlSession session = getSqlSession();
                        MybatisDao.INSTANCE.closeSession(session);
                        sqlThreadSessionMap.remove(Thread.currentThread());
                        
                        info = sqlSessionInfoMap.remove(Thread.currentThread());
                } catch (Exception e)
                {
                        log.error("关闭sqlsession发生异常", e);
                }finally
                {
                        debugSessionInfo("关闭sqlsession[" + info.toString() + "]");
                }
        }
        
        private static void debugSessionInfo(String info)
        {
                if( log.isDebugEnabled())
                {
                try
                {
                        StringBuilder sb = new StringBuilder(
                                        "sqlsession信息\n" + info + "\n");
                        sb.append(StringUtil.getString(
                                        "thread sqlsession池中共有session{}个",sqlThreadSessionMap.size()))
                                        .append("\n");
                        for (Entry<Thread, SqlSession> entry : Collections
                                        .unmodifiableMap(sqlThreadSessionMap)
                                        .entrySet())
                        {
                                sb.append(StringUtil.getString(
                                                "线程id[{}], 线程状态[{}], 相关信息[{}]",
                                                entry.getKey().toString(),
                                                entry.getKey().getState(),
                                                sqlSessionInfoMap.get(entry.getKey()).toString()))
                                                .append("\n");
                                if (entry.getKey()
                                                .getState() == Thread.State.TERMINATED)
                                {
                                        sb.append("sqlsession泄露\n");
                                }
                        }


                        log.debug(sb.toString());
                } catch (Exception e)
                {
                        log.error("打印异常调试信息时出现异常", e);
                }
                }
        }
}
