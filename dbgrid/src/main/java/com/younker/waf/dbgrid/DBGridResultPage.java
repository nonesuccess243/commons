package com.younker.waf.dbgrid;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBGridResultPage implements ResultSetHandler
{
        private final static Logger log = LoggerFactory.getLogger(DBGridResultPage.class);

        /**
         * 用于获得数据行数
         */
        @Override
        public Object handle(ResultSet rs) throws SQLException
        {

                if (rs == null)
                {
                        log.info("数据行数Result为空");
                        return 0;
                }
                if (!rs.next())
                {
                        log.info("数据行数Result为空");
                        return 0;
                }

                return rs.getInt(1);
        }

}
