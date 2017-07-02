/******************************************************************************
 * Title:     Younker Web Application Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import static com.younker.waf.dbgrid.DBGridToken.ESCAPE_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.GROUPBY;
import static com.younker.waf.dbgrid.DBGridToken.ORDERBY;
import static com.younker.waf.dbgrid.DBGridToken.STR_BLANK;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;
import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.db.ScrollQueryRunner;
import com.younker.waf.dbgrid.Dialect.DBGridDialect;
import com.younker.waf.utils.WebUtil;

/**
 * DBGrid,对应配置文件的<DBGrid>标签
 */
public class DBGrid implements Serializable
{

        /**
         * 
         */
        private static final long serialVersionUID = -2113755911644531078L;

        private String gridName = null;
        private String gridTitle = null;
        private String gridTable = null;
        private String gridCols = null;
        private String gridLabel = null;
        private String gridFields = null;
        private String gridRelation = null;
        private String gridParent = null;
        private String gridWhere = null;
        private String gridCondCols = null;
        private String gridParam = null;
        private String gridOperation = null;
        private String gridQueryType = "and";
        // ----------------------修改添加方言借口支持 2013-4-16----------------------
        private static DBGridDialect dbDialect = null;
        // ------------------------------------------------------------------------

        private String gridFzC = null;

        private String gridTest = null;
        private String gridColor = null;

        /**
         * 2009.02.14(情人节) 肖：in order to reuse the result of DBGrid Component,
         * the result may not be forward to directly the JSP for display, but
         * can be forward to assigned URL(servlet etc.) for processing.
         */
        private String forwardURL = null;

        private String gridOrderBy = null;
        private String gridGroupBy = null;
        private int gridRowsPerPage;
        private int currentPage;
        private int pages;
        private int recordNum;
        private String gridHandler = null;

        private String strWhereSQL = null;
        private String strSQL = null;
        private String colFormat[] = null;
        private ArrayList<DBGridItem> titles = null;
        private boolean changeflag = true;
        private boolean reparse;
        private int gridType;
        
        private DBGridDecorate gridDecorate = DBGridDecorate.DEFAULT_INSTANCE;

        private final static Logger log = LoggerFactory.getLogger(DBGrid.class);

        public DBGrid()
        {
        }

        public DBGrid(String gridName)
        {
                this.gridName = gridName;
        }

        public int[] extractOperation()
        {
                String tmps[] = WebUtil.split(getGridOperation(), DBGridToken.SEPARATOR);
                int[] operations = new int[tmps.length];
                for (int i = 0; i < tmps.length; i++)
                {
                        operations[i] = WebUtil.getIntValue(tmps[i]);
                }
                return operations;
        }

        public String[] extractParam()
        {
                if (gridParam == null)
                        return new String[0];
                return WebUtil.split(getGridParam(), DBGridToken.SEPARATOR);
        }

        public String[] extractCondCols()
        {
                if (gridCondCols == null)
                        return new String[0];
                String[] cols = WebUtil.split(getGridCondCols(), DBGridToken.SEPARATOR);
                for (int i = 0; i < cols.length; i++)
                {
                        cols[i] = WebUtil.replace(cols[i], ESCAPE_TOKEN, DBGridToken.SEPARATOR);
                }
                return cols;
        }

        /**
         * @return the SQL for query database
         */
        public String generateSQL()
        {
                if (dbDialect == null)
                {
                        log.error("方言为空");
                }
                /*StringBuffer tsb = new StringBuffer();
                tsb.append("select \n").append(getGridCols()).append("\n from \n")
                                .append(getGridTable()).append("\n").append(getWhereSQL())
                                .append("\n").append(getGridGroupBy()).append("\n")
                                .append(getGridOrderByWithOrderBy()).toString();
                String result = (strSQL = dbDialect.getPaging(tsb.toString(), gridRowsPerPage,
                                currentPage));*/
                String result = (strSQL = dbDialect.getPaging(this));
//                log.debug("generateSql: " + result);
                return result;
        }

