package com.nbm.waf.core;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseException;
import com.opensymphony.xwork2.ActionSupport;
import com.younker.waf.db.DataSourceProvider;

public class BasicAction extends ActionSupport implements  ReturnValue
{

        private final static Logger log = LoggerFactory.getLogger(BasicAction.class);
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private String returnValue;
        
        private SqlSession sqlSession;
        
        /*
         * (non-Javadoc)
         * 
         * @see com.opensymphony.xwork2.Action#execute()
         */
        @Override
        public String execute() throws Exception
        {
                throw new NbmBaseException("调用了抽象基类的execute方法").set("调用类", this.getClass());
        }
        
        protected void runSqlByPath( String path ) throws SQLException, IOException
        {
                String sql = IOUtils.toString(
                                ServletActionContext
                                .getServletContext()
                                .getResourceAsStream(path));
                String[] ss = sql.split(";");
                
                Statement statement = DataSourceProvider.instance().getDataSource().getConnection().createStatement();
                
                for(String s : ss )
                {
                        log.debug(s);
                        
                        if( s == null || s.trim().equals("") || s.trim().equals(";" ) )
                                continue;
                        
                        statement.execute(s);
                }
                statement.executeBatch();
                
                statement.getConnection().commit();
                statement.getConnection().close();
        }
        
        /**
         * @return the returnValue
         */
        public String getReturnValue()
        {
                return returnValue;
        }

        /**
         * @param returnValue
         *                the returnValue to set
         */
        protected void setReturnValue(String returnValue)
        {
                this.returnValue = returnValue;
        }

        public SqlSession getSqlSession()
        {
                return sqlSession;
        }

        public void setSqlSession(SqlSession sqlSession)
        {
                this.sqlSession = sqlSession;
        }

}
