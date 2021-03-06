-- auto generated by nbm-dbgenerator at ${.now}

CREATE TABLE ${model.dbTypeName} (
	<#list fields as field>	
	${field.dbName} ${field.dbType}${field.dbTypeExtraInfo} <#if field.notNull> not null </#if><#if field_has_next>,</#if>
	</#list>
) ;

ALTER TABLE ${model.dbTypeName} ADD CONSTRAINT ${model.dbTypeName}_PK PRIMARY KEY (${model.pkField.dbName});  

CREATE SEQUENCE ${model.dbTypeName}_SEQ	
	START WITH 100001
	INCREMENT BY 1
    MINVALUE 100001 
    MAXVALUE 99999999999    
    NOCYCLE NOCACHE;  