        public String getSQLWithoutPage()
        {
                StringBuffer tsb = new StringBuffer();
                tsb.append("select \n").append(getGridCols()).append("\n from \n")
                                .append(getGridTable()).append("\n").append(getWhereSQL())
                                .append("\n").append(getGridGroupByWithGroupBy()).append("\n")
                                .append(getGridOrderByWithOrderBy()).toString();

                return tsb.toString();
        }

        /**
         * 生成获得总行数sql
         * 
         * @return
         */
        public String generateCountSql()
        {
                StringBuffer tsb = new StringBuffer();
                tsb.append("select count(*) from ").append(getGridTable()).append("\n")
                                .append(getWhereSQL()).append("\n").append(getGridGroupByWithGroupBy())
                                .append("\n");//倪玉哲 20170701修改，去掉orderby子句，在count查询中，排序无意义
                log.debug("count data:" + tsb.toString());
                return tsb.toString();
        }

        public String getGridFzC()
        {
                return gridFzC;
        }

        public void setGridFzC(String gridFzC)
        {
                this.gridFzC = gridFzC;
        }

        public String getGridColor()
        {
                return gridColor;
        }

        public String getGridCols()
        {
                return gridCols;
        }

        public String getGridCondCols()
        {
                return WebUtil.nvl(gridCondCols);
        }

        public String getGridGroupByWithGroupBy()
        {
                if (gridGroupBy != null && gridGroupBy.trim().length() != 0)
                        return GROUPBY + gridGroupBy;
                else
                        return STR_BLANK;
        }

        public String getGridHandler()
        {
                return gridHandler;
        }

        public String getGridLabel()
        {
                return gridLabel;
        }

        /**
         * gridLabels may contain display styles,this method separates styles of
         * display from the contents of display.
         * 
         * @return list of GridItem instances representing grid table title.
         */
        public List<DBGridItem> getGridTitles()
        {
                if (titles != null)
                        return titles;

                String[] labels;
                if (gridLabel.length() == 0)// 如果xml中未配置labels，则使用gridCols作为列名
                {
                        labels = WebUtil.split(gridCols, DBGridToken.SEPARATOR);
                } else
                {
                        labels = WebUtil.split(gridLabel, DBGridToken.SEPARATOR);
                }

                WebUtil.trimStrArray(labels);

                String[] fields;
                if (gridFields != null && gridFields.length() != 0)
                {
                        fields = WebUtil.split(gridFields, DBGridToken.SEPARATOR);
                } else
                {
                        fields = WebUtil.split(gridCols, DBGridToken.SEPARATOR);
                }
                WebUtil.trimStrArray(fields);

                colFormat = new String[labels.length];
                titles = new ArrayList<DBGridItem>(labels.length);
                String[] cols = getGridColumns();

                for (int i = 0; i < labels.length; i++)
                {
                        DBGridItem gt = new DBGridItem();

                        colFormat[i] = DBGridUtil.getDataFormat(labels[i]);
                        gt.setFormat(colFormat[i]);
                        gt.setLabel(DBGridUtil.getLabel(labels[i]));
                        if (i < fields.length)
                        {
                                gt.setField(WebUtil.split(fields[i], DBGridToken.FORMAT_TOKEN)[0]);
                                if (this.gridFzC != null
                                                && this.gridFzC.toLowerCase()
                                                                .indexOf(WebUtil.split(
                                                                                fields[i],
                                                                                DBGridToken.FORMAT_TOKEN)[0]
                                                                                .toLowerCase()) >= 0)
                                {
                                        gt.setFrozen(true);
                                }
                        }

                        if (i < cols.length)
                                gt.setColumn(cols[i]);
                        if (colFormat[i] != null && colFormat[i].equalsIgnoreCase("h"))
                        {
                                gt.setStyle(DBGridItem.STYLE_HIDE);
                        } else
                                gt.setStyle(DBGridItem.STYLE_NONE);
                        titles.add(gt);
                }
                return titles;
        }

