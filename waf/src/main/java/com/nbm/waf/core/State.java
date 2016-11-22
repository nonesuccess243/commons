package com.nbm.waf.core;

/**
 * 由于每个Model类都会有状态问题，因此将通用的状态放在本类中。
 * 
 * 后代码架构工作没有坚持进行，导致本类的用法没有持续整理。现在本类是否还要继续使用尚不明朗，因此暂时标记为过时。
 */
@Deprecated
public class State
{
        /**
         * Model状态:正常
         */
        public static final String S_COMMON = "00";
        /**
         * Model状态:停用
         */
        public static final String S_STOP = "01";

        /**
         * Model状态：错误
         */
        public static final String S_ERROR = "98";
        /**
         * Model状态:删除
         */
        public static final String S_DELETE = "99";
}
