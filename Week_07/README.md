## 本次作业

- [插入100w条数据测试](https://github.com/realDW/JAVA-01/tree/main/Week_07/thinking-in-jdbc/src/main/java/io/daiwei/insert)
- [读写分离-动态切换数据源版本1.0](https://github.com/realDW/JAVA-01/tree/main/Week_07/thinking-in-jdbc/src/main/java/io/daiwei/proxy)
- [读写分离-数据库框架版本2.0](https://github.com/realDW/JAVA-01/tree/main/Week_07/thinking-in-multi-datasource/src)

---

## 100w 数据插入性能测试

### 前言

这是学习中遇到的一个题目，往一个表中插入100w条数据，看到这个题目的时候人是懵逼的。因为目前公司线上最大的表记录不过100w出头，每次做数据清洗或者数据迁移的时候一个通宵都不一定能跑完，那么插入100w 又怎么会快的起来呢？更何况老师问是否能把性能优化到 10s 之内呢。。

### 思维爆炸

只要思想不滑坡，办法总比方法多。老师既然问了那这个问题肯定是有解的，如果脑暴分析下可以得到下面的一些信息线索。

- **数据库优化主要分以下三部分**
  - **数据库端参数优化**
  - **sql 优化**
  - **表结构优化**
- 影响数据库吞吐量的因素
  - 单个事务大小
  - redolog写入情况和脏页数量
  - 数据库连接池空闲情况和一些相关的buffer
  - 索引
  - 数据库实例性能

- java 代码的性能因素
  - 封装越小 速度越快
  - 多线程
  - 数据库连接池
- 运行环境因素
  - cpu
  - io
  - system

以上这些是我能想到的一些影响的方面，和可以针对优化的点。本次测试基本上都在我自己的 mbp 上跑，所以**运行环境这个条件变量基本是控制不变**的。

根据上面的一些因素可以得到一些可行的操作方案

1. 数据库连接工具执行批量sql
2. 基本的循环插入
3. 拼接批量 sql
4. 使用preparedStatement + queryBatch
5. 多线程提升性能

### 走两步试试

#### 数据库环境

>docker 默认配置 + mysql5.7

```zsh
docker pull mysql:5.7
docker run --name mysql-test -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7
```

#### 表结构

```sql
create table tb_order_test_1(
	`id` int auto_increment,
	`good_id` int not null default '0',
	`user_id` int not null default '0', 
	`good_status` tinyint not null default '1',
	`username` varchar(16) not null default '',
	primary key(id)
) engine=innodb
```

#### 数据库连接工具执行批量sql

从数据库中反向导出数据

```sql
LOCK TABLES `tb_order_test_1` WRITE;
/*!40000 ALTER TABLE `tb_order_test_1` DISABLE KEYS */;

INSERT INTO `tb_order_test_1` (`id`, `good_id`, `user_id`, `good_status`, `username`)
VALUES
	(1,0,0,1,'daiwei'),
	(2,1,1,1,'daiwei'),
	(3,2,2,1,'daiwei'),
	(4,3,3,1,'daiwei'),
	(5,4,4,1,'daiwei'),
	..... 100w .....
	(999997,999996,999996,1,'daiwei'),
	(999998,999997,999997,1,'daiwei'),
	(999999,999998,999998,1,'daiwei'),
	(1000000,999999,999999,1,'daiwei');

/*!40000 ALTER TABLE `tb_order_test_1` ENABLE KEYS */;
UNLOCK TABLES;
```

这里导出数据可以发现两个问题，首先sql 的存储的文件是批量插入的，其次在操作的过程中，表是锁定状态的。

最终手动🙈掐表测100w 的数据导入大概需要 7.5 秒。

#### 基础的循环操作插入操作

这个最基本最简单的循环插入，明显是不可能会有较高的插入性能的，但是我就是想知道最慢着要多久🤤。这最基本的就不用那些ORM 框架了就直接使用 jdbc 操作了，封装越高速度也就越慢。同时稍微控制下事务，这种不考虑大事务的前提下，减少事务提交，一次提交。大量数据的插入会影响到数据页导致页分裂，也会拖慢整体的时间，所以整个测试过程中，不额外创建索引。

```java
public void insertByLoop() {
  	long start = System.currentTimeMillis();
  	Connection conn = JdbcUtil.getConnFromHikari();
  	PreparedStatement stat = null;
  	try {
      	String sql = "insert into tb_order_test_1(good_id, user_id, good_status, username) values (?, ?, ?, ?)";
      	conn.setAutoCommit(false);
      	stat = conn.prepareStatement(sql);
      	for (int i = 0; i < 3000; i++) {
          	stat.setLong(1, i);
          	stat.setLong(2, i);
          	stat.setInt(3, 1);
          	stat.setString(4, "daiwei");
          	stat.execute();
        }
      	conn.commit();
    } catch (SQLException throwables) {
      	throwables.printStackTrace();
    }finally {
      	JdbcUtil.release(conn, stat, null);
    }
  	System.out.println(System.currentTimeMillis() - start);
}
```

1w条数据插入耗时 24015 ms，插入的数据量和插入时间是线性相关的，所以就不过多的浪费时间了。。。🤨

#### jdbc batch 批处理

这个版本的操作相较于上面的一种，最大的提升就是用了批处理操作。在批量插入上性能能有一定的提升，但是提升效果不是很大。1w 数据量 11358ms。**但是这里有一个参数rewriteBatchedStatements=true**，加上这个参数允许将批量处理的 sql 进行重写，来提高批处理性能，相当于魔改了。。。

```java
public void insertSqlBatch() {
  	long start = System.currentTimeMillis();
  	Connection conn = JdbcUtil.getConnFromHikari();
  	PreparedStatement stat = null;
  	try {
      	String sql = "insert into tb_order_test_1(good_id, user_id, good_status, username) values (?, ?, ?, ?)";
      	conn.setAutoCommit(false);
      	stat = conn.prepareStatement(sql);
      	for (int i = 0; i < 50000; i++) {
          	stat.setLong(1, i);
          	stat.setLong(2, i);
          	stat.setInt(3, 1);
          	stat.setString(4, "daiwei");
          	stat.addBatch();
        }
      	stat.executeBatch();
      	conn.commit();
    	} catch (SQLException throwables) {
      	throwables.printStackTrace();
    	} finally {
      	JdbcUtil.release(conn, stat, null);
    	}
  	System.out.println(System.currentTimeMillis() - start);
}
```

```properties
jdbc.url=jdbc:mysql://127.0.0.1:3306/test?rewriteBatchedStatements=true
```

添加参数之后，batch insert 的性能有极大的提高。100w 的数据量 8775ms 就操作完成。

#### 拼接sql + 批处理

在不开启rewriteBatchedStatements = true 的情况下，如果我自己手动拼接sql 减少 jdbc 的封装，速度是否能在往上提升呢？

```java
public void insertByBatch() {
  	Connection conn = JdbcUtil.getConnFromHikari();
    Statement stat = null;
    long start = System.currentTimeMillis();
    try {
      	stat = conn.createStatement();
      	String sql;
      	StringBuilder sb = new StringBuilder("insert into tb_order_test_1(good_id, user_id, good_status, username) values");
      	conn.setAutoCommit(false);
      	for (int i = 0; i < 1000; i++) {
          	for (int j = 0; j < 1000; j++) {
              	sb.append("(" + i +" , " + j +", 1 , 'daiwei'),");
            }
          	sql = sb.deleteCharAt(sb.length() - 1).toString();
          	stat.addBatch(sql);
          	sb.delete(75, sb.length());
        }
      	stat.executeBatch();
      	conn.commit();
      	System.out.println(System.currentTimeMillis() - start);
    } catch (SQLException throwables) {
      	throwables.printStackTrace();
    } finally {
      	JdbcUtil.release(conn, stat, null);
    }
}
```

这个方案跑下来，优化效果还是比较明显的，100w的数据插入的时间大概在 5s ～6s 这个区间。

这个方案的小缺点是事务分开的整体的数据插入不是原子性的。

#### 多线程 + 拼接sql + 批处理

单线程跑下来性能都已经提升这么明显了，那多线程。。。

```java
public void insertConcurBatch() {
  	ExecutorService executor = Executors.newFixedThreadPool(4);
  	long start = System.currentTimeMillis();
  	CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
      	System.out.println(System.currentTimeMillis() - start);
      	executor.shutdown();
    });
  	for (int i = 0; i < 4; i++) {
      	executor.execute(() -> {
          	insertByBatch(250);
          	try {
              	cyclicBarrier.await();
            } catch (InterruptedException e) {
              	e.printStackTrace();
            } catch (BrokenBarrierException e) {
              	e.printStackTrace();
            }
        });
    }
}

public void insertByBatch(int n) {
  	Connection conn = JdbcUtil.getConnFromHikari();
    Statement stat = null;
    long start = System.currentTimeMillis();
    try {
      	stat = conn.createStatement();
      	String sql;
      	StringBuilder sb = new StringBuilder("insert into tb_order_test_1(good_id, user_id, good_status, username) values");
      	conn.setAutoCommit(false);
      	for (int i = 0; i < n; i++) {
          	for (int j = 0; j < 1000; j++) {
              	sb.append("(" + i +" , " + j +", 1 , 'daiwei'),");
            }
          	sql = sb.deleteCharAt(sb.length() - 1).toString();
          	stat.addBatch(sql);
          	sb.delete(75, sb.length());
        }
      	stat.executeBatch();
      	conn.commit();
      	System.out.println(System.currentTimeMillis() - start);
    } catch (SQLException throwables) {
      	throwables.printStackTrace();
    } finally {
      	JdbcUtil.release(conn, stat, null);
    }
}
```

果然发挥多线程的性能优势，整体性能优能往上提升一点点。

### 泥巴路上走一走

实际生产环境的话，并不会有几个表只有 几个字段，多是字段20+ ，所以如果字段多上去，插入性能是否会受到影响？如此便有了下面的测试

```sql
CREATE TABLE `tb_order_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `good_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `good_status` tinyint(4) NOT NULL DEFAULT '1',
  `field_4` varchar(16) NOT NULL,
  `field_5` varchar(16) NOT NULL,
  `field_6` varchar(16) NOT NULL,
  `field_7` varchar(16) NOT NULL,
  `field_8` varchar(16) NOT NULL,
  `field_9` varchar(16) NOT NULL,
  `field_10` varchar(16) NOT NULL,
  `field_11` varchar(16) NOT NULL,
  `field_12` varchar(16) NOT NULL,
  `field_13` varchar(16) NOT NULL,
  `field_14` varchar(16) NOT NULL,
  `field_15` varchar(16) NOT NULL,
  `field_16` varchar(16) NOT NULL,
  `field_17` varchar(16) NOT NULL,
  `field_18` varchar(16) NOT NULL,
  `field_19` varchar(16) NOT NULL,
  `field_20` varchar(16) NOT NULL,
  `field_21` varchar(16) NOT NULL,
  `field_22` varchar(16) NOT NULL,
  `field_23` varchar(16) NOT NULL,
  `field_24` varchar(16) NOT NULL,
  `field_25` varchar(16) NOT NULL,
  `field_26` varchar(16) NOT NULL,
  `field_27` varchar(16) NOT NULL,
  `field_28` varchar(16) NOT NULL,
  `field_29` varchar(16) NOT NULL,
  `field_30` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5214001 DEFAULT CHARSET=latin1;
```

测试代码的话这里用的还是 多线程 + 拼接sql + 批处理 的 pro 版本，果然这次的测试的速度有很明显的下降 100w 的插入时间 到了 19866ms，我的电脑是 8 核的，本着充分利用系统资源和数据库并发资源的原则。最终的 100w 时间到了 18080ms。

### 总结

在这个场景中有很多的环境因素：mysql数据库、sql语句和 java代码等多个方面，其中的任何一个部分出现瓶颈，都会拖累整体的性能，但是如果换个角度去看这个问题。如果我们每个部分的性能都能充分发挥整体的性能也相应提高。本次的优化过程从 sql部分，编码技巧到表结构优化去尽可能提升100w数据的插入速度。这里漏了一个角度，就是调整mysql 的参数去优化，在mysql 写入过程中有 WAL 机制，如果这里调大 redolog buf 尽可能少的减少 redolog 的 刷脏页操作，是否也能提升写入的性能呢？🤔，最后下面是本次测试结果表格

| 插入方式                                      | 1w      | 10w    | 100W   |
| --------------------------------------------- | ------- | ------ | ------ |
| 数据库工具导入                                | -       | -      | 7500ms |
| 单个事务循环插入                              | 24015ms | -      | -      |
| 单个事务batch批处理                           | 11358ms | -      | -      |
| 魔改 batch <br />（rewriteBatchedStatements） | 689ms   | 1562ms | 8775ms |
| 拼接sql + 批处理                              | 105ms   | 992ms  | 5302ms |
| 多线程 + 拼接sql + 批处理                     | -       | -      | 4314ms |

