package com.younker.waf.dbgrid;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理DBGrid配置信息
 * 
 * @author computer
 * 
 */
public class DBGridEngine
{
        private final static Logger log = LoggerFactory.getLogger(DBGridEngine.class);
        private final static String DEFAULT_CONFIG_FILE_PATH = "dbgrid_config/dbgrids.xml";
        private static Map<String, DBGridEngine> instances = new HashMap<String, DBGridEngine>();

        public final static DBGridEngine getInstance(String configFilePath)
        {
                DBGridEngine instance = instances.get(configFilePath);
                if (instance == null)
                {
                        log.debug("create dbgrid engine[configFile:" + configFilePath + "].");
                        instance = new DBGridEngine(configFilePath);
                        instances.put(configFilePath, instance);
                }
                return instance;
        }

        public final static DBGridEngine getDefaultInstance()
        {
                return getInstance(DEFAULT_CONFIG_FILE_PATH);
        }

        private DBGrids dbgrids;

        private DBGridEngine(String configFilePath)
        {
                dbgrids = DBGridGenerator.getInstance().getDBGrids(configFilePath);
        }

        public DBGrids getDBGrids()
        {
                return dbgrids;
        }
}
