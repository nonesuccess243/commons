-- auto generated by nbm-dbgenerator at 2016-4-16 10:55:07

CREATE TABLE `MYSQL_TEST_MODEL`
(
	`ID` BIGINT unsigned AUTO_INCREMENT,
	`NAME` VARCHAR(200) ,
	`REMARK` VARCHAR(200) ,
	`MODEL_LAST_UPDATE_TIME` DATE ,
	`MODEL_CREATE_TIME` DATE ,
	`MODEL_IS_DELETED` VARCHAR(200) ,
	`MODEL_DELETE_TIME` DATE ,
	PRIMARY KEY (`ID`)
) ;