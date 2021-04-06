# 万里之行头一步——MySQL的连接参数init_connect的简单使用

作为Mysql的一个连接参数，init_connect本身并不十分抢眼，官方手册中对其介绍只有几行，只简单指出了init_connect的基本规则：

1. 只有普通用户通过客户端连接服务器时才会执行这个参数，超级用户或有连接管理权限的用户会将其跳过；


2. 一个init_connect可以执行一条或多条sql语句；


3. init_connect的sql语句中若包含错误，则会导致用户连接失败。

下面我们来简单描述一下他的基本用法和注意事项。

### 一、用于审计

#### 1、配置

​	从具体的使用角度讲，在网上能查到的init_connect使用频率最高的，就是结合mysql自身的binlog进行的审计功能，而init_connect则是被用来记录普通用户的登录信息，追踪业务操作时，可以根据记录的用户线程编号，用户名等基本信息，跟踪日志中该用户的所有操作。

​	我们通过mysql单机版本8.0.23来说明审计功能的实现过程和最终效果。mysql的编译、安装、初始化等操作这里不做过多描述，相关内容请读者自行搜索学习。

![image](./edit/main/MySQL/pic/Picture1.png)

​	网上很多资料写的是通过general-log的内容进行审计跟踪，但由于general-log会记录用户登录后的所有内容，存在IO占用和资源消耗等问题，所以相对简便和节省资源的方式是通过binlog和init_connect来执行，所有对数据库修改的关键步骤执行结束时,将在binlog的末尾写入一条记录,同时通知语句解析器,语句执行完毕，我们可以根据这个信息，对用户的操作进行审计跟踪。

​	首先从配置文件中看到当前MySQL系统中的binlog配置，系统会在持续执行时，自动生成.000001及以后的文件。

![image](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture2.png)

​	接下来我们来看init_connect的设置，需要在mysql初始化data的时候，就在cnf文件中增加init_connect参数，如下：

```
init_connect='insert into auditdb.access(thread_id,login_time,localname,matchname) values (connection_id(),now(),user(),current_user());'
```

参数名：init_connect

参数值：单引号中的部分，就是普通用户在通过客户端连接服务器时需要完成的操作。

​	这里我们设置的是向auditdb.access中写入一条记录，这个库和表是我们稍后会创建的，内容包括当前用户的“thread_id，登录的时间，登录用户名，匹配的用户规则”。以下为建表语句：

```
mysql> create database auditdb;

mysql> create table auditdb.access(
  id int not null auto_increment,
  thread_id int not null,
  login_time timestamp,
  localname varchar(50) default null,
  matchname varchar(50) default null, 
  primary key (id)
) comment '审计用户登录信息';
```

​	由系统管理员创建这个用于存储普通用户登记信息的表，根据实际需要，还可以修改表结构以获得更多功能。

![p3](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture3.png)

​	数据库管理员登录，创建实际存储普通用户登录记录的库和表。

![p4](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture4.png)

#### 2、应用

​	根据实际使用需要，创建存储业务数据的库。

```
mysql> create database testDB;
```

​	创建一个普通用户，要求这个用户的所有操作被审计。

```
--创建用户user1
mysql> CREATE USER  'user1'@'%' IDENTIFIED BY '123456';
--首先授权user1可以通过任何位置访问testDB库的所有操作
mysql> GRANT ALL ON testDB.* TO 'user1'@'%';
--授权user1对访问记录表有写入权限
mysql> GRANT insert on auditdb.access to 'user1'@'%';
--刷新所有用户权限生效
mysql> flush privileges;
```

​	数据库管理员退出，新用户user1登录，在testDB中执行一些操作。

![p5](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture5.png)

```
mysql> use testDB;
mysql> create table tb1(a int,b int);
mysql> insert into tb1(1,2);
mysql> select * from tb1;
```

​	user1登录成功，并且可以执行use和一些列操作，说明配置正确生效。由于user1对auditdb.access没有select的权限，因此暂时重新由root登录，查看该表的记录。

![p15](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture15.png)

​	可以查看到user1的两次登录信息。

#### 3、审计过程

