package com.younker.waf.dbgrid.Dialect;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.dbgrid.DBGrid;

public class Oracle implements DBGridDialect
{
        private final static Logger log = LoggerFactory.getLogger(Oracle.class);
        
        @Override
        public String getPaging(DBGrid dbGrid)
        {
                String whereSQL = dbGrid.getWhereSQL();
                if(StringUtils.isEmpty(whereSQL))
                        whereSQL = "where";
                else
                        whereSQL = whereSQL + " AND";
                StringBuffer tsb = new StringBuffer();
                tsb.append("select \n").append(dbGrid.getGridCols()).append(",rownum rn").append("\n from \n")
                                .append(dbGrid.getGridTable()).append("\n").append(whereSQL).append("\n rowNum <= ").append((dbGrid.getGridRowsPerPage() * dbGrid.getCurrentPage()))
                                .append("\n").append(dbGrid.getGridGroupByWithGroupBy()).append("\n")
                                .append(dbGrid.getGridOrderByWithOrderBy()).toString();
                
                log.debug("gridRowsPerPage={}, currentPage={}", dbGrid.getGridRowsPerPage(), dbGrid.getCurrentPage());
                return "select * from (" + tsb.toString() + ") t where t.rn > " + (dbGrid.getGridRowsPerPage() * (dbGrid.getCurrentPage() - 1));
        }
        
        /* (non-Javadoc)
         * @see com.younker.waf.dbgrid.Dialect.DBGridDialect#generateDateParameter(java.lang.String)
         */
        @Override
        public String generateDateParameter(String originParamValue)
        {
                return "TO_DATE('" + originParamValue + "', 'YYYY-MM-DD')";
        }
}
