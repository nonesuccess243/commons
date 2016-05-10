-- auto generated by nbm-dbgenerator at ${.now}

CREATE TABLE `${model.dbTypeName}`
(
	<#list fields as field>	
	<#if field.isPk()>
	`${field.dbName}` ${field.dbType} unsigned AUTO_INCREMENT<#if field_has_next>,</#if>
	<#else>
	`${field.dbName}` ${field.dbType}${field.dbTypeExtraInfo} <#if field.notNull> not null </#if>,
	</#if>
	</#list>
	PRIMARY KEY (`${model.pkField.dbName}`)
) ;