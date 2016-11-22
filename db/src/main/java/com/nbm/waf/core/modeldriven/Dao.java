package com.nbm.waf.core.modeldriven;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nbm.core.modeldriven.PureModel;
import com.younker.waf.db.mybatis.SqlSessionProvider;

public enum Dao
{

        INSTANCE;

        private static LoadingCache<Class<? extends PureModel>, Class<? extends Mapper>> mapperClassCache = CacheBuilder
                        .newBuilder().build(new CacheLoader<Class<? extends PureModel>, Class<? extends Mapper>>()
                        {

                                @Override
                                public Class<? extends Mapper> load(Class<? extends PureModel> key)
                                {
                                        System.out.println("get get");
                                        String mapperClassName = key.getPackage().getName() + ".dao." + key.getSimpleName() + "Mapper";
                                        try
                                        {
                                                return (Class<? extends Mapper>) Class.forName(mapperClassName);
                                        } catch (ClassNotFoundException e)
                                        {
                                                return Mapper.class;
                                                                
//                                                throw new RuntimeException("Class forname错误[className=" + mapperClassName + "].", e);
                                        }
                                }

                        });

        public <T extends PureModel> void delete(T toDelete)
        {
                SqlSessionProvider.getSqlSession().getMapper(getMapperByModelClass(toDelete.getClass()))
                                .deleteByPrimaryKey(toDelete.getId());
        }

//        public <T> T selectByPrimaryKey(Class<T extends Model> claz, Long id)
//        {
//                return (T) SqlSessionProvider.getSqlSession().getMapper(getMapperByModelClass(claz))
//                                .selectByPrimaryKey(id);
//        }

        public <T> int countByExample(Example example)
        {
                return 0;

        }

        public <T> int deleteByExample(Example example)
        {
                return 0;

        }

        public <T> int insert(T record)
        {
                return 0;
        }

        public <T> int insertSelective(T record)
        {
                return 0;
        }

        public <T> List<T> selectByExample(Example example)
        {
                return null;
        }

        public <T> int updateByExampleSelective(T record, Example example)
        {
                return 0;
        }

        public <T> int updateByExample(T record, Example example)
        {
                return 0;
        }

        public <T> int updateByPrimaryKeySelective(T record)
        {
                return 0;
        }

        public <T> int updateByPrimaryKey(T record)
        {
                return 0;
        }

        public static Class<? extends Mapper> getMapperByModelClass(Class<? extends PureModel> claz)
        {
                        try
                        {
                                return mapperClassCache.get(claz);
                        } catch (ExecutionException e)
                        {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                return null;
                        }
                        
        }

        private static Class<?> getExampleByModelClass(Class claz)
        {
                String mapperClassName = claz.getPackage().getName() + ".dao." + claz.getSimpleName() + "Example";
                try
                {
                        return (Class<? extends Mapper>) Class.forName(mapperClassName);
                } catch (ClassNotFoundException e)
                {
                        throw new RuntimeException("Class forname错误[className=" + mapperClassName + "].", e);
                }
        }
}
