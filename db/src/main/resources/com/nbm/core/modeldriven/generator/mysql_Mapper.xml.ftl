<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--  auto generated by nbm-dbgenerator at ${.now} -->
<mapper namespace="${namespace}">
	<resultMap id="BaseResultMap" type="${typeName}">
		<#list fields as field>
		<#if field.isPk()>
		<id column="${field.dbName}" property="${field.name}" jdbcType="${field.mybatisJdbcType}" />
		<#else>
		<result column="${field.dbName}" property="${field.name}" jdbcType="${field.mybatisJdbcType}"/>
		</#if>
		</#list>
	</resultMap>

	<sql id="Example_Where_Clause">
		<where>
			<foreach collection="oredCriteria" item="criteria" separator="or">
				<if test="criteria.valid">
					<trim prefix="(" suffix=")" prefixOverrides="and" >
						<foreach collection="criteria.criteria" item="criterion">
							<choose>
								<when test="criterion.noValue">
									and ${'$'}{criterion.condition}
								</when>
								<when test="criterion.singleValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value}
								</when>
								<when test="criterion.betweenValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value} and
									${'#'}{criterion.secondValue}
								</when>
								<when test="criterion.listValue">
									and ${'$'}{criterion.condition}
									<foreach collection="criterion.value" item="listItem" open="(" close=")" 
										separator=",">
										${'#'}{listItem}
									</foreach>
								</when>
							</choose>
						</foreach>
					</trim>
				</if>
			</foreach>
		</where>
	</sql>
	<sql id="Update_By_Example_Where_Clause">
		<where>
			<foreach collection="example.oredCriteria" item="criteria"
				separator="or">
				<if test="criteria.valid">
					<trim prefix="(" prefixOverrides="and" suffix=")">
						<foreach collection="criteria.criteria" item="criterion">
							<choose>
								<when test="criterion.noValue">
									and ${'$'}{criterion.condition}
								</when>
								<when test="criterion.singleValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value}
								</when>
								<when test="criterion.betweenValue">
									and ${'$'}{criterion.condition} ${'#'}{criterion.value} and
									${'#'}{criterion.secondValue}
								</when>
								<when test="criterion.listValue">
									and ${'$'}{criterion.condition}
									<foreach close=")" collection="criterion.value" item="listItem"
										open="(" separator=",">
										${'#'}{listItem}
									</foreach>
								</when>
							</choose>
						</foreach>
					</trim>
				</if>
			</foreach>
		</where>
	</sql>
	<sql id="Base_Column_List">
		<#list fields as field>
		${field.dbName}<#if field_has_next>,</#if>
		</#list>
	</sql>
	<select id="selectByExample" parameterType="${exampleJavaName}"
		resultMap="BaseResultMap">
		select
		<if test="distinct">
			distinct
		</if>
		<include refid="Base_Column_List" />
		from ${model.dbTypeName}
		<if test="_parameter != null">
			<include refid="Example_Where_Clause" />
		</if>
		<if test="orderByClause != null">
			order by ${'$'}{orderByClause}
		</if>
	</select>
	<select id="selectByPrimaryKey" parameterType="java.lang.Long"
		resultMap="BaseResultMap">
		select
		<include refid="Base_Column_List" />
		from ${model.dbTypeName}
		where ID = ${'#'}{${model.pkField.name},jdbcType=${model.pkField.dbType}}
	</select>
	
	<#if model.nameCol??>
	<select id="selectByName" parameterType="java.lang.String"
		resultMap="BaseResultMap">
		select
		<include refid="Base_Column_List" />
		from ${model.dbTypeName}
		where ${model.nameCol.dbName} = ${'#'}{${model.nameCol.name},jdbcType=${model.nameCol.dbType}}
	</select>
	</#if>
	
	<delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
		delete from ${model.dbTypeName}
		where ID = ${'#'}{${model.pkField.name},jdbcType=${model.pkField.dbType}}
	</delete>
	<delete id="deleteByExample" parameterType="${exampleJavaName}">
		delete from ${model.dbTypeName}
		<if test="_parameter != null">
			<include refid="Example_Where_Clause" />
		</if>
	</delete>
	<insert id="insert" parameterType="${typeName}">
		<selectKey keyProperty="id" order="AFTER" resultType="java.lang.Long">
			SELECT LAST_INSERT_ID()
		</selectKey>
		insert into ${model.dbTypeName} (
		<#list fields as field>
		${field.dbName}<#if field_has_next>,</#if>
		</#list>
		)
		values (
		<#list fields as field>
		${'#'}{${field.name},jdbcType=${field.mybatisJdbcType}}<#if field_has_next>,</#if>
		</#list>
		)
	</insert>
	<insert id="insertSelective" parameterType="${typeName}">
		<selectKey keyProperty="id" order="AFTER" resultType="java.lang.Long">
			SELECT LAST_INSERT_ID()
		</selectKey>
		insert into ${model.dbTypeName}
		<trim prefix="(" suffix=")" suffixOverrides=",">
			ID
			<#list fields as field>
			<#if field.name != 'id'>
			,
			<if test="${field.name} != null">
				${field.dbName}
			</if>
			</#if>
			</#list>
		</trim>
		<trim prefix="values (" suffix=")" suffixOverrides=",">
			<#list fields as field>
			${'#'}{${field.name}, jdbcType=${field.mybatisJdbcType}}
			<#if field_has_next>,</#if>
			</#list>
		</trim>
	</insert>
	<select id="countByExample" parameterType="${exampleJavaName}"
		resultType="java.lang.Integer">
		select count(*) from ${model.dbTypeName}
		<if test="_parameter != null">
			<include refid="Example_Where_Clause" />
		</if>
	</select>
	<update id="updateByExampleSelective" parameterType="map">
		update ${model.dbTypeName}
		<set>
			<#list fields as field>
			<if test="${field.name} != null">
				${field.dbName} = ${'#'}{record.${field.name},jdbcType=${field.mybatisJdbcType}}<#if
				field_has_next>,</#if>
			</if>
			</#list>
		</set>
		<if test="_parameter != null">
			<include refid="Update_By_Example_Where_Clause" />
		</if>
	</update>
	<update id="updateByExample" parameterType="map">
		update ${model.dbTypeName}
		set
		<#list fields as field>
		${field.dbName} = ${'#'}{record.${field.name},jdbcType=${field.mybatisJdbcType}}<#if
		field_has_next>,</#if>
		</#list>
		<if test="_parameter != null">
			<include refid="Update_By_Example_Where_Clause" />
		</if>
	</update>
	<update id="updateByPrimaryKeySelective" parameterType="${typeName}">
		update ${model.dbTypeName}
		<set>
			<#list fields as field>
			<if test="${field.name} != null">
				${field.dbName} = ${'#'}{${field.name},jdbcType=${field.mybatisJdbcType}}<#if
				field_has_next>,</#if>
			</if>
			</#list>
		</set>
		where ID = ${'#'}{${model.pkField.name},jdbcType=${model.pkField.dbType}}
	</update>
	<update id="updateByPrimaryKey" parameterType="${typeName}">
		update ${model.dbTypeName}
		set <#list fields as field>
		${field.dbName} =
		${'#'}{${field.name},jdbcType=${field.mybatisJdbcType}}<#if field_has_next>,</#if>
		</#list>
		where ID = ${'#'}{${model.pkField.name},jdbcType=${model.pkField.dbType}}
	</update>
</mapper>