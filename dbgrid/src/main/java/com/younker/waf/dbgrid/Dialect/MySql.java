package com.younker.waf.dbgrid.Dialect;

import com.younker.waf.dbgrid.DBGrid;

public class MySql implements DBGridDialect
{
        /* (non-Javadoc)
         * @see com.younker.waf.dbgrid.Dialect.DBGridDialect#generateDateParameter(java.lang.String)
         */
        @Override
        public String generateDateParameter(String originParamValue)
        {
                return "'" + originParamValue + "'";
        }

        @Override
        public String getPaging(DBGrid dbGrid)
        {
                StringBuffer tsb = new StringBuffer();
                tsb.append("select \n").append(dbGrid.getGridCols()).append("\n from \n")
                                .append(dbGrid.getGridTable()).append("\n").append(dbGrid.getWhereSQL())
                                .append("\n").append(dbGrid.getGridGroupByWithGroupBy()).append("\n")
                                .append(dbGrid.getGridOrderByWithOrderBy()).toString();
                
                return tsb.toString() + " limit " + ((dbGrid.getGridRowsPerPage() * (dbGrid.getCurrentPage() - 1)) + "," 
                		+ dbGrid.getGridRowsPerPage() );
        }
}
