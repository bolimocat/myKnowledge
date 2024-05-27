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
>
### 5 配置文件

[client]
socket = /mgr/mgr_node1.sock

[mysqld]
transaction_write_set_extraction = 'XXHASH64'
binlog_transaction_dependency_tracking = 'WRITESET'
loose-group_replication_group_name = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
loose-group_replication_start_on_boot = off
loose-group_replication_local_address = "172.16.130.207:24901"
loose-group_replication_group_seeds="172.16.130.207:24901,172.16.130.208:24901,172.16.130.209:24901"
loose-group_replication_bootstrap_group=off
loose-group_replication_single_primary_mode= ON
loose-group_replication_enforce_update_everywhere_checks= FALSE
loose-group_replication_recovery_get_public_key= TRUE
loose-group_replication_exit_state_action= READ_ONLY
loose-group_replication_consistency= AFTER
report_host=172.16.130.207
report_port=3306
basedir = /mgr/mysql
datadir = /mgr/data
port = 3306
socket = /mgr/mgr_node1.sock
server_id = 1
general-log-file = /mgr/data/mysqld.log
slow-query-log-file = /mgr/data/mysqld-slow.log
pid-file = /mgr/data/mysqld.pid
log-error = /mgr/data/mysqld.err
enforce_gtid_consistency = on
binlog_ignore_db = dbscale_tmp
binlog_checksum=NONE
innodb_buffer_pool_size = 200m
innodb_doublewrite = 1
innodb_flush_log_at_trx_commit = 1
innodb_flush_method = O_DIRECT
innodb_io_capacity = 800
innodb_io_capacity_max = 1000
gtid_mode = on
innodb_print_all_deadlocks = on
innodb_read_io_threads = 4
innodb_thread_concurrency = 0
innodb_write_io_threads = 4
interactive_timeout = 31536000
log_bin_trust_function_creators = 1
max_connections = 1000000
net_read_timeout = 10000
net_write_timeout = 10000
slow_query_log = on
sort_buffer_size = 2M
sync_binlog = 1
table_definition_cache = 5000
table_open_cache = 5000
wait_timeout = 31536000
lower_case_table_names = 1
skip_slave_start = 1
transaction_isolation = READ-COMMITTED
ssl = off
log_bin=binlog
plugin-load = rpl_semi_sync_master=semisync_master.so;rpl_semi_sync_slave=semisync_slave.so
innodb_log_file_size = 10m
innodb_log_files_in_group = 1
open_files_limit = 1000000
relay_log = mysql-relay
relay_log_info_repository = TABLE
skip_external_locking = 1
skip_name_resolve = 1
slave_parallel_type = LOGICAL_CLOCK
slave_parallel_workers = 16
slave_pending_jobs_size_max = 2147483648
slave_preserve_commit_order = 1
slave_rows_search_algorithms = INDEX_SCAN,HASH_SCAN
thread_cache_size = 3000
init_connect = SET NAMES gbk
back_log = 5000
binlog_format = row
character_set_server = gbk
expire_logs_days = 15
innodb_buffer_pool_instances = 10
innodb_change_buffering = all
innodb_file_per_table = 1
innodb_log_buffer_size = 128M
innodb_temp_data_file_path = ibtmp1:12M:autoextend:max:81920M
log_slave_updates = ON
log_timestamps = SYSTEM
long_query_time = 10
master_info_repository = TABLE
max_allowed_packet = 16M
max_prepared_stmt_count = 1048576
innodb_monitor_enable = all
performance-schema-consumer-events_stages_current = on
performance-schema-consumer-events_waits_current = on
performance-schema-consumer-events_transactions_current = on
performance-schema-consumer-events_statements_current = on
performance-schema-consumer-events_waits_history = on
performance-schema-consumer-events_transactions_history = on
performance-schema-consumer-events_statements_history = on
performance-schema-consumer-events_stages_history = on
performance-schema-instrument = %=ON
federated = 1
default_authentication_plugin = mysql_native_password
#character_set_database = UTF-8


