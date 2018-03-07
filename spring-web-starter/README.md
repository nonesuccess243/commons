# spring web starter

java web项目maven引用本项目后，就可以使用springmvc。

默认配置如下：

## spring.mvc.config_classes

数组。

配置初始化类，默认为本项目下的RootConfig类。

显式配置后，RootConfig类保留，并添加配置的类。

RootConfig类内容：

* 搜索"com.wayeasoft","com.nbm"两个包下的spring bean
* 定义一个PropertySourcesPlaceholderConfigurer

## spring.mvc.servlet_config_classes

数组。

定义ServletConfigClasses，默认为WebConfig类，显式配置后，WebConfig不再生效。

WebConfig内容：

* spring.mvc.view_prefix： /WEB-INF/views/
* spring.mvc.view_suffix： .jsp

还有其它内容，未完成，详细参考WebConfig代码

## spring.mvc.servlet_mappings

springmvc默认监听的url pattern，默认为*.do

# spring result bean

一个用于spring mvc开发webapi 时，做统一返回值的工具类。

详细内容见文档。

