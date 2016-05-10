/***********************************************
 * Title:       ReturnValue.java
 * Description: ReturnValue.java
 * Create Date: 2011-6-21
 * CopyRight:   CopyRight(c)@2011
 * Company:     NBM
 ***********************************************
 */
package com.nbm.waf.core;

/**
 *
 */
public interface ReturnValue
{

        public static final String RET_CODE = "ret_code";

        /**
         * 无返回信息
         */
        public static final String RC_NONE = "none";
        /**
         * 操作成功
         */
        public static final String RC_SUCCESS = "success";
        /**
         * 操作失败
         */
        public static final String RC_FAILURE = "fail";

        /**
         * 无相应数据
         */
        public static final String RC_NOELEMENT = "3";

}
