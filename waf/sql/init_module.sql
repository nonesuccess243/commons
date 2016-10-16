-- subsys[subsys] start
INSERT INTO S_SUBSYS(ID,CODE,NAME,ABBR,WELCOME_PAGE,ORDERBY) VALUES ('11', 'subsys', '子系统', '子', '','11') ;

-- module[someCode] start
INSERT INTO S_MODULE(ID, CODE, NAME, SUBSYS_ID, ORDERBY) VALUES (1111, 'someCode', '示例', 11, 1111);

INSERT into S_MODULE_ITEM (ID,CODE,NAME,URL,MODULE_ID,ISPUBLIC,ISSHOW,INFO_HANDLER,ORDERBY) values (111111, 'someModuleItem','示例','/subsys/someCode/someModuleItem/index.jsp',1111,'N','Y','',111111);
INSERT into S_MODULE_ITEM (ID,CODE,NAME,URL,MODULE_ID,ISPUBLIC,ISSHOW,INFO_HANDLER,ORDERBY) values (111112, 'someModuleItem2','示例2','/subsys/someCode/someModuleItem2/index.jsp',1111,'N','Y','',111112);
-- module[someCode] end

-- subsys[subsys] end

-- subsys[subsys] start
INSERT INTO S_SUBSYS(ID,CODE,NAME,ABBR,WELCOME_PAGE,ORDERBY) VALUES ('12', 'subsys', '子系统', '子', '','12') ;

-- module[someCode] start
INSERT INTO S_MODULE(ID, CODE, NAME, SUBSYS_ID, ORDERBY) VALUES (1211, 'someCode', '示例', 12, 1211);

INSERT into S_MODULE_ITEM (ID,CODE,NAME,URL,MODULE_ID,ISPUBLIC,ISSHOW,INFO_HANDLER,ORDERBY) values (121111, 'someModuleItem','示例','/subsys/someCode/someModuleItem/index.jsp',1211,'N','Y','',121111);
INSERT into S_MODULE_ITEM (ID,CODE,NAME,URL,MODULE_ID,ISPUBLIC,ISSHOW,INFO_HANDLER,ORDERBY) values (121112, 'someModuleItem2','示例2','/subsys/someCode/someModuleItem2/index.jsp',1211,'N','Y','',121112);
-- module[someCode] end

-- subsys[subsys] end