​	有了登录信息，我们就可以通过thread_id在binlog中匹配用户的操作了。首先我们要了解MySQL的binlog有两种打开方式，一种是在登录的MySQL中通过show命令，可以有格式的浏览binlog文件。

​	

```
mysql> show binlog events in ‘binlogfile’;
```

![p6](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture6.png)

​	![p7](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture7.png)

​	其中Pos和End_log_pos成对出现，可以理解为一个事务的开始和结束，下面的截图中可以看到，从10292到10323，完成一个提交，10402到10479是一个事务开始，10479到10531查询的是testDB.tb3这个表，10531到10575对这个表（id都是128）执行了写入，最后从10575到10606做提交。

​	那么如何知道这些操作是谁完成的能，则需要通过第二种binlog的查看方式，使用系统工具mysqlbinlog查看具体信息：bin/.mysqlbinlog binlog.000002

![p8](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture8.png)

​	找到刚才10402到10479开始事务的位置，看到有一个thread_id=33，这个就是执行接下来操作的用户ID，登录状态下，可以直接执行select connection_id();查看当前用户的这个值。而init_connect要做的，就是把每次用户登录时的thread_id记录下来。

​	具体能审计的内容受binlog中的内容限制，使用mysqlbinlog打开的二进制文件可以重定向到文件，再进行文本过滤。

​	以上就算通过init_connect和binlog组合，完成mysql的基本审计操作。

### 二、对于init_connect的扩展使用

#### 	1、执行存储过程

​	如前面所讲，init_connect的功能其实本身非常简单，无非就是让普通用户在连接服务器的时候能够自动执行SQL语句，那么除了做用户登录的信息登记，是否能执行一些相对复杂的任务呢，比如我们尝试在这里运行存储过程或函数，在init_connect中执行一个call，通过存储过程或函数调用大批任务。下面我们来简单尝试一下：

​	假设当前mysql系统中包含一个testDB库，其中包含一个tb1(id int primary key auto_increment,time timestamp, comment text)表。

​	我们编写一个存储过程，自动向这个表里写一些内容。

```
DELIMITER //
CREATE PROCEDURE init_insert()
begin
insert into testDB.tb1 (time,comment) values (now(),’insert by procedure’);
end //
DELIMITER ;
```

​	由于这个存储过程是在testDB这个库中创建的，因此在登录状态下，尝试了使用：

```
use testDB;
call init_insert();
```

​	和登录后直接执行：

```
call testDB.init_insert();
```

​	两种方式都是可以正常执行的，考虑init_connect的时候没有use库，所以我们按call testDB.init_insert()的方式写。

![p9](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture9.png)

​	普通用户登录，查询testDB.tb1：

![p10](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture10.png)

​	查询到了结果，说明用户登录时通过init_connect执行的存储过程可用。

#### 2、执行多条语句

​	还用刚才的testDB库，再创建一个tb2(a int,b int);，同样的思路，继续给用户user1在tb2上的写入权限。

![p11](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture11.png)

​	在cnf的init_connect中写为两个SQL连续的形式。

```
init_connect='insert into tb1 (time,comment) values (now(),'abcde');insert into tb2 (a,b) values (1,2)'
```

![p12](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture12.png)

​	尝试普通用户连接并查看相关表记录。

![13](https://github.com/bolimocat/myKnowledge/blob/main/MySQL/pic/Picture13.png)

​	同时在两个表里看到了插入的内容，说明init_connect配置有效。

​	综上所述，init_connect的功能简单，只是让普通用户在连接时自动执行一些语句，甚至DBA都不关心当时执行返回了哪些结果，而只关心成功连接则可以use下去，不成功执行则会报类似

![p14](./MySQL/pic/Picture14.png)

的错误。

### 三、使用注意

​	init_connect的官方文档篇幅不大，但是在验证上面的功能时，发现了一个比较麻烦的问题，就是init_connect虽然是控制客户端连接时执行的内容，但是这个设定需要在初始化data目录的时候就设置好，单纯的重启已有的库，对于参数修改是没有效果的，或者会出现莫名失效的问题，使用上多少需要注意，但总之init_connect功能比较简单，可做的事情也非常有限，我们利用好他提供的用户登录信息记录，做好审计相关的内容就足够了，这也是目前在网上关于这个参数讨论最多的用法。
