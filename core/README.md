# 概述

本项目用于保存公共基础设施相关的代码。

# spring相关

## spring context util

提供一个在非spring环境下全局获取spring bean的方法

```java
SpringTestBean bean = SpringContextUtil.getInstance().getBean(SpringTestBean.class);
```