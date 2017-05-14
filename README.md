# how to build

## 新的开发任务

git checkout -b something_new

## 开发完成

开发过程中，是否提交到something_new，无关紧要。

开发完成后，要合并到master

git checkout master
git merge something_new

## 用maven发布新版本

在aggr项目路径下执行以下命令

mvn release:prepare
mvn release:perform

prepare时需要输入版本号，默认最低级版本号加一