        /**
         * ------------------------------修改 设置父对象名称-------------------------
         * 
         * @return
         */
        public String getGridParent()
        {
                return gridParent;
        }

        public void setGridParent(String gridParent)
        {
                if (gridParent == null)
                {
                        this.gridParent = null;
                        return;
                }
                // 两个类型名相同不做操作
                if (gridParent.equals(gridName))
                        return;
                this.gridParent = gridParent;
        }

        // 用父类填充本地空数据
        public DBGrid fillAttr(DBGrid dbGrid)
        {

                if (gridTitle == null)
                {
                        gridTitle = dbGrid.getGridTitle();
                }
                if (gridTable == null)
                {
                        gridTable = dbGrid.getGridTable();
                }
                if (gridCols == null)
                {
                        gridCols = dbGrid.getGridCols();
                }
                if (gridLabel == null)
                {
                        gridLabel = dbGrid.getGridLabel();
                }
                if (gridFields == null)
                {
                        gridFields = dbGrid.getGridFields();
                }
                if (gridRelation == null)
                {
                        gridRelation = dbGrid.getGridRelation();
                }
                if (gridParent == null)
                {
                        gridParent = dbGrid.getGridParent();
                }
                if (gridWhere == null)
                {
                        gridWhere = dbGrid.getGridWhere();
                }
                if (gridGroupBy == null)
                {
                        gridGroupBy = dbGrid.gridGroupBy;
                }
                if (gridOrderBy == null)
                {
                        gridOrderBy = dbGrid.getGridOrderByWithOrderBy();
                }
                if (forwardURL == null)
                {
                        forwardURL = dbGrid.getForwardURL();
                }
                if (gridColor == null)
                {
                        gridColor = dbGrid.getGridColor();
                }
                if (gridTest == null)
                {
                        gridTest = dbGrid.getGridTest();
                }
                if (gridQueryType.equals("and"))
                {
                        gridQueryType = dbGrid.getGridQueryType();
                }
                if (gridOperation == null)
                {
                        gridOperation = dbGrid.getGridOperation();
                }
                if (gridParam == null)
                {
                        gridParam = dbGrid.getGridParam();
                }
                if (gridCondCols == null)
                {
                        gridCondCols = dbGrid.getGridCondCols();
                }
                if (gridHandler == null)
                {
                        gridHandler = dbGrid.getGridHandler();
                }
                if (strWhereSQL == null)
                {
                        strWhereSQL = dbGrid.getWhereSQL();
                }
                if (colFormat == null)
                {
                        colFormat = dbGrid.getColFormat();
                }
                if (titles == null)
                {
                        titles = dbGrid.titles;
                }

                if (gridRowsPerPage == 0)
                {
                        gridRowsPerPage = dbGrid.getGridRowsPerPage();
                }

                if (currentPage == 0)
                {
                        currentPage = dbGrid.getCurrentPage();
                }
                if (pages == 0)
                {
                        pages = dbGrid.getPages();
                }
                if (recordNum == 0)
                {
                        recordNum = dbGrid.getRecordNum();
                }

                if (!changeflag)
                {
                        changeflag = true;
                }

                if (!reparse)
                {
                        reparse = dbGrid.reparse;
                }
                if (gridType == 0)
                {
                        gridType = dbGrid.getGridType();
                }
                if (gridFzC == null)
                {
                        gridFzC = dbGrid.getGridFzC();
                }
                // log.info(this.gridLabel+this.gridFields+this.gridCols);
                return this;
        }

        // --------------------------------------------------------------
        /**
         * 将gridCols使用SEPARATOR分割为字符串数组
         * 
         * @return
         */
        public String[] getGridColumns()
        {
                if (gridCols == null || "".equals(gridCols))
                        return null;
                else
                        return WebUtil.split(gridCols, DBGridToken.SEPARATOR);
        }

