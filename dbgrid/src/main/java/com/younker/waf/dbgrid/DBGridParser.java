/******************************************************************************
 * Title:     Younker Web Appliaction Framework
 * Copyright: Copyright (c) 2004
 * Company:   YounkerSoft
 * Author: 	  Xiao Jian
 * Version:   2.0
 *****************************************************************************/
package com.younker.waf.dbgrid;

import static com.younker.waf.dbgrid.DBGridToken.ATTRIBUTE_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.DATE;
import static com.younker.waf.dbgrid.DBGridToken.DATE_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.FORMAT_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.NUMERIC;
import static com.younker.waf.dbgrid.DBGridToken.NUMERIC_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.OPS;
import static com.younker.waf.dbgrid.DBGridToken.OP_BETWEEN;
import static com.younker.waf.dbgrid.DBGridToken.OP_BOTH_LIKE;
import static com.younker.waf.dbgrid.DBGridToken.OP_EQUAL;
import static com.younker.waf.dbgrid.DBGridToken.OP_GE;
import static com.younker.waf.dbgrid.DBGridToken.OP_GREATER;
import static com.younker.waf.dbgrid.DBGridToken.OP_LE;
import static com.younker.waf.dbgrid.DBGridToken.OP_LEFT_LIKE;
import static com.younker.waf.dbgrid.DBGridToken.OP_LESS;
import static com.younker.waf.dbgrid.DBGridToken.OP_NE;
import static com.younker.waf.dbgrid.DBGridToken.OP_RIGHT_LIKE;
import static com.younker.waf.dbgrid.DBGridToken.PARAMETER_TOKEN;
import static com.younker.waf.dbgrid.DBGridToken.STRING;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.WebUtil;

/**
 * The main function of this class is generating "WHERE SQL".
 * <p>
 * <b>NOTE:</b>Chinese encoding problem is a really troublesome problem of java.
 * In practice,developers have to manually change source code when transplanting
 * an application between different app servers or JDBC drivers. At least, I
 * have to do these boring things in the coming years. So the developer must be
 * sure the "WHERE SQL" has proper encoding in its running envirenment.
 * </p>
 */

public class DBGridParser
{

        private static final Logger log = LoggerFactory.getLogger(DBGridParser.class);

        private boolean whereIsExist;
        private HttpServletRequest request;
        private int operations[];
        private String condCols[];
        private String parameters[];
        private String colValues[];
        private String queryType;

        public void parse(DBGrid dbgrid, HttpServletRequest request) throws DBGridException
        {

                this.request = request;
                setQueryType(dbgrid.getGridQueryType());
                String gridName = dbgrid.getGridName();
                operations = dbgrid.extractOperation();
                condCols = dbgrid.extractCondCols();
                parameters = dbgrid.extractParam();

                if (condCols.length != parameters.length)
                {
                        throw new DBGridException(
                                        gridName.concat("：the number of condition columns is not equal the number of paramters."));
                }
                if (condCols.length != operations.length)
                {
                        throw new DBGridException(
                                        gridName.concat("：the number of condition columns is not equal the number of operations."));
                }
                if (parameters.length != operations.length)
                {
                        throw new DBGridException(
                                        gridName.concat("：the number of parameters is not equal the number of operations."));
                }

                String whereSQL = generateWhereSQL(dbgrid);
                // note the encoding convertion,避免与EncodingFilter进行重复编码转化
                // dbgrid.setWhereSQL(WebUtil.UnicodeToGB(fillWhereSQL(whereSQL,
                // request)));
                dbgrid.setWhereSQL(fillWhereSQL(whereSQL));
                dbgrid.generateSQL();
                dbgrid.isReparse(false);
        }

        private String prepareWhereSQL()
        {

                getParameter();
                String tempStr = "";
                for (int i = 0; i < colValues.length; i++)
                {
                        if ("".equals(colValues[i]))
                        {
                                continue;
                        }
                        if (whereIsExist)
                        {
                                tempStr = tempStr + queryType + colValues[i];
                        } else
                        {
                                tempStr = colValues[i];
                                whereIsExist = true;
                        }
                }

                whereIsExist = false;
                return tempStr;
        }

