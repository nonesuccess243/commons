# how to build
[db](db)
## 新的开发任务

git checkout -b something_new

## 开发完成

开发过程中，是否提交到something_new，无关紧要。

开发完成后，要合并到master

git checkout master
git merge something_new

## 用maven发布新版本


由于master分支已经被锁定，而发布过程中mvn release插件需要提交内容，因此必须新建一个分支：

git checkout -b build20170513

之后在aggr项目路径下执行以下命令

mvn release:prepare
mvn release:perform

prepare时需要输入版本号，默认最低级版本号加一

由于发布成功后，tags应多一条最新版本的记录，maven私服上也应该有了最新版本的包。

此时，将用于打包的分支push：

git push --set-upstream origin build20170531

再到gitbucket上提出该分支的pr，合并后，打包完成。

如果有正在并行开发的其他人员，则执行git rebase，将master上的更新同步下来
