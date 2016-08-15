# auto generated by nbm-dbgenerator at ${.now}
# 将下面的代码拷贝到mybatis配置文件中
<mapper resource="${mapperPath}"/>

# 如果涉及到修改数据库结构，则此处有alter table语句

<#list fields as field>	
ALTER TABLE ${model.dbTypeName} ADD ${field.dbName} ${field.dbType}${field.dbTypeExtraInfo} <#if field.notNull> not null </#if>;
</#list>