        private String generateWhereSQL(DBGrid dbgrid)
        {
                String tmpWhere = "";
                boolean flag = false;
                String whereSQL = prepareWhereSQL();

                if (!whereSQL.equals(""))
                {
                        tmpWhere = whereSQL;
                        int post = tmpWhere.toLowerCase().indexOf("where");
                        if (post != -1 && post <= 5)
                        {
                                tmpWhere = tmpWhere.substring(post + 5);
                        }
                } else
                {
                        tmpWhere = dbgrid.getGridWhere();

                        int post = tmpWhere.toLowerCase().indexOf("where");
                        if (post != -1 && post <= 5)
                        {
                                tmpWhere = tmpWhere.substring(post + 5);
                        }
                }

                if (!dbgrid.getGridRelation().equals(""))
                {
                        /**
                         * the relation of table relations and query conditions
                         * is "and", such as : t1.column1 = t2.column1 AND
                         * (t1.c2="str1" or t2.c2="str2)
                         */
                        if (!tmpWhere.equals(""))
                        {
                                flag = true;
                                tmpWhere = dbgrid.getGridRelation() + " and (" + tmpWhere + ")";
                        } else
                                tmpWhere = dbgrid.getGridRelation();
                }

                if (tmpWhere != null && !tmpWhere.equals(""))
                {
                        tmpWhere = " where " + tmpWhere;
                } else
                {
                        tmpWhere = "";
                }
                return tmpWhere;
        }

        private String fillWhereSQL(String strWhere)
        {
                String originWhere;
                String temp = strWhere;
                String param = "";
                String paramValue = "";
                originWhere = strWhere;
                int i = 0;
                int subi = 0;
                i = originWhere.indexOf(PARAMETER_TOKEN);
                while (i != -1)
                {
                        originWhere = originWhere.substring(i + 1);
                        subi = originWhere.indexOf(" ");
                        param = originWhere.substring(0, subi != -1 ? subi : originWhere.length()).trim();
                        if (param.lastIndexOf(")") >= 0)
                                param = param.substring(0, param.lastIndexOf(")"));
                        String strType = DBGridUtil.getDataFormat(param);
                        param = DBGridUtil.getLabel(param);
                        i = originWhere.indexOf(PARAMETER_TOKEN);

                        // 倪玉哲 2010.125添加，传来的参数名称可能会含有若干空格，需要执行本语句
                        param = param.trim();

                        paramValue = WebUtil.nvl(request.getParameter(param));

                        if (!NUMERIC_TOKEN.equals(strType))
                        {
                                paramValue = "'" + paramValue + "'";
                        }

                        temp = WebUtil.replace(
                                        temp,
                                        (new StringBuffer(PARAMETER_TOKEN))
                                                        .append(param)
                                                        .append(!"".equals(strType) && strType != null ? FORMAT_TOKEN
                                                                        .concat(strType) : "").toString(), paramValue);
                }

                originWhere = temp;
                i = originWhere.indexOf(ATTRIBUTE_TOKEN);
                while (i != -1)
                {
                        originWhere = originWhere.substring(i + 1);
                        subi = originWhere.indexOf(" ");
                        param = originWhere.substring(0, subi != -1 ? subi : originWhere.length()).trim();
                        String strType = DBGridUtil.getDataFormat(param);
                        param = DBGridUtil.getLabel(param);
                        i = originWhere.indexOf(ATTRIBUTE_TOKEN);

                        paramValue = getValueFromAttribute(param, request);
                        if (!NUMERIC_TOKEN.equals(strType))
                        {
                                paramValue = "'" + WebUtil.isNull(paramValue, "") + "'";
                        }
                        temp = WebUtil.replace(
                                        temp,
                                        (new StringBuffer(ATTRIBUTE_TOKEN))
                                                        .append(param)
                                                        .append(!"".equals(strType) && strType != null ? FORMAT_TOKEN
                                                                        .concat(strType) : "").toString(), paramValue);
                }

                return temp;
        }