        public String getGridName()
        {
                return gridName;
        }

        public String getGridOperation()
        {
                return WebUtil.nvl(gridOperation);
        }

        public String getGridOrderByWithOrderBy()
        {
                if (gridOrderBy != null && gridOrderBy.trim().length() != 0)
                        return ORDERBY + gridOrderBy;
                else
                        return STR_BLANK;
        }

        public String getGridOrderBy()
        {
                return gridOrderBy;
        }

        public String getGridParam()
        {
                return WebUtil.nvl(gridParam);
        }

        public String getGridRelation()
        {
                return WebUtil.nvl(gridRelation);
        }

        public int getGridRowsPerPage()
        {
                return gridRowsPerPage;
        }

        public String getGridTable()
        {
                return gridTable;
        }

        public String getGridTest()
        {
                return gridTest;
        }

        public String getGridWhere()
        {
                return WebUtil.nvl(gridWhere);
        }

        public String getGridQueryType()
        {
                return gridQueryType;
        }

        public void setGridColor(String s)
        {
                gridColor = s;
        }

        public void setGridCols(String s)
        {
                gridCols = s;
        }

        public void setGridCondCols(String s)
        {
                gridCondCols = s;
        }

        public void setGridGroupBy(String s)
        {
                gridGroupBy = s;
        }
        
        public String getGridGroupBy()
        {
                return gridGroupBy;
        }

        public void setGridHandler(String s)
        {
                gridHandler = s;
        }

        public void setGridLabel(String s)
        {
                gridLabel = s;
        }

        public void setGridName(String s)
        {
                gridName = s;
        }

        public void setGridOperation(String s)
        {
                gridOperation = s;
        }

        public void setGridOrderBy(String s)
        {
                if (!getGridOrderByWithOrderBy().equals(s))
                {
                        gridOrderBy = s;
                        changeflag = true;
                }
        }

        public void setGridParam(String s)
        {
                gridParam = s;
        }

        public void setGridRelation(String s)
        {
                gridRelation = s;
        }

        public void setGridRowsPerPage(int i)
        {
                log.debug("行数:" + i);
                gridRowsPerPage = i;
        }

        public void setGridRowsPerPage(String i)
        {
                gridRowsPerPage = WebUtil.getIntValue(i);
        }

        public void setGridTable(String s)
        {
                gridTable = s;
        }

        public void setGridTest(String s)
        {
                gridTest = s;
        }

        public void setGridWhere(String s)
        {
                gridWhere = s;
        }

        public void setGridQueryType(String s)
        {
                if (!getGridQueryType().equals(s))
                {
                        gridQueryType = s;
                        reparse = true;
                }
        }

        public void setWhereSQL(String whereSQL)
        {
                this.strWhereSQL = whereSQL;
        }

        public String getWhereSQL()
        {
                return this.strWhereSQL;
        }

        public String getSQL()
        {
                if (changeflag == true)
                {
                        changeflag = false;
                        return generateSQL();
                } else
                        return this.strSQL;
        }

        public String[] getColFormat()
        {
                return colFormat;
        }

        /**
         * @return the number of current page
         */
        public int getCurrentPage()
        {
                return currentPage;
        }

        /**
         * @return the number of total pages
         */
        public int getPages()
        {

                int recNum = getRecordNum();
                int rowsPerPage = getGridRowsPerPage();
                if ((rowsPerPage == 0) || (recNum == 0))
                {
                        return 1;
                }
                if (recNum % rowsPerPage == 0)
                {
                        return recNum / rowsPerPage;
                } else
                {
                        return recNum / rowsPerPage + 1;
                }
        }

        public void setCurrentPage(int i)
        {
                currentPage = i;
        }

        public void setPages(int i)
        {
                pages = i;
        }

