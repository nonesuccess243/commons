
/*新建数据*/
CREATE DATABASE WAF_TEST
    DEFAULT CHARACTER SET utf8;
    
     
GRANT ALL PRIVILEGES ON WAF_TEST.* TO WAF_TEST@'%' IDENTIFIED BY 'hrms_12530_We'
WITH GRANT OPTION;
 