package com.nbm.core.modeldriven.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.ModelMeta;
import com.nbm.core.modeldriven.ModelUtils;
import com.nbm.waf.core.modeldriven.Example;
import com.younker.waf.db.mybatis.SqlSessionProvider;

public enum CommonDao
{

        INSTANCE;
        
        private final static Logger log = LoggerFactory.getLogger(CommonDao.class);

        private SqlSession sqlSession()
        {
                return SqlSessionProvider.getSqlSession();
        }

        public <T extends Model> T selectById( Class<T> modelClass, Object id )
        {
                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("id", id);
                Map<String, Object> result = sqlSession().selectOne("com.nbm.core.modeldriven.dao.CommonMapper.selectByPrimaryKey", param);
                
                if( result == null )
                {
                        log.debug("result is null ");
                        return null;
                }
                
                log.debug("after select ,the map is {}", result.toString());
                
                result = wrapperResultMap(modelClass, result);
                
                try
                {
                       T m = modelClass.newInstance();
                       BeanUtils.populate(m, result);
                       return m;
                } catch (Exception e)
                {
                        throw new RuntimeException(e); 
                } 
                
        }

        
        /**
         * //mybatis会自动把结果集的列名转换为大写，因此即便在sql中用了as也无法准确指定列名
                //所以，在mybatis的结果集中，保留数据库的下划线命名法，在此处转换一遍
         * @param modelClass
         * @param result
         * @return 转换后的结果
         */
        private <T extends Model> Map<String, Object> wrapperResultMap(Class<T> modelClass, Map<String, Object> result)
        {
                
                Map<String, Object> newResult = new HashMap<>(result.size());
                
                ModelMeta meta = ModelMeta.getModelMeta(modelClass);
                for( Map.Entry<String, Object> entry : result.entrySet() )
                {
                        newResult.put(meta.getFieldByDbName(entry.getKey()).getName(), entry.getValue());
                }
                
                return newResult;
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
        public <T> T selectByName(String name)
        {
                return null;
        }

        /**
         *
         * @param example
         * @return
         */
        public <T> List<T> selectByExample(Example example)
        {
                return null;
        }

        /**
         *
         * @param example
         * @return 查询结果的数目
         */
        public <T> int countByExample(Example example)
        {
                return 0;
        }

        /**
         *
         * @param record
         * @return 插入后的主键值
         */
        public <T> Long insert(T record)
        {
                return 0l;
        }

        /**
         * 插入一条记录，只执行record中不为空的字段
         * 
         * @param record
         * @return 插入后的主键值
         */
        public <T> Long insertSelective(T record)
        {
                return 0l;
        }

        /**
         *
         * @param record
         * @param example
         * @return 受影响的函数
         */
        public <T> int updateByExample(T record, Example example)
        {
                return 0;
        }

        /**
         * 仅更新不为空的列
         * 
         * @param record
         * @param example
         * @return 受影响的行数
         */
        public <T> int updateByExampleSelective(T record, Example example)
        {
                return 0;
        }

        /**
         *
         * @param record
         * @return 受影响的行数
         */
        public <T> int updateByPrimaryKeySelective(T record)
        {
                return 0;
        }

        /**
         *
         * @param record
         * @return 受影响的行数
         */
        public <T> int updateByPrimaryKey(T record)
        {
                return 0;
        }

        /**
         * 实际删除的数目
         * 
         * @param example
         * @return
         */
        public <T> int deleteByExample(Example example)
        {
                return 0;
        }

        /**
         * 实际删除的数目
         * 
         * @param id
         * @return
         */
        public <T> int deleteByPrimaryKey(Long id)
        {
                return 0;
        }

}
