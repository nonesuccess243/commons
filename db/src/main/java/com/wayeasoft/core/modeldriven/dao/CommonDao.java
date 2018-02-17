package com.wayeasoft.core.modeldriven.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.nbm.commons.db.meta.DbNamingConverter;
import com.nbm.core.modeldriven.Field;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.exception.NbmBaseRuntimeException;
import com.younker.waf.db.mybatis.SqlSessionProvider;

/**
 * 基于spring和spring-mybatis的common dao
 * 
 * 需要注入sqlSession
 * 
 * 
 * @author niyuzhe
 *
 */
@Component("commonDao")
public class CommonDao
{

        private final static Logger log = LoggerFactory.getLogger(CommonDao.class);

        private final static String PACKAGE_NAME = "com.wayeasoft.core.modeldriven.dao.CommonMapper";

        public static CommonDao old()
        {
                CommonDao result = new CommonDao();
                result.sqlSession = SqlSessionProvider.getSqlSession();
                return result;
        }
        
        @Autowired
        protected SqlSession sqlSession;

        public <T extends PureModel> T selectById(Class<T> modelClass, Object id)
        {
                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("id", id);
                Map<String, Object> result = sqlSession.selectOne(PACKAGE_NAME + ".selectById", param);

                if (result == null)
                {
                        log.debug("result is null ");
                        return null;
                }

                log.debug("after select ,the map is {}", result.toString());

                result = wrapperResultMap(modelClass, result);
                
                try
                {
                        T m = modelClass.newInstance();
                        BeanUtilsBeanFactory.populate(m, result);
                        return m;
                } catch (Exception e)
                {
                        throw new NbmBaseRuntimeException("执行commondao的selectById函数，将数据库内容转化为Java类时发生异常", e)
                                .set("modelClass", modelClass)
                                .set("id", id);
                }

        }

        /**
         * 如果model中定义了 @NameCol，则可以根据name进行查询。
         *
         * name列的类型必须为字符串
         * 
         * @param name
         *                名称列的值
         * @return 查询结果
         */
        public <T extends PureModel> T selectByName(Class<T> modelClass, String name)
        {
                ModelMeta meta = ModelMeta.getModelMeta(modelClass);
                Field nameCol = meta.getNameCol();
                if (nameCol == null)
                {
                        throw new NbmBaseRuntimeException("Model未定义Name列").set("modelClass", modelClass);
                }

                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("nameCol", nameCol.getDbName());
                param.put("name", name);
                Map<String, Object> result = sqlSession.selectOne(PACKAGE_NAME + ".selectByName", param);

                if (result == null)
                {
                        log.debug("result is null ");
                        return null;
                }

                log.debug("after select ,the map is {}", result.toString());

                BeanUtilsBeanFactory.registerModelClass(modelClass);
                
                result = wrapperResultMap(modelClass, result);

                try
                {
                        T m = modelClass.newInstance();
                        BeanUtilsBeanFactory.populate(m, result);
                        return m;
                } catch (Exception e)
                {
                        throw new RuntimeException(e);
                }
        }

        /**
         *
         * @param example
         * @return
         */
        public <T extends PureModel> List<T> selectByExample(Class<T> modelClass, CommonExample example)
        {
                BeanUtilsBeanFactory.registerModelClass(modelClass);

                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("example", example);

                List<Map<String, Object>> result = sqlSession.selectList(PACKAGE_NAME + ".selectByExample", param);

                if (result == null)
                {
                        log.debug("result is null ");
                        return null;
                }

                log.debug("after select ,the map is {}", result.toString());

                List<T> results = new ArrayList<>(result.size());

                try
                {
                        for (Map<String, Object> map : result)
                        {
                                T m = modelClass.newInstance();
                                BeanUtilsBeanFactory.populate(m, wrapperResultMap(modelClass, map));
                                results.add(m);
                        }
                } catch (Exception exception)
                {
                        throw new RuntimeException(exception);
                }

                return results;
        }

        /**
         *
         * @param example
         * @return 查询结果的数目
         */
        public <T extends PureModel> Integer countByExample(Class<T> modelClass, CommonExample example)
        {
                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("example", example);

                Integer result = sqlSession.selectOne(PACKAGE_NAME + ".countByExample", param);

                return result;
        }

        /**
         *
         * @param record
         * @return 插入后的主键值
         * 
         *         在mysql下测试成功。
         * 
         */
        public <T extends PureModel> Long insert(T record)
        {
                Map<String, Object> param = new HashMap<>();

                param.put("meta", ModelMeta.getModelMeta(record.getClass()));
                param.put("record", record);

                param.put("order", "AFTER");

                sqlSession.insert(PACKAGE_NAME + ".insert", param);

                return record.getId();
        }

        /**
         *
         * @param record
         * @return 受影响的行数
         */
        public <T extends PureModel> int updateById(T record)
        {
                return sqlSession.update(PACKAGE_NAME + ".updateById",
                                ImmutableMap.of("meta", ModelMeta.getModelMeta(record.getClass()), "record", record));
        }

        // /**
        // *
        // * @param record
        // * @param example
        // * @return 受影响的函数
        // */
        // public <T extends PureModel> int updateByExample(T record,
        // CommonExample example)
        // {
        // return sqlSession.update(PACKAGE_NAME + ".updateByExample",
        // ImmutableMap.of("meta", ModelMeta.getModelMeta(record.getClass()),
        // "record", record,
        // "example", example));
        // }

        /**
         * 实际删除的数目
         * 
         * @param id
         * @return
         */
        public <T extends PureModel> int deleteById(Class<T> modelClass, Long id)
        {
                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("id", id);
                return sqlSession.delete(PACKAGE_NAME + ".deleteById", param);
        }

        /**
         * 实际删除的数目
         * 
         * @param example
         * @return
         */
        public <T extends PureModel> int deleteByExample(Class<T> modelClass, CommonExample example)
        {
                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("example", example);
                return sqlSession.delete(PACKAGE_NAME + ".deleteByExample", param);
        }

        /**
         * mybatis会自动把结果集的列名转换为大写，因此即便在sql中用了as也无法准确指定列名
         * 所以，在mybatis的结果集中，保留数据库的下划线命名法，在此处转换一遍
         * 
         * @param modelClass
         * @param result
         * @return 转换后的结果
         */
        private static <T extends PureModel> Map<String, Object> wrapperResultMap(Class<T> modelClass,
                        Map<String, Object> result)
        {
        
                Map<String, Object> newResult = new HashMap<>(result.size());
        
                ModelMeta meta = ModelMeta.getModelMeta(modelClass);
                for (Map.Entry<String, Object> entry : result.entrySet())
                {
                        if (meta.getFieldByDbName(entry.getKey()) != null)
                        {
                                newResult.put(meta.getFieldByDbName(entry.getKey()).getName(), entry.getValue());
                        } else
                        {
                                newResult.put(DbNamingConverter.DEFAULT_ONE.columnName2JavaPropertyName(entry.getKey()),
                                                entry.getValue());
                        }
                }
        
                return newResult;
        }

}