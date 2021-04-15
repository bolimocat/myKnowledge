# EverDB手动部署流程

EverDB的部署方案，基本是dbscale+zookeeper+mysql的方式，以下描述手动安装部署的基本流程和使用方法，实际使用过程中如有差异，随时修改。

## 一、部署安装方案

这次安装，我们使用3台机器，每台机器上创建5个mysql实例，分别初始化为不同的集群角色。

三台机器分别为nodeA,nodeB,nodeC，每个机器上分别有角色auth,global,normal,part1,part2。三台机器之间的角色上，有master和slave之分，所以整个集群需要初始化15个mysql实例。

![部署方案](./picture/部署方案.png)

## 二、系统准备

开始部署前，要准备系统环境：

### 1.配置每个系统的打开文件数量

```
--查看当前系统的open files数量，通常设置为1024000。
ulimit -n 
--最简单的修改方法，将ulimit -n 1024000 写入profile，例如当前系统使用root执行操作，则在/etc/profile中适当的位置增加：
ulimit -n 1024000
--即可
```

### 2.在每个节点上配置zookeeper环境

#### 	a .获取zookeeper

wget https://ftp.jaist.ac.jp/pub/apache/zookeeper/zookeeper-3.6.2/apache-zookeeper-3.6.2-bin.tar.gz

保存到3台机器各自适当的位置，例如：/edb/zookeeper，解压。

#### 	b.配置zookeeper

在conf/中编辑zoo.conf文件：

```
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/tmp/zookeeper/data
dataLogDir=/tmp/zookeeper/logs
clientPort=2181
server.1=192.168.130.21:2888:3888
server.2=192.168.130.22:2888:3888
server.3=192.168.130.23:2888:3888
```

server.1=192.168.130.21:2888:3888这句，根据节点个数，IP具体值，端口可用情况配置。

dataDir和dataLogDir，在有用户操作权限的位置提前建好目录。

clientPort是作为客户端去连接服务器时候使用的端口。

为每个节点生成一个myid文件，分别在myid中echo 一个数字，例如第一个节点为1，第二个为2，以此类推。

myid放在每个节点的data目录里，例如节点1上：

touch /tmp/zookeeper/data/myid

echo "1 " > /tmp/zookeeper/data/myid

其他所有节点均朝此操作。

#### 	c.启动zookeeper

在每个节点的bin下执行 ./zkServer.sh start

查看当前节点状态：./zkServer.sh status

可以看到当前节点是leader还是follow，随机连接。

#### 	d.创建集群需要的信息

连接服务器：zkCli.sh -timeout 5000 -server 192.168.130.22:2182，执行创建语句

```
create /dbscale ""
create /dbscale/cluster ""
create /dbscale/cluster/nodes ""
create /dbscale/cluster/online_nodes ""
create /dbscale/keepalive ""
create /dbscale/keepalive/keepalive_init_info ""
create /dbscale/keepalive/keepalive_update_info ""
create /dbscale/keepalive/mul_sync_info ""
create /dbscale/dynamic_operation ""
create /dbscale/dynamic_operation/dynamic_operation_info ""
create /dbscale/dynamic_operation/dynamic_cluster_management_info ""
create /dbscale/dynamic_operation/dynamic_cluster_management_init_info ""
create /dbscale/dynamic_operation/dynamic_config_info ""
create /dbscale/dynamic_operation/session_info ""
create /dbscale/dynamic_operation/block_info ""
create /dbscale/dynamic_operation/dynamic_set_option_info ""
create /dbscale/configuration ""
create /dbscale/configuration/master_config_info ""
create /dbscale/configuration/changed_config_info ""
```

检查创建正确性，在各个节点的登录状态下执行：ls /dbscale 等类似操作。

> 注意：第一次连接时不需要密码，创建信息后再执行连接，提示需要认证信息，addauth digest dbscale:dbscale

## 三、初始化MySQL实例

如前面所说本次部署共需要初始化15个mysql实例，首先要准备15个配置文件。需要注意的是，通过data位置和端口号等信息进行同一个机器上的实例区分。这里只列出可能存在不同信息的配置项。