        /**
         * @return the number of all records
         */
        public int getRecordNum()
        {
                return recordNum;
        }

        public void setRecordNum(int i)
        {
                recordNum = i;
        }

        /**
         * @return the number of the first record in current page
         */
        public int getStartRecordNum()
        {
                return getGridRowsPerPage() * (getCurrentPage() - 1) + 1;
        }

        public int getGridType()
        {
                return gridType;
        }

        public void setGridType(int i)
        {
                gridType = i;
        }

        public void setGridType(String s)
        {
                gridType = WebUtil.getIntValue(s);
        }

        public boolean isReparse()
        {
                return reparse;
        }

        void isReparse(boolean b)
        {
                reparse = false;
        }

        public String getGridTitle()
        {
                return gridTitle;
        }

        public void setGridTitle(String gridTitle)
        {
                this.gridTitle = gridTitle;
        }

        public String getForwardURL()
        {
                return forwardURL;
        }

        public void setForwardURL(String forwardURL)
        {
                this.forwardURL = forwardURL;
        }

        public String toString()
        {
                StringBuffer sb = new StringBuffer();
                return sb.append("DBGrid[").append("gridName=").append(gridName)
                                .append(": gridTable=").append(gridTable).append(": gridCols=")
                                .append(gridCols).append("]").toString();
        }

        public String getGridFields()
        {
                return gridFields;
        }

        public void setGridFields(String gridFields)
        {
                this.gridFields = gridFields;
        }

        public static DBGridDialect getDbD()
        {
                return dbDialect;
        }

        public static void setDbD(DBGridDialect dd)
        {
                dbDialect = dd;
        }
        
        public DBGridDecorate getGridDecorate()
        {
                return gridDecorate;
        }

        public void setGridDecorate(String gridDecorate) throws InstantiationException, IllegalAccessException, ClassNotFoundException
        {
                this.gridDecorate = (DBGridDecorate) Class.forName(gridDecorate).newInstance();
        }

        public List<DBGridRow> getData()
        {
                long startTime = System.currentTimeMillis();
                
                ResultSetHandler h = new DBGridResultSetHandler(this);
                ResultSetHandler ph = new DBGridResultPage();
                QueryRunner run = new ScrollQueryRunner(DataSourceProvider.instance()
                                .getDataSource());

                Object result = null;
                try
                {
                        int count = Integer.parseInt(run.query(this.generateCountSql(), ph)
                                        .toString());
                        
                        long countTime = System.currentTimeMillis()-startTime;
                        this.setRecordNum(count);
                        log.debug("数据行数:" + count);
                        // -------------------添加对应数据库语句匹配
                        // 2013-4-15---------------------
                        result = run.query(this.getSQL(), h);
                        // ---------------------------------------------------------------------
                        
                        long queryTime = System.currentTimeMillis()-startTime-countTime;
                        
                        log.debug("dbgrid[countTime={}, queryTime={}]", countTime, queryTime);
                        
                } catch (SQLException e)
                {
                        // --------------------------2013-4-15----------------------
                        log.error(this.getGridName() + " SQL : \n" + this.getSQL());
                        // ---------------------------------------------------------
                        log.error("Failed to generate dbgrid : " + this.getGridName(), e);
                }
                
                List<DBGridRow> datas =  (List<DBGridRow>) result;

                //执行装饰逻辑
                getGridDecorate().handleData(datas);
                return datas;
        }