        /**
         * 在Attribute中获取参数值，以session、request的顺序获取。 支持apache beanutil表达式
         * 
         * @param paramName
         * @param request
         * @return
         */
        private static String getValueFromAttribute(String paramName, HttpServletRequest request)
        {
                int firstDot = paramName.indexOf(".");
                if (firstDot == -1)
                {
                        Object result = getValueFromAttributeByName(paramName, request);
                        if (result == null)
                                return "";
                        return result.toString();
                } else
                {
                        String attributeName = paramName.substring(0, firstDot);
                        Object value = getValueFromAttributeByName(attributeName, request);
                        String exp = paramName.substring(firstDot + 1);
                        String result;
                        try
                        {
                                result = BeanUtils.getProperty(value, exp);
                        } catch (Exception e)
                        {
                                log.error("get value error[bean=" + value + ", name=" + exp + "].");
                                result = "!ERROR!";
                        }
                        return result;

                        // TODO 未测试
                }
        }

        private static Object getValueFromAttributeByName(String paramName, HttpServletRequest request)
        {
                Object paramValue = request.getSession().getAttribute(paramName);
                log.debug("get value from session[paramName=" + paramName + ", value=" + paramValue + "]");
                if (paramValue == null || paramValue.equals(""))
                {
                        paramValue = (String) request.getAttribute(paramName);
                        log.debug("get value from request attribute[paramName=" + paramName + ", value=" + paramValue
                                        + "]");
                }
                // 去掉此段逻辑，不再从request参数中获取。此机制用于只在后台填充的参数，可用于权限判断的等
                // if (paramValue == null || paramValue.equals(""))
                // {
                // paramValue = request.getParameter(paramName);
                // log.debug("get value from request param[paramName=" +
                // paramName + ", value=" + paramValue + "]");
                // }
                return paramValue;
        }

        private int getColumnType(String column)
        {
                if (column == null || "".equals(column))
                {
                        return STRING;
                }
                String temp[] = WebUtil.split(column, FORMAT_TOKEN);
                if (temp.length <= 1)
                {
                        return STRING;
                }
                if (NUMERIC_TOKEN.equalsIgnoreCase(temp[1]))
                {
                        return NUMERIC;
                }
                if (DATE_TOKEN.equalsIgnoreCase(temp[1]))
                        return DATE;

                return STRING;
        }

        private String getCompareValue(String parameter, String condCol, String operator)
        {

                String tempValue = getParameter(parameter);
                StringBuffer tsb = new StringBuffer("");
                if (tempValue == null || "".equals(tempValue))
                {
                        tsb.append("");
                } else if (NUMERIC == getColumnType(condCol))
                {
                        tsb.append(WebUtil.split(condCol, FORMAT_TOKEN)[0]).append(operator).append(tempValue);
                } else if (DATE == getColumnType(condCol))
                {
                        log.debug("是日期");
                        tsb.append(WebUtil.split(condCol, FORMAT_TOKEN)[0]).append(operator)
                                        .append(DBGrid.getDbD().generateDateParameter(tempValue));
                } else
                {
                        tsb.append(WebUtil.split(condCol, FORMAT_TOKEN)[0]).append(operator).append("'")
                                        .append(tempValue).append("'");
                }
                return tsb.toString();
        }

        /**
         * 
         * 根据参数名获取参数值
         * 
         * @param parameter
         *                参数名，如果以#开头则代表在request或session的request中，优先在session中获取
         * @return
         */
        private String getParameter(String parameter)
        {
                String tempValue = "";
                // If parameter begins with '#' ,it means that the parameter is
                // stored session or request
                if (parameter.indexOf(ATTRIBUTE_TOKEN) != -1)
                {
                        parameter = parameter.substring(1);
                        String sysTemp = (String) request.getSession().getAttribute(parameter);
                        if (sysTemp == null || sysTemp.equals(""))
                        {
                                sysTemp = (String) request.getAttribute(parameter);
                        }
                        if (sysTemp == null || sysTemp.equals(""))
                        {
                                sysTemp = request.getParameter(parameter);
                        }
                        tempValue = WebUtil.nvl(sysTemp);
                } else
                {
                        tempValue = WebUtil.nvl(request.getParameter(parameter));
                }
                return tempValue;
        }

