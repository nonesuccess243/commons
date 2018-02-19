package com.nbm.waf.boot;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.wayeasoft.starter.Starter;
import com.younker.waf.db.mybatis.MybatisDao;
import com.younker.waf.db.mybatis.MybatisDao.TransactionHelper;

public class DbBooterTest
{

        @Test
        public void test()
        {
                Starter.I.start();
                MybatisDao.INSTANCE.doTransact(new TransactionHelper() {

                        @Override
                        public void transact(SqlSession session)
                        {
                                DbBooter.I.boot();
                                
                        }});
        }

}
