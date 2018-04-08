# how to start

下载项目，并在根目录下执行mvn clean package test，全部测试用例能成功的话意味着本地环境搭建成功。

开发流程：

1. 先写测试用例，如果写测试用例需要的话，先写接口
1. 执行测试用例，此时必定失败
1. 修改代码至测试用例跑通
1. 确保原有测试用例也能跑通
1. 结束，执行build流程


测试用例尽量不要依赖外部资源，如外部数据库。本地提供了一个嵌入式数据库h2，可以作简单的测试。

# how to build

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

---

如果是在某个分支feature/something下进行开发，在push了所有修改内容后，可以直接在这个分支上执行打包发布操作，之后合并到master上即可。
