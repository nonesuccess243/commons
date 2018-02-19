package com.wayeasoft.starter;

import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.db.mybatis.MybatisDao;

public enum Starter
{
        I;
        
        public void start()
        {
                DataSourceProvider.initSimple();
                MybatisDao.INSTANCE.scanAndInit();
        }
        

}
