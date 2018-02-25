package com.younker.waf.dbgrid;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
        
        private final static String CONFIG_FILE_DIR = "dbgrid_config";
        
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
                Path path = Paths.get(CONFIG_FILE_DIR);//在eclipse中运行此程序时，用.获取的是项目源代码根目录
                
                try
                {
                        Files.walkFileTree(path, new SimpleFileVisitor<Path>()
                        {

                                @Override
                                public FileVisitResult visitFile(Path file,
                                                BasicFileAttributes attrs) throws IOException
                                {
                                        if( dbgrids == null )
                                        {
                                                dbgrids = DBGridGenerator.getInstance().getDBGrids("");
                                        }
                                        return null;
                                }

                        });
                } catch (IOException e)
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                dbgrids = DBGridGenerator.getInstance().getDBGrids(configFilePath);
        }

        public DBGrids getDBGrids()
        {
                return dbgrids;
        }
}