        public List<DBGridRow> getAllData()
        {
                ResultSetHandler<?> h = new DBGridResultSetHandler(this);
                ResultSetHandler ph = new DBGridResultPage();
                QueryRunner run = new ScrollQueryRunner(DataSourceProvider.instance()
                                .getDataSource());

                Object result = null;
                try
                {
                        int count = Integer.parseInt(run.query(this.generateCountSql(), ph)
                                        .toString());
                        this.setRecordNum(count);
                        log.debug("数据行数:" + count);
                        // -------------------添加对应数据库语句匹配
                        // 2013-4-15---------------------

                        log.debug(this.getSQLWithoutPage());
                        result = run.query(this.getSQLWithoutPage(), h);
                        // ---------------------------------------------------------------------
                } catch (SQLException e)
                {
                        // --------------------------2013-4-15----------------------
                        log.error(this.getGridName() + " SQL : \n" + this.getSQL());
                        // ---------------------------------------------------------
                        log.error("Failed to generate dbgrid : " + this.getGridName(), e);
                }
                
                List<DBGridRow> datas =  (List<DBGridRow>) result;
                getGridDecorate().handleData(datas);
                return datas;
        }

        public int getCount()
        {
                QueryRunner run = new ScrollQueryRunner(DataSourceProvider.instance()
                                .getDataSource());
                ResultSetHandler ph = new DBGridResultPage();
                int count = 0;
                try
                {

                        count = Integer.parseInt(run.query(this.generateCountSql(), ph).toString());
                } catch (SQLException e)
                {
                        // --------------------------2013-4-15----------------------
                        log.error(this.getGridName() + " SQL : \n" + this.generateCountSql());
                        // ---------------------------------------------------------
                        log.error("Failed to generate dbgrid count: " + this.getGridName(), e);
                }

                return count;
        }
        
        public int getCount( Connection connection )
        {
                QueryRunner run = new ScrollQueryRunner(DataSourceProvider.instance()
                                .getDataSource());
                ResultSetHandler ph = new DBGridResultPage();
                int count = 0;
                try
                {

                        count = Integer.parseInt(run.query(connection, this.generateCountSql(), ph).toString());
                } catch (SQLException e)
                {
                        // --------------------------2013-4-15----------------------
                        log.error(this.getGridName() + " SQL : \n" + this.generateCountSql());
                        // ---------------------------------------------------------
                        log.error("Failed to generate dbgrid count: " + this.getGridName(), e);
                }

                return count;
        }

        public void generateExcel(OutputStream os)
        {
                HSSFWorkbook wb = new HSSFWorkbook();

                HSSFSheet sheet = wb.createSheet(getGridTitle());

                int rowIndex = 0;
                HSSFRow titleRow = sheet.createRow(rowIndex++);

                int cellIndex = 0;
                for (Iterator<DBGridItem> iter = getGridTitles().iterator(); iter.hasNext();)
                {
                        DBGridItem item = iter.next();

                        if (item.getFormat().equalsIgnoreCase("h"))
                        {
                                continue;
                        }
                        titleRow.createCell(cellIndex).setCellValue(item.getLabel());

                        cellIndex++;
                }

                List<DBGridRow> data = getAllData();
                log.debug("共获取数据{}条", data.size());
                for (Iterator<DBGridRow> iter = data.iterator(); iter.hasNext();)
                {
                        DBGridRow row = iter.next();
                        HSSFRow dataRow = sheet.createRow(rowIndex);
                        cellIndex = 0;
                        for (Iterator<DBGridItem> itemIter = getGridTitles().iterator(), dataIter = row
                                        .getItems().iterator(); itemIter.hasNext()
                                        && dataIter.hasNext();)
                        {
                                DBGridItem item = itemIter.next();
                                DBGridItem dataItem = dataIter.next();

                                if (item.getFormat().equalsIgnoreCase("h"))
                                {
                                        continue;
                                }
                                dataRow.createCell(cellIndex).setCellValue(dataItem.getLabel());

                                cellIndex++;
                        }

                        rowIndex++;

                }

                try
                {
                        wb.write(os);
                } catch (IOException e)
                {
                        log.error("导出excel发生异常", e);
                }finally
                {
                	try
                        {
	                        wb.close();
                        } catch (IOException e)
                        {
	                        throw new NbmBaseRuntimeException("导出excel发生异常", e);
                        }
                }
        }

}
