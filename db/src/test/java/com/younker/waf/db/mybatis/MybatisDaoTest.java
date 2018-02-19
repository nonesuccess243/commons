package com.younker.waf.db.mybatis;

import org.junit.Test;
import com.younker.waf.db.DataSourceProvider;

public class MybatisDaoTest
{        
        @Test
        public void testInitAuto()
        {
                DataSourceProvider.initSimple();
                MybatisDao.INSTANCE.scanAndInit();
        }
        
}
