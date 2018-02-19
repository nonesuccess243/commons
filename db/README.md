# commondao

一个省略mybatis配置文件和Dao代码的语法糖工具

使用方式参见com.wayeasoft.core.modeldriven.dao.CommondaoTest

## 在spring中使用

配置spring扫描com.wayeasoft.core.modeldriven.dao包，或者手动配置CommonDao类为spring的bean

采用如下方式注入：

```java
@Autowired
private CommonDao dao;
```

## 在非spring环境中使用

暂无完全脱离spring的方法。

必须先配置为spring的bean，然后可以用工具类在非spring环境下获取commondao

# modeldriven

## model register

一个支持全局遍历Model列表的语法糖工具。

注册后，可以全局获取Model类的列表及实例。