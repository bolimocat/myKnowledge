# 手动编译java源码

### 1、使用手动编译的原因

因为有时候实际项目的运行环境和本地开发环境有差异，为了避免环境差异造成的运行失败，需要在实际运行环境重新编译源码，但是不可能把IDE在运行环境重装一遍，所以这个时候需要jdk手动执行编译。以下操作，全部在当前环境中已经配置了jdk以及正确的path。

### 2、执行源码准备

假设我们使用eclipse进行开发，选择不打包发布，只发布源码包，可以看到导出的内容中除了源码java文件外，还有已经编译好的class文件，这些是IDE在导出的时候完成的，且存储目录结构符合开发时的源码部署和层次。

### 3、将class打包到一个jar



进入class的最顶层目录，即源码中包的最顶层，指向打包操作：

此处还有一部准备，例如如果项目中使用了第三方lib，需要将lib的jar解压，将目录放在项目名称一层，例如mysql_jdbc.jar，解压后的目录为 mysql/，其中又包含两个目录，需要将mysql/这个目录放入源码包项目同名一层。

jar -cvf myfunction.jar *

### 4、修改启动的class位置

打开myfunction.jar，里面有META-INF目录，再编辑里面的MANIFEST.MF文件：

```
Manifest-Version: 1.0

Main-Class: com.testMysqlStress.function.main

Class-Path: .
```

对应启动的主函数等信息。



即可使用java -jar 执行。