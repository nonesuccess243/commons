package com.younker.waf.db.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import com.younker.waf.db.DataSourceProvider;

public class MybatisDataSourceFactory extends UnpooledDataSourceFactory 
{
	@Override
        public DataSource getDataSource()
        {
	        return DataSourceProvider.instance().getDataSource();
        }
}
