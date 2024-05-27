# MySql8.0.25部署MGR集群

### 1 准备mysql单机实例

当前部署的mysql使用8.0.25，使用传统的方式初始化data目录，启动服务等。

```
--初始化，start.conf会放在当前文档目录中
./mysqld --defaults-file=/mgr/start.conf --explicit_defaults_for_timestamp --initialize-insecure --user=mysql

--启动服务
./mysqld_safe --defaults-file=/mgr/start.conf --user=mysql &
```

假设当前集群环境有3个节点，就要在三个节点上完成初始化操作。



### 2 在每个mgr集群的实例上创建集群连接的用户

```
CREATE USER rpl_user@'%' IDENTIFIED BY '123456';
GRANT REPLICATION SLAVE ON *.* TO rpl_user@'%';
RESET MASTER;
CHANGE MASTER TO MASTER_USER='rpl_user', MASTER_PASSWORD='123456' FOR CHANNEL 'group_replication_recovery';
INSTALL PLUGIN group_replication SONAME 'group_replication.so';
--所有节点均要操作
```



### 3 启动mgr主节点

登录作为主节点的实例，执行：

```
SET GLOBAL group_replication_bootstrap_group=ON;
start group_replication;
SET GLOBAL group_replication_bootstrap_group=OFF;

--查看当前状态：
SELECT * FROM performance_schema.replication_group_members;
--主节点为primary
```



### 4 从节点加入集群

```
--每个从节点上执行加入
start group_replication;
```

全部执行完，查看节点状态：

> mysql> SELECT * FROM performance_schema.replication_group_members;
> +---------------------------+--------------------------------------+----------------+-------------+--------------+-------------+----------------+
> | CHANNEL_NAME              | MEMBER_ID                            | MEMBER_HOST    | MEMBER_PORT | MEMBER_STATE | MEMBER_ROLE | MEMBER_VERSION |
> +---------------------------+--------------------------------------+----------------+-------------+--------------+-------------+----------------+
> | group_replication_applier | 3274aa07-f8e1-11eb-b97b-52540082e00c | 172.16.130.209 |        3306 | ONLINE       | SECONDARY   | 8.0.25         |
> | group_replication_applier | 6766d630-f8e0-11eb-b4c4-525400a8c2d1 | 172.16.130.208 |        3306 | ONLINE       | SECONDARY   | 8.0.25         |
> | group_replication_applier | c1a00e18-f8df-11eb-9b1a-525400a09b79 | 172.16.130.207 |        3306 | ONLINE       | PRIMARY     | 8.0.25         |
> +---------------------------+--------------------------------------+----------------+-------------+--------------+-------------+----------------+
> 3 rows in set (0.00 sec)