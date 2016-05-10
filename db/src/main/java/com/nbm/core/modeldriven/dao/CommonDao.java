package com.nbm.core.modeldriven.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;

import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.ModelMeta;
import com.younker.waf.db.mybatis.SqlSessionProvider;


public enum CommonDao
{
        
        INSTANCE;
        
        private SqlSession sqlSession()
        {
                return SqlSessionProvider.getSqlSession();
        }
        
        public <T extends Model> T selectById( Class<T> modelClass, Object id )
        {
                Map<String, Object> param = new HashMap<>();
                param.put("meta", ModelMeta.getModelMeta(modelClass));
                param.put("id", 1l);
                Map<String, Object> result = sqlSession().selectOne("com.nbm.core.modeldriven.dao.CommonMapper.selectByPrimaryKey", param);
                
                
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
        
        public <T> Long insert( T model )
        {
                return null;
        }

}
