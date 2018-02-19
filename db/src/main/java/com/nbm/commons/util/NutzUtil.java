package com.nbm.commons.util;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import com.nbm.exception.NbmBaseRuntimeException;
import com.younker.waf.db.mybatis.SqlSessionProvider;

public enum NutzUtil
{
        DAO;
        private static DataSource ds = null;
        private static Dao dao = null;
        int i;

        private NutzUtil()
        {

        };

        private void setDataSource() throws Exception
        {
                ds = new DataSource()
                {

                        @Override
                        public PrintWriter getLogWriter() throws SQLException
                        {
                                throw new NbmBaseRuntimeException("调用datasource函数getLogWriter");
                        }

                        @Override
                        public void setLogWriter(PrintWriter out) throws SQLException
                        {
                                throw new NbmBaseRuntimeException("调用datasource函数setLogWriter");

                        }

                        @Override
                        public void setLoginTimeout(int seconds) throws SQLException
                        {
                                throw new NbmBaseRuntimeException("调用datasource函数setLoginTimeout");

                        }

                        @Override
                        public int getLoginTimeout() throws SQLException
                        {
                                throw new NbmBaseRuntimeException("调用datasource函数getLoginTimeout");
                        }

                        @Override
                        public java.util.logging.Logger getParentLogger()
                                        throws SQLFeatureNotSupportedException
                        {
                                throw new NbmBaseRuntimeException("调用datasource函数getParentLogger");
                        }

                        @Override
                        public <T> T unwrap(Class<T> iface) throws SQLException
                        {
                                throw new NbmBaseRuntimeException("调用datasource函数unwrap");
                        }

                        @Override
                        public boolean isWrapperFor(Class<?> iface) throws SQLException
                        {
                                throw new NbmBaseRuntimeException("调用datasource函数isWrapperFor");
                        }

                        @Override
                        public Connection getConnection() throws SQLException
                        {
                                return SqlSessionProvider.getConncetion();
                        }

                        @Override
                        public Connection getConnection(String username, String password)
                                        throws SQLException
                        {
                                throw new NbmBaseRuntimeException(
                                                "调用datasource函数，带参数的getConnection");
                        }
                };

        };

        public Dao getDao() throws Exception
        {

                setDataSource();
                if (null == dao)
                {
                        dao = new NutDao(ds);
                }

                return dao;
        }

}
