# Kafka简单部署及快速使用

#### 1 解压tar，创建使用脚本。

将解压内容放入/usr/local/kafka

```
mv kafka_2.13-2.7.1 /usr/local/kafka
```

修改配置文件

```
cd /usr/local/kafka/config/
cp server.properties{,.bak}
vim server.properties
35：listeners=PLAINTEXT://192.168.2.25:9092
73：log.dirs=/usr/local/kafka/logs
136：zookeeper.connect=192.168.2.25:2181 【默认有可用的zk服务器】
```

编写启动的sh脚本

vim kafka-service.sh 【直接复制下面的内容】

```
#!/bin/bash
KAFKA_HOME=/usr/local/kafka

case $1 in
start)
	echo "------------------Kafka 启动 --------------------"
	$KAFKA_HOME/bin/kafka-server-start.sh -daemon $KAFKA_HOME/config/server.properties
;;
stop)
	echo "------------------Kafka 停止 --------------------"
	$KAFKA_HOME/bin/kafka-server-stop.sh
;;
restart)
	$0 stop
	$0 start
;;
status)
	echo " ------------- Kafka 状态 ---------------"
	count=$(ps -ef | grep kafka | egrep -cv "grep|$$")
	if [ "$count" -eq 0 ];then
		echo "kafka is not running .. .."
	else
		echo "kafka is running .. .."
	fi
;;
*)
	echo "Usage: $0 {start|stop|restart|status}"
esac

```



#### 2  Kafka基本使用

没有配置环境变量的话，需要在/usr/local/kafka/bin下执行;

##### a 创建topic

```
kafka-topics.sh --create --zookeeper 192.168.2.25:2181 --replication-factor 2 --partitions 3 --topic mytest

--zookeeper					定义 zookeeper 集群服务器地址，如果有多个 IP 地址使用逗号分割，一般使用一个 IP 即可
--replication-factor		定义分区副本数，1 代表单副本，建议为 2 
--partitions				定义分区数 
--topic						定义 topic 名称

```

##### b 查看当前服务器中的topic

```
kafka-topics.sh --list --zookeeper 192.168.2.25:2181
```

##### c 查看某个topic的详情

```
kafka-topics.sh --describe --zookeeper 192.168.2.25:2181
```

##### d 发布消息

```
kafka-console-producer.sh --broker-list 192.168.2.25:9092 --topic mytest
```

##### e 接收消息

```
kafka-console-consumer.sh --bootstrap-server 192.168.2.25:9092 --topic mytest --from-beginning
```

##### f 删除topic

```
kafka-topics.sh --delete --zookeeper 192.168.2.25:2181 --topic mytest
```