首先15个实例分布在3台机器上，端口号的分布模式为：

nodeA：30621,30622,30623,30624,30625

nodeB：30722,30723,30724,30725,30726

nodeC：30823,30824,30825,30826,30827

```
[mysqld]
basedir = /edb/app
datadir = /edb/mysqldata/dbdata30621
port = 30621
socket = /edb/mysqldata/dbdata30621/sock_nodeA_30621.sock
server-id =30621
general-log-file = /edb/mysqldata/dbdata30621/mysqld.log
slow-query-log-file = /edb/mysqldata/dbdata30621/mysqld-slow.log
pid-file = /edb/mysqldata/dbdata30621/mysqld_30621.pid
log-error = /edb/mysqldata/dbdata30621/mysqld.err
max_connections = 1000000
open_files_limit = 1000000
```

以上是需要注意每个配置文件里关注的内容，其余完整的配置文件会以单独文件保存。

### 1.初始化mysql实例

```
/*注意每个文件的位置，要初始化15个。*/
/edb/app/bin/mysqld --defaults-file=/opt/everDB/330621.conf --explicit_defaults_for_timestamp --initialize-insecure --user=mysql
```

### 2.启动mysql实例

```
/*每个机器上5个实例，共15个。*/
/edb/app/bin/mysqld_safe --defaults-file=/opt/everDB/330621.conf --user=mysql &
```

### 3.在每个实例上创建用户

```
/*使用socket方式启动，使用-e方式执行用户创建和授予权限操作，共15个。*/
/edb/app/bin/mysql -uroot -S /edb/mysqldata/dbdata30621/sock_nodeA_30621.sock -e "grant all on *.* to dbscale_internal@'%' identified by '123456' with grant option;grant all on *.* to root@'%' identified by '123456' with grant option;reset master;reset slave"
```

## 四、配置dbscale

### 1.获取dbscale版本

> greatdb-cluster-3.1.5046-WithSpark-WithSSL-Build468440a-CentOS7.4_x86_64.tar.gz

分别存放在每个机器的适当位置，例如解压到：/edb/dbscale 

### 2.修改daemon/dbscale_daemon.py中的内容

```
/*每个机器都要修改，对应当前机器上auth实例的端口。*/
file_pid= './dbscale.pid'
user='root'
password='123456'
port='30621'
host='192.168.130.21'
login_retry_times=100
zk_hosts = "127.0.0.1:2181"
```

### 3.修改/dbscale.logrotate文件中日志的位置

```
/*第一行，指向dbscale的位置三个节点都改*/
/edb/dbscale/dbscale.log /edb/dbscale/dbscale.log_audit /edb/dbscale/dbscale.log_slow /edb/dbscale/dbscale.log_zk /edb/dbscale/zookeeper.log
```

### 4.编写dbscale.conf

这里只列出文件中需要特殊关注的参数，完整文件单独保存。

