package com.nbm.waf.core.modeldriven;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface Mapper<MODEL_TYPE, EXAMPLE_TYPE>
{

        /**
         *
         * @param id 主键
         * @return 查询后的结果
         */
        MODEL_TYPE selectByPrimaryKey(Long id);

        /**
         * 如果model中定义了 @NameCol，则可以根据name进行查询。
         *
         * name列的类型必须为字符串
         * @param name 名称列的值
         * @return 查询结果
         */
        MODEL_TYPE selectByName( String name );

        /**
         *
         * @param example
         * @return
         */
        List<MODEL_TYPE> selectByExample(EXAMPLE_TYPE example);


        /**
         *
         * @param example
         * @return 查询结果的数目
         */
        int countByExample(EXAMPLE_TYPE example);

        /**
         *
         * @param record
         * @return 插入后的主键值
         */
        Long insert(MODEL_TYPE record);

        /**
         * 插入一条记录，只执行record中不为空的字段
         * @param record
         * @return 插入后的主键值
         */
        Long insertSelective(MODEL_TYPE record);





        /**
         *
         * @param record
         * @param example
         * @return 受影响的函数
         */
        int updateByExample(@Param("record") MODEL_TYPE record, @Param("example") EXAMPLE_TYPE example);

        /**
         * 仅更新不为空的列
         * @param record
         * @param example
         * @return 受影响的行数
         */
        int updateByExampleSelective(@Param("record") MODEL_TYPE record, @Param("example") EXAMPLE_TYPE example);


        /**
         *
         * @param record
         * @return 受影响的行数
         */
        int updateByPrimaryKeySelective(MODEL_TYPE record);

        /**
         *
         * @param record
         * @return 受影响的行数
         */
        int updateByPrimaryKey(MODEL_TYPE record);


        /**
         * 实际删除的数目
         * @param example
         * @return
         */
        int deleteByExample(EXAMPLE_TYPE example);

        /**
         * 实际删除的数目
         * @param id
         * @return
         */
        int deleteByPrimaryKey(Long id);

}
