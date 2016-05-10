/***********************************************
 * Title:       ModelProxy.java
 * Description: ModelProxy.java
 * Create Date: 2013-5-22
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.waf.core.modeldriven;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;

import com.younker.waf.db.mybatis.SqlSessionProvider;

/**
 * Proxy层职责：
 * <ul>
 * <li>各有关常量</li>
 * <li>读数据时封装外键关系
 * <li>
 * <li>除全表插改操作外，封装有业务含义的单表操作</li>
 * </ul>
 */
public abstract class ModelProxy<T> implements ModelConst, Serializable
{
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        protected T model;

        public ModelProxy(T model)
        {
                this.model = model;
        }

        /**
         * 在持久化层面上是否存在。当id大于保留值时，视作存在。
         * 
         * @param id
         * @return
         */
        public static boolean exist(Long id)
        {
                if (id == null || id<=ID_RESERVED)
                {
                        return false;
                }
                return true;
        }

        protected static SqlSession getSqlSession()
        {
                return SqlSessionProvider.getSqlSession();
        }
        
}
