//TODO 待整理为不需要ibatis的形式

//package com.nbm.commons.config;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;import org.slf4j.LoggerFactory;
//
//import com.younker.waf.db.IBatisConfig;
//
///**
// * 运行时配置信息工具类。运行时配置信息应偏重于业务相关的信息，如学校管理系统的招生信息等。
// * 所有关于RuntimeConfig的操作都应通过本类完成。
// */
//public enum RuntimeConfigUtil
//{
//        INSTANCE;
//
//        private Map<String, String> map = new HashMap<String, String>();
//
//        private Logger log = LoggerFactory.getLogger(this.getClass());
//
//        public String getValue(String key) throws NoSuchConfigInfoException
//        {
//
//                String value = this.map.get(key);
//                if (value == null)
//                {
//
//                        value = getValueFromDB(key);
//                        this.map.put(key, value);
//                }
//
//                return value;
//        }
//
//        public void setValue(String key, String value)
//        {
//                saveValue(key, value);
//                this.map.put(key, value);
//        }
//
//        private String getValueFromDB(String key) throws NoSuchConfigInfoException
//        {
//                String value = "error";
//                try
//                {
//                        value = (String) IBatisConfig.getSqlMapInstance().queryForObject(
//                                        "getValue", key);
//                } catch (SQLException e)
//                {
//                        this.log.fatal("get runtime config Value error[key=" + key + "].", e);
//                }
//                if (value == null)
//                {
//                        log.error("get value return null[key=" + key + "].");
//                        throw new NoSuchConfigInfoException(key);
//                }
//                return value;
//        }
//
//        /**
//         * 将key，value键值对进行持久化。
//         * 
//         * @param key
//         * @param value
//         */
//        private void saveValue(String key, String value)
//        {
//                log.debug("save value[key=" + key + ", value=" + value);
//                Map<String, String> model = new HashMap<String, String>();
//                model.put("value", value);
//                model.put("key", key);
//                try
//                {
//                        if (this.map.get(key) == null)
//                        {
//                                // 为了避免数据库中存在此数据，而内存中不存在此数据的情况，此处先在数据库中进行数据读取。
//                                // 如果在数据库中得到了该数据，则将此数据存入内存，然后递归调用saveValue函数
//                                log.debug("no such element in memory, insert first");
//                                try
//                                {
//                                        String v = getValueFromDB(key);
//                                        this.map.put(key, v);
//                                        saveValue(key, value);
//                                } catch (NoSuchConfigInfoException e)
//                                {
//                                        IBatisConfig.getSqlMapInstance().insert("insertValue",
//                                                        model);
//                                }
//                        } else
//                        {
//                                IBatisConfig.getSqlMapInstance().update("saveValue", model);
//                                this.map.put(key, value);
//                        }
//                } catch (SQLException e)
//                {
//                        this.log.fatal("Save runtime config infomation error.[key=" + key
//                                        + ",value=" + value + "].", e);
//                }
//        }
//        
//
// }
