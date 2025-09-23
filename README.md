# APACHE KAFKA TUTORIAL

---

## KRAFT MODE

### 1. Creating Cluster
```bash
bin\windows\kafka-storage.bat random-uuid
```
Example:
```
egHLL2idT4677iTIooCEaw
```

### 2. Formatting Data
```bash
bin\windows\kafka-storage.bat format -t egHLL2idT4677iTIooCEaw -c .\config\kraft\server.properties
```
Output:
```
Formatting metadata directory C:/kafka/tmp/kraft-combined-logs with metadata.version 3.9-IV0.
```

### 3. Start Server
```bash
bin\windows\kafka-server-start.bat .\config\kraft\server.properties
```

### 4. Create Kafka Topic
```bash
bin\windows\kafka-topics.bat --create --topic test --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### 5. Producer Console
```bash
bin\windows\kafka-console-producer.bat --topic test --bootstrap-server localhost:9092
```

### 6. Consumer Console
```bash
bin\windows\kafka-console-consumer.bat --topic test --from-beginning --bootstrap-server localhost:9092
```

---

## ZOOKEEPER MODE

### 1. Start ZooKeeper
```bash
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

### 2. Broker Configurations

**server-0.properties**
```properties
broker.id=0
listeners=PLAINTEXT://0.0.0.0:9092
advertised.listeners=PLAINTEXT://localhost:9092
log.dirs=C:/kafka/tmp/kafka-logs-0
num.partitions=1
zookeeper.connect=localhost:2181
```

**server-1.properties**
```properties
broker.id=1
listeners=PLAINTEXT://0.0.0.0:9093
advertised.listeners=PLAINTEXT://localhost:9093
log.dirs=C:/kafka/tmp/kafka-logs-1
num.partitions=1
zookeeper.connect=localhost:2181
```

**server-2.properties**
```properties
broker.id=2
listeners=PLAINTEXT://0.0.0.0:9094
advertised.listeners=PLAINTEXT://localhost:9094
log.dirs=C:/kafka/tmp/kafka-logs-2
num.partitions=1
zookeeper.connect=localhost:2181
```

### 3. Start Brokers
```bash
.\bin\windows\kafka-server-start.bat .\config\server-0.properties
.\bin\windows\kafka-server-start.bat .\config\server-1.properties
.\bin\windows\kafka-server-start.bat .\config\server-2.properties
```

### 4. Verify Brokers
```bash
.\bin\windows\zookeeper-shell.bat localhost:2181 ls /brokers/ids
```
Output:
```
[0,1,2]
```

### 5. Client View (Optional)
```bash
.\bin\windows\kafka-broker-api-versions.bat --bootstrap-server localhost:9092
```

### 6. Create Topic
```bash
.\bin\windows\kafka-topics.bat --create --topic my-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 3
```

### 7. Describe Topic
```bash
.\bin\windows\kafka-topics.bat --describe --bootstrap-server localhost:9092 --topic my-topic
```

### 8. Test Producer / Consumer

**Producer**
```bash
.\bin\windows\kafka-console-producer.bat --topic my-topic --bootstrap-server localhost:9092
```

**Consumer**
```bash
.\bin\windows\kafka-console-consumer.bat --topic my-topic --bootstrap-server localhost:9092 --from-beginning
```
