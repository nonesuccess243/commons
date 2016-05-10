package com.nbm.commons.code.web;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.db.DataSourceProvider;

/**
 * 读取配置文件，
 */
public class CodeSQLConfig
{

        private static Logger log = LoggerFactory.getLogger(CodeSQLConfig.class);

        private static CodeSQLConfig _instance;

        private CodeSQLConfig()
        {
        }

        static
        {
                _instance = new CodeSQLConfig();
        }

        public static CodeSQLConfig getInstance()
        {
                return _instance;
        }

        private PropertyResourceBundle RESOURCE_BUNDLE = null;

        public void init(URL url) throws IOException
        {
                RESOURCE_BUNDLE = new PropertyResourceBundle(url.openStream());
                if (log.isDebugEnabled())
                {
                        Enumeration<String> e = RESOURCE_BUNDLE.getKeys();
                        while (e.hasMoreElements())
                        {
                                String k = e.nextElement();
                                log.debug(k + "=" + RESOURCE_BUNDLE.getString(k));
                        }
                }
        }

        public String getString(String key)
        {
                try
                {
                        log.debug("get key[" + key + "]");
                        return RESOURCE_BUNDLE.getString(key);
                } catch (MissingResourceException e)
                {
                        log.error("", e);
                        return '!' + key + '!';
                }
        }

        @SuppressWarnings("unchecked")
        public List<Object[]> getResultList(String sqlName)
        {
                QueryRunner run = new QueryRunner(DataSourceProvider.instance().getDataSource());
                ResultSetHandler h = new ArrayListHandler();
                String sql = getString(sqlName);
                List<Object[]> result = null;
                try
                {
                        result = (List<Object[]>) run.query(sql, h);
                } catch (SQLException e)
                {
                        log.error("gereral result list error[sql=" + sql + "].", e);
                        result = new ArrayList<Object[]>();
                }
                return result;
        }
}