        private String getBetweenValue(String col, String param)
        {

                String thecol = "";
                StringBuffer tsb = new StringBuffer("");
                String par[] = WebUtil.split(param, "+");
                int colType = getColumnType(col);
                thecol = WebUtil.split(col, FORMAT_TOKEN)[0];
                String value1 = WebUtil.nvl(getParameter(par[0]));
                String value2 = WebUtil.nvl(getParameter(par[1]));
                if (NUMERIC == colType)
                {
                        if ("".equals(value1) || "".equals(value2))
                        {
                                tsb.append("");
                        } else
                        {
                                value1 = String.valueOf(WebUtil.getLongValue(value1));
                                value2 = String.valueOf(WebUtil.getLongValue(value2));
                                tsb.append(thecol).append(" between ").append(value1).append(" and ").append(value2);
                        }
                } else if (DATE == colType)
                {
                        if ("".equals(value1) && "".equals(value2))
                        {
                                tsb.append("");
                        } else if (!"".equals(value1) && "".equals(value2))
                        {
                                tsb.append(thecol).append(" between '").append(value1).append("' and '")
                                                .append(WebUtil.getCurrentDate()).append("'");

                        } else if ("".equals(value1) && !"".equals(value2))
                        {
                                tsb.append(thecol).append(" between '").append(WebUtil.getCurrentDate())
                                                .append("' and '").append(value2).append("'");
                        } else
                        {
                                tsb.append(thecol).append(" between '").append(value1).append("' and '").append(value2)
                                                .append("'");
                        }
                } else
                {
                        tsb.append(thecol).append(" between '").append(value1).append("' and '").append(value2)
                                        .append("'");
                }
                return tsb.toString();
        }

        private String getLikeValue(String parameter, String condCol, int operator)
        {

                String tempValue = "";
                StringBuffer tsb = new StringBuffer("");
                tempValue = WebUtil.nvl(getParameter(parameter));
                if (tempValue == null || "".equals(tempValue))
                {
                        tsb.append("");
                } else
                {
                        switch (operator)
                        {
                        case OP_LEFT_LIKE:
                                tsb.append(condCol).append(" like '").append(tempValue).append("%' ");
                                break;

                        case OP_RIGHT_LIKE:
                                tsb.append(condCol).append(" like '%").append(tempValue).append("' ");
                                break;

                        case OP_BOTH_LIKE:
                                tsb.append(condCol).append(" like '%").append(tempValue).append("%' ");
                                break;
                        }
                }
                return tsb.toString();
        }

        private void getParameter()
        {
                if (condCols == null)
                {
                        return;
                }
                colValues = new String[condCols.length];
                for (int i = 0; i < condCols.length; i++)
                {
                        String temp = "";
                        String tempCol = condCols[i];
                        int op = operations[i];
                        switch (op)
                        {
                        case OP_EQUAL:
                        case OP_NE:
                        case OP_GREATER:
                        case OP_GE:
                        case OP_LESS:
                        case OP_LE:
                        {
                                temp = getCompareValue(parameters[i], tempCol, OPS[op]);
                                break;
                        }
                        case OP_BETWEEN:
                        {
                                temp = getBetweenValue(tempCol, parameters[i]);
                                break;
                        }
                        case OP_LEFT_LIKE:
                        case OP_RIGHT_LIKE:
                        case OP_BOTH_LIKE:
                        {

                                temp = getLikeValue(parameters[i], tempCol, op);
                                break;
                        }
                        }
                        colValues[i] = temp;
                }
        }

        private void setQueryType(String queryType)
        {
                String s = WebUtil.nvl(queryType, "and");
                StringBuffer sb = new StringBuffer();
                this.queryType = sb.append(" ").append(s).append(" ").toString();
        }
}
