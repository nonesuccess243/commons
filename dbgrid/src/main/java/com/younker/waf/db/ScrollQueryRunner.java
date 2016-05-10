/******************************************************************************
 * Title:     Younker Web Application Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:    Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

/**
 * 2008/03/24 对应MS SQL Server，追加对滚动结果集的支持
 * 
 * @author xiaojian
 * 
 *         不知道哪个地方在用，标记为过时。
 *         后期对数据库的操作，预计会慢慢转移到支持直接返回Java容器类的数据库工具上，直接操作底层jdbc对象的情况会越来越少。
 * 
 */
@Deprecated
public class ScrollQueryRunner extends QueryRunner
{

        public ScrollQueryRunner(DataSource ds)
        {
                super(ds);
                // setDataSource(ds);
                // 倪玉哲2011.12.27修改：更新dbutil版本为1.4，QueryRunner类的setDataSource函数取消，改为通过构造函数传入ds
        }

        protected PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException
        {
                return conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
}
