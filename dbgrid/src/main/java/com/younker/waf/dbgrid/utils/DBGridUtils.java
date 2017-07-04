/***********************************************
 * Title:       DBGridUtils.java
 * Description: DBGridUtils.java
 * Create Date: 2013-9-25
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.younker.waf.dbgrid.utils;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.nbm.exception.NbmBaseRuntimeException;
import com.opensymphony.xwork2.ActionContext;
import com.younker.waf.db.mybatis.SqlSessionProvider;
import com.younker.waf.dbgrid.DBGrid;
import com.younker.waf.dbgrid.DBGridEngine;
import com.younker.waf.dbgrid.DBGridParser;

/**
 *
 */
public enum DBGridUtils
{
        INSTANCE;
        
        public int getCount( String dbgridName )
        {
                DBGrid dbgrid = getParsedDbgrid(dbgridName);
                if( ActionContext.getContext() != null)
                {
                        return dbgrid.getCount(SqlSessionProvider.getSqlSession().getConnection());
                }
                return dbgrid.getCount();
        }
        
        public DBGrid getParsedDbgrid(String dbgridName)
        {
                DBGrid dbgrid = DBGridEngine.getDefaultInstance().getDBGrids().getDBGrid(dbgridName);
                new DBGridParser().parse(dbgrid, ServletActionContext.getRequest());
                if( dbgrid == null )
                {
                        throw new NbmBaseRuntimeException("找不到dbgrid").set("dbgridName", dbgridName);
                }
                return dbgrid;
        }
        
        
        /**
         * 根据url解析dbgrid名称。
         * 
         * 规则为：从第一个字符一直匹配到最后一个.
         * 
         * 如：
         * 
         * 请求范围：/a/b/c/d.jsonlist
         * 
         * 则将dbgrid解析为/a/b/c/d
         * 
         * @param url
         * @return
         */
        public String getDbgridName(String url)
        {
                String gridName = url.substring(0, url.lastIndexOf("."));
                return gridName;
        }
}
