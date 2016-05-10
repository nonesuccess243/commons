/******************************************************************************
 * Title:     Younker Web Application Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.WebUtil;

/**
 * DBGridResultSetHandler
 * 
 * @see org.apache.commons.dbutils.ResultSetHandler
 */
public class DBGridResultSetHandler implements ResultSetHandler
{

        private final static Logger log = LoggerFactory.getLogger(DBGridResultSetHandler.class);

        private DBGrid dbgrid;
        private int currentRowNum;

        public DBGridResultSetHandler(DBGrid dbgrid)
        {
                this.dbgrid = dbgrid;
        }

        /**
         * format data list
         */
        public Object handle(ResultSet rs) throws SQLException
        {

                ArrayList<DBGridRow> list = new ArrayList<DBGridRow>();
                DBGridRow row;
                int column;
                DBGridItem gi, gti;
                // 不明原因的未使用变量，暂时注释 int iNumberFormat, iDateFormat;
                List<DBGridItem> gt = dbgrid.getGridTitles();
                ResultSetMetaData meta = rs.getMetaData();

                setColumnType(gt, meta);
                // page(rs);
                while (rs.next())
                {
                        column = meta.getColumnCount();
                        row = new DBGridRow(rs.getString(1), column);
                        for (int i = 1; i <= column && i <= gt.size(); i++)
                        // oracle分页必须多选一列rownumber出来,这就造成了column比dbgrid宽一列,所以此处加了个二重判断,下面的函数是相同的原因

                        {
                                gti = (DBGridItem) gt.get(i - 1);
                                gi = new DBGridItem();
                                gi.setStyle(gti.getStyle());
                                gi.setLabel(formateRS(rs, i));
                                row.addItem(gi);
                        }
                        list.add(row);
                }
                return list;
        }

        /**
         * 根据meta中的信息获取列在数据库中的类型，再根据此类型设置griditem的类型
         */
        private void setColumnType(List<DBGridItem> gt, ResultSetMetaData meta) throws SQLException
        {
                DBGridItem gti = (DBGridItem) gt.get(0);
                if (gti.getType() != null)
                        return;

                int column = meta.getColumnCount();
                for (int i = 1; i <= column && i <= gt.size(); i++)
                {
                        gti = (DBGridItem) gt.get(i - 1);
                        switch (meta.getColumnType(i))
                        {
                        case Types.NUMERIC:
                        case Types.DECIMAL:
                        case Types.FLOAT:
                        case Types.DOUBLE:
                        case Types.BIGINT:
                        case Types.INTEGER:
                        case Types.SMALLINT:
                                gti.setType(DBGridItem.NUMBER);
                                break;
                        case Types.CHAR:
                        case Types.LONGVARCHAR:
                        case Types.VARCHAR:
                                gti.setType(DBGridItem.STRING);
                                break;
                        case Types.DATE:
                        case Types.TIME:
                        case Types.TIMESTAMP:
                                gti.setType(DBGridItem.DATE);
                                break;
                        default:
                                gti.setType(DBGridItem.STRING);
                                break;
                        }
                }
        }

        private void page(ResultSet rs) throws SQLException
        {
                if (rs.last())
                {
                        dbgrid.setRecordNum(rs.getRow());
                }
                int start = (dbgrid.getCurrentPage() - 1) * dbgrid.getGridRowsPerPage() + 1;
                if (start == 1)
                {
                        rs.beforeFirst();
                } else
                {
                        rs.absolute(start - 1);
                }
                currentRowNum = 1;
        }

        private boolean next(ResultSet rs) throws SQLException
        {
                boolean flag;
                if (dbgrid.getGridRowsPerPage() <= 0)
                {
                        flag = rs.next();
                        return flag;
                }
                if (currentRowNum <= dbgrid.getGridRowsPerPage())
                {
                        flag = rs.next();
                        currentRowNum++;
                } else
                {
                        flag = false;
                }
                return flag;
        }

        private String formateRS(ResultSet rs, int i) throws SQLException
        {

                int iNumberFormat;
                int iDateFormat;
                String[] colsFormat = dbgrid.getColFormat();
                String val = rs.getString(i);

                iNumberFormat = DBGridUtil.getNumberFormat(colsFormat[i - 1]);
                iDateFormat = DBGridUtil.getDateFormat(colsFormat[i - 1]);

                if (iNumberFormat > -1 && !"".equals(val))
                {
                        WebUtil.setComma(WebUtil.getBigDecimalString(val, iNumberFormat));
                } else if (iDateFormat > 0)
                {
                        if (iDateFormat == 1)
                        {
                                if (rs.getDate(i) != null)
                                        val = String.valueOf(rs.getDate(i));
                                else
                                        val = DBGridItem.LABEL_NULL;
                        } else if (iDateFormat == 2)
                        {
                                if (rs.getTimestamp(i) != null)
                                        val = String.valueOf(rs.getTimestamp(i));
                                else
                                        val = DBGridItem.LABEL_NULL;
                        }
                }
                return val;
        }
}
