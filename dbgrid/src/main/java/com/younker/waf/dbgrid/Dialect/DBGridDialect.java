package com.younker.waf.dbgrid.Dialect;

import com.younker.waf.dbgrid.DBGrid;

//添加借口用于实现sql串方言
public interface DBGridDialect
{
        // 用于返回分页语句
        /**
         * 适应分页后的Sql串
         * @param dbGrid
         * @return
         */
        public String getPaging(DBGrid dbGrid);
        
        /**
         * 由于前台传入的必须为字符串，某些数据库中不能直接将字符串和日期类型比较，因此需要添加函数
         * @param originParamValue
         * @return
         */
        public String generateDateParameter(String originParamValue );

}
