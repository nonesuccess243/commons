# ArchitectBooter

根据一个json格式的配置文件，生成各种项目骨架文件夹的工具类

使用方法：


在classpath中创建webmeta.json（参考本项目test文件夹中的例子）

在appconfig中有三项可以配置，配置项及默认值分别为：

* commons.webmeta.java_src_dir=src/main/java，此项在src目录不止一个的时候使用
* commons.webmeta.web_src_dir=src/main/webapp，此项一般在mvc框架有特殊习惯时使用，如springmvc的WEB-INF/views
* commons.webmeta.sql_dir=src/main/resources/sql，默认放在classpath中是为了让自动运行sql的工具可以读取

执行ArchitectBooter.INSTANCE.boot();不报错即表示成功。此工具会做：

* 生成Java包
* 生成存放web文件的目录，及每个模块的一个index.jsp
* 生成insert功能模块信息的sql语句
* 生成dbgrid配置文件