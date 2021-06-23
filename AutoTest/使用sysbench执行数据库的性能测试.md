# 使用sysbench执行数据库的性能测试

### 1 获取版本

网上有很多获取sysbench的方法，最简单的是从github上clone官方最新版本。

```
git clone https://github.com/akopytov/sysbench.git
```

或者直接到：https://github.com/akopytov/sysbench 下载zip包到本地。



### 2 编译安装

首先确保系统中（以下默认环境为centos7）已经安装了"automake"和“libtool”。

进入源码目录，目录中有autogen.sh，执行./autogen.sh，在当前目录生成configure等相关文件。

执行编译安装：

```
configure --prefix=指定目录 && make && make install
```



### 3 使用

完成安装后，在目录里有bin和share两个目录，bin里是执行程序，share里是脚本。

执行数据库性能测试，首先需要在被测数据库里创建相应的信息，例如先创建一个库：

```
create database sysbench_test;
```

然后选择share中的一个合适的脚本，顺序执行prepare，run，cleanup等操作，其中run之后要记录本次测试结果。

#### a:准备数据

```
./sysbench /home/lming/sysbench/share/sysbench/oltp_update_index.lua \
--mysql-host=172.16.130.205 \
--mysql-port=16310 \
--mysql-user=root \
--mysql-password='!QAZ2wsx' \
--mysql-db=sysbench_tpcc \
--db-driver=mysql \
--tables=32 \
--table-size=50000 \
--report-interval=10 \
--threads=32 \
--time=300 prepare
每个选项的意义一目了然。
```

#### b：执行测试

```
./sysbench /home/lming/sysbench/share/sysbench/oltp_update_index.lua \
--mysql-host=172.16.130.205 \
--mysql-port=16310 \
--mysql-user=root \
--mysql-password='!QAZ2wsx' \
--mysql-db=sysbench_tpcc \
--db-driver=mysql \
--tables=32 \
--table-size=50000 \
--report-interval=10 \
--threads=32 \
--time=300 run
```

#### c：收集结果

> [ 240s ] thds: 32 tps: 946.66 qps: 946.66 (r/w/o: 0.00/946.66/0.00) lat (ms,95%): 69.29 err/s: 0.00 reconn/s: 0.00
> [ 250s ] thds: 32 tps: 772.99 qps: 772.99 (r/w/o: 0.00/772.99/0.00) lat (ms,95%): 73.13 err/s: 0.00 reconn/s: 0.00
> [ 260s ] thds: 32 tps: 1048.48 qps: 1048.48 (r/w/o: 0.00/1048.48/0.00) lat (ms,95%): 42.61 err/s: 0.00 reconn/s: 0.00
> [ 270s ] thds: 32 tps: 972.49 qps: 972.49 (r/w/o: 0.00/972.49/0.00) lat (ms,95%): 45.79 err/s: 0.00 reconn/s: 0.00
> [ 280s ] thds: 32 tps: 758.06 qps: 758.06 (r/w/o: 0.00/758.06/0.00) lat (ms,95%): 106.75 err/s: 0.00 reconn/s: 0.00
> [ 290s ] thds: 32 tps: 896.12 qps: 896.12 (r/w/o: 0.00/896.12/0.00) lat (ms,95%): 62.19 err/s: 0.00 reconn/s: 0.00
> [ 300s ] thds: 32 tps: 962.57 qps: 962.57 (r/w/o: 0.00/962.57/0.00) lat (ms,95%): 49.21 err/s: 0.00 reconn/s: 0.00
> SQL statistics:
>
>     queries performed:
>         read:                            0
>         write:                           300537
>         other:                           0
>         total:                           300537
>     transactions:                        300537 (1001.66 per sec.)
>     queries:                             300537 (1001.66 per sec.)
>     ignored errors:                      0      (0.00 per sec.)
>     reconnects:                          0      (0.00 per sec.)
>
> Throughput:
>     events/s (eps):                      1001.6577
>     time elapsed:                        300.0396s
>     total number of events:              300537
>
> Latency (ms):
>          min:                                    5.18
>          avg:                                   31.94
>          max:                                  738.62
>          95th percentile:                       49.21
>          sum:                              9599980.48
>
> Threads fairness:
>     events (avg/stddev):           9391.7812/41.27
>     execution time (avg/stddev):   299.9994/0.01

#### d：清理测试数据

```
./sysbench /home/lming/sysbench/share/sysbench/oltp_update_index.lua \
--mysql-host=172.16.130.205 \
--mysql-port=16310 \
--mysql-user=root \
--mysql-password='!QAZ2wsx' \
--mysql-db=sysbench_tpcc \
--db-driver=mysql \
--tables=32 \
--table-size=50000 \
--report-interval=10 \
--threads=32 \
--time=300 cleanup
完成清理，重启实例，执行下一轮测试。
```



### 4 关于lua脚本的说明

lua是执行数据库测试时的具体脚本，在/share/sysbench中，根据文件名可以大致了解脚本的作用。

我们可以根据例子，简单编辑自己想要的lua脚本：创建文件test.lua

```
 #!/usr/bin/env sysbench

function event()

db_query(

"SELECT 1"

)
end 
```

只做一个查询操作，执行时要给test.lua可执行权限。

更多情况，我们随便打开一个官方提供的lua文件，可以看到一个测试lua分为以下几个部分：

#### a 文件头

> #!/usr/bin/env sysbench
> -- -------------------------------------------------------------------------- --
> -- Bulk insert benchmark: do multi-row INSERTs concurrently in --threads
> -- threads with each thread inserting into its own table. The number of INSERTs
> -- executed by each thread is controlled by either --time or --events.
> -- -------------------------------------------------------------------------- --

#### b 变量声明

> cursize=0

#### c 连接数据库

> function thread_init()
>    drv = sysbench.sql.driver()
>    con = drv:connect()
> end

推测这里的信息是从执行sysbench的时候，由参数传递的。

#### d 执行测试

##### 	i 准备数据

> function prepare()
>    local i
>
>    local drv = sysbench.sql.driver()
>    local con = drv:connect()
>
>    for i = 1, sysbench.opt.threads do
>       print("Creating table 'sbtest" .. i .. "'...")
>       con:query(string.format([[
>         CREATE TABLE IF NOT EXISTS sbtest%d (
>           id INTEGER NOT NULL,
>           k INTEGER DEFAULT '0' NOT NULL,
>           PRIMARY KEY (id))]], i))
>    end
> end

其中包含一些变量定义，循环控制的方法。

##### 	ii 执行测试

> function event()
>    if (cursize == 0) then
>
>       con:bulk_insert_init("INSERT INTO sbtest" .. sysbench.tid+1 .. " VALUES")
>    end
>
>    cursize = cursize + 1
>
>    con:bulk_insert_next("(" .. cursize .. "," .. cursize .. ")")
> end

#### e 结束线程

> function thread_done()
>    con:bulk_insert_done()
>    con:disconnect()
> end

#### f 清理环境

> function cleanup()
>    local i
>
>    local drv = sysbench.sql.driver()
>    local con = drv:connect()
>
>    for i = 1, sysbench.opt.threads do
>       print("Dropping table 'sbtest" .. i .. "'...")
>       con:query("DROP TABLE IF EXISTS sbtest" .. i )
>    end
> end

最后保存文件，授予执行权限，通过sysbench执行对数据库的测试。