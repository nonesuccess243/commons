/***********************************************
 * Title:       ModelState.java
 * Description: ModelState.java
 * Create Date: 2013-5-22
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.waf.core.modeldriven;

/**
 *
 */
public interface ModelConst
{
        
        public long ID_TEST = -1l;
        public long ID_NULL = 0l;

        public long ID_ERROR = 1l;
        
        /**
         * 小于该值的为系统保留字，实际数据不允许使用
         */
        public long ID_RESERVED = 10000l;
        
        public long ID_TEST_MIN = 1000l;
        
        /**
         * 表示该条记录不存在
         */
        public long ID_NOT_EXIST = 2l;

        public String STR_NULL = "不存在";

        public String STR_ERROR = "错误";

}
