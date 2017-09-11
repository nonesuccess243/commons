/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author:        Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;
import com.nbm.waf.core.modeldriven.ModelUtils;
import com.younker.waf.dbgrid.Dialect.DBGridDialect;
import com.younker.waf.dbgrid.Dialect.MySql;
import com.younker.waf.dbgrid.Dialect.Oracle;

/**
 * DBGrids.java
 */
public class DBGrids implements Serializable
{
        private Logger log = LoggerFactory.getLogger(DBGrids.class);

        /**
         * 
         */
        private static final long serialVersionUID = 20574212171955703L;
        private Map<String, DBGrid> dbGrids;

        public DBGrids()
        {
                dbGrids = new HashMap<String, DBGrid>();
        }

        public void addDBGrid(DBGrid dbgrid)
        {
                String gridName = dbgrid.getGridName();
                if (dbGrids.containsKey(gridName))
                {
                        log.error("duplicate dbgrid name[name=" + gridName + "].");
                        return;
                } else
                {
                        // -----------------------------2013-4-6修改添加继承关系------------------------
                        getMyParent(dbgrid);
                        // -----------------------------2013-4-6修改添加继承关系------------------------
                        dbGrids.put(gridName, dbgrid);
                }
        }

        
        /**
         * 返回一个克隆dbgrid对象
         * @param gridName
         * @return
         */
        public DBGrid getDBGrid(Object gridName)
        {
                if (dbGrids.containsKey(gridName))
                {
                        DBGrid grid = dbGrids.get(gridName);

                        DBGrid newGrid = new DBGrid();

                        ModelUtils.INSTANCE.copyProperties(grid, newGrid, new String[]
                        { "forwardURL", "gridType", "gridHandler", "gridRowsPerPage",
                                        "gridGroupBy", "gridOrderBy", "gridColor", "gridTest",
                                        "gridQueryType", "gridOperation", "gridParam",
                                        "gridCondCols", "gridWhere", "gridParent", "gridRelation",
                                        "gridFields", "gridLabel", "gridCols", "gridTable",
                                        "gridTitle", "gridName", "gridDecorate" });

                        return newGrid;
                } else
                        throw new NbmBaseRuntimeException("找不到dbgrid[" + gridName + "]").set(
                                        "currentDbgrids", dbGrids);
       }

        /**
         * 返回所有dbgrid实例。这个返回值是只读的，不能修改
         * @return
         */
       public Collection<DBGrid> getAllDBGrids()
       {
               return Collections.unmodifiableCollection(dbGrids.values());
       }

        /**
         * 配置文件中读取到import元素时的处理函数
         */
        public void handleImport(String filePath)
        {
                DBGrids _dbgrid = DBGridGenerator.getInstance().getDBGrids(filePath);
                if (_dbgrid != null)
                        dbGrids.putAll(_dbgrid.dbGrids);
        }

        /**
         * --------------------------------修改2013-4-6添加继承关系--------------------
         * ------
         */
        public void getMyParent(DBGrid dbgrid)
        {
                if (dbgrid.getGridParent() != null)
                {
                        if (dbGrids.containsKey(dbgrid.getGridParent()))
                        {
                                // 将数据按照父类补全
                                dbgrid.fillAttr(dbGrids.get(dbgrid.getGridParent()));
                                log.info("add child with :" + dbgrid.getGridName());
                        } else
                        {
                                log.error("child`s parent isn`t exist with:" + dbgrid.getGridName());
                                return;
                        }
                } else
                {
                        // log.info("data is null!");
                }
        }

        // ------------------------------------------------------------------------------------

        /**
         * 添加生成时添加DB类型方法 2013-4-22 何
         */
        public void setAllDBType(String DBType)
        {
                // 根据类名生成
                DBGridDialect dbD;
                log.info("添加DB方言");
                if (DBType.toUpperCase().equalsIgnoreCase("MYSQL"))
                {
                        dbD = new MySql();
                        log.info("DBType:MYSQL");
                } else if (DBType.toUpperCase().equalsIgnoreCase("Oracle"))
                {
                        dbD = new Oracle();

                        log.info("DBType:ORACLE");
                } else
                {
                        // 其他生成方式,由DBType提供class路径
                        try
                        {
                                dbD = (DBGridDialect) Class.forName(DBType).newInstance();
                                log.info("[DBType]:" + DBType + " [Class by]:" + DBType);
                        } catch (Exception e)
                        {
                                e.printStackTrace();
                                return;
                        }
                }
                DBGrid.setDbD(dbD);
        }

        public String toString()
        {
                StringBuffer sb = new StringBuffer();
                Object[] s = dbGrids.values().toArray();
                for (int i = 0; i < s.length; i++)
                {
                        sb.append(s[i]);
                }
                return sb.toString();
        }
}