```
[main]
driver = mysql
log-file = /edb/dbscale/log/dbscale.log
pid-file = dbscale.pid
admin-user = dbscale_internal
dbscale-internal-user = dbscale_internal
admin-password = 123456
authenticate-source = auth_ds --注意这里要和下面的数据源匹配
zk-log-file = /edb/dbscale/log/zookeeper.log
zookeeper-host = 192.168.130.21:2181,192.168.130.22:2181,192.168.130.23:2181
--3个zookeeper的ip和端口
cluster-user = dbscale_internal
cluster-password = 123456

[driver mysql]
type = MySQLDriver
port = 16310 --这个端口固定，使用这个端口进行客户端连接。
admin-port = 23399 --同上
bind-address = 0.0.0.0

[catalog default]
data-source = normal_ds --对应下面的名称

--因为当前部署的环境上，包含auth，global，normal，part1，part2，所以以下有5个ds，而每个ds又包含1个master和2个slave，所以有以下的内容。
[data-source auth_ds]
group-id = 105 --全局不重复
user = dbscale_internal
password = 123456
type = replication
semi-sync-on = 1
load-balance-strategy = MASTER
--auth_master后面4个int类型数字分别为连接池的初始值、最大值、低阀值、高阀值，它们之间用“-”进行分割，并且要求满足“初始值<=低阀值<=高阀值<最大值，以下皆同。
master = auth_master-20-1000-400-800
slave = auth_slave1-1-1000-400-800
slave = auth_slave2-1-1000-400-800

[data-source global_ds]
group-id = 106
user = dbscale_internal
password = 123456
type = replication
semi-sync-on = 1
load-balance-strategy = MASTER
master = global_master-20-1000-400-800
slave = global_slave1-1-1000-400-800
slave = global_slave2-1-1000-400-800
slave-source = normal_ds
slave-source = par1
slave-source = par2

[data-source normal_ds]
group-id = 100
user = dbscale_internal
password = Bgview@2020
type = replication
semi-sync-on = 1
load-balance-strategy = MASTER
master = normal_master-20-1000-400-800
slave = normal_slave1-1-1000-400-800
slave = normal_slave2-1-1000-400-800

[data-source part1]
group-id = 102
user = dbscale_internal
password = 123456
type = replication
semi-sync-on = 1
load-balance-strategy = MASTER
master = part1_master-20-1000-400-800
slave = part1_slave1_1-1000-400-800
slave = part1_slave2_1-1000-400-800

[data-source part2]
group-id = 103
user = dbscale_internal
password = 123456
type = replication
semi-sync-on = 1
load-balance-strategy = MASTER
master = part2_master-20-1000-400-800
slave = part2_slave1_1-1000-400-800
slave = part2_slave2_1-1000-400-800

[partition-scheme HASH_TYPE]
type = hash
partition = part2
partition = part1
hash-method = CHAR
is-shard = 1
shard-nums = 16

--以下是具体实例的分配方案和连接信息，同样需要配置15个。
[data-server auth_master]
host = 192.168.130.21
port = 30625
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server auth_slave1]
host = 192.168.130.22
port = 30726
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server auth_slave2]
host = 192.168.130.23
port = 30827
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server global_master]
host = 192.168.130.21
port = 30624
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server global_slave1]
host = 192.168.130.22
port = 30725
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server global_slave2]
host = 192.168.130.23
port = 30826
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server normal_master]
host = 192.168.130.21
port = 30623
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server normal_slave1]
host = 192.168.130.22
port = 30724
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server normal_slave2]
host = 192.168.130.23
port = 30825
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server part1_master]
host = 192.168.130.21
port = 30622
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server part1_slave1]
host = 192.168.130.22
port = 30723
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server part1_slave2]
host = 192.168.130.23
port = 30824
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22


[data-server part2_master]
host = 192.168.130.21
port = 30621
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server part2_slave1]
host = 192.168.130.22
port = 30722
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22

[data-server part2_slave2]
host = 192.168.130.23
port = 30823
user = dbscale_internal
password = 123456
master-priority = 1
remote-user = mysql
remote-password = 123456
remote-port = 22
--以上为dbscale.conf的基本配置方式和参数描述，实际的conf请使用附件中的文件。
```

### 5.检查配置及所有节点状态

首先将dbscale.conf放入auth master机器的dbscale根目录，为了避免执行之后命令的时候出现找不到库文件的问题，export LD_LIRBARY_PATH=./libs，当前dbscale的libs文件夹。

执行./pre_check

![pre_check](./picture/pre_check.png)

截图中可以看到，检查工具提示max open file limit当前是1024000，如果低于1000000会报错，这个是mysql的conf里的max open file，需要在启动时写好。下面是对每个节点检查的状态结果。如果有异常，则可以根据配置文件定位的log，到里面找dbscale.log文件进行分析。

## 五、启动及连接集群

在auth master节点上的dbscale目录，执行启动：

```
dbscale-service.sh start
```

等待完成启动后，可以开始连接集群：

```
mysql -uroot -p123456 -h -P19900
```

如果出现异常，仍然是到之前定义的log目录里查看对应的日志。

## 六、集群使用

（未完待续）