# JDBC
## JDBC所在的包
java.sql.*   
javax.sql.*

## JDBC 简介
JDBC(Java Database Connect)

JDBC 是一套规范，各个数据库厂商提供的Driver是对JDBC规范的实现。每个数据库厂商才能知道各自的实现细节。

## JDBC 接口
java.sql.Driver   
java.sql.Connection  
java.sql.Statement   
java.sql.ResultSet


## JDBC 步骤
第一步：加载驱动

Class.forName("com.mysql.cj.jdbc.Driver");   
>实际执行的是实例化Driver并注册  
DriverManager.registerDriver(new Driver());

第二步：获取连接
 
需要提供连接数据的url,用户名，密码 

url:  jdbc(协议):mysql(自协议)://主机名或ip地址:端口号/数据库名称?key1=value1&key2&value2
    
和HTML类似：        http(协议)://主机名或ip地址:端口号/contextpath?param1=value1&param2=value2

Connection connection = DriverManager.getConnection(url, user, password);

第三步：获取Statement 或 PreparedStatement

Statement statement = connection.createStatement();
sql是拼接的，没有预编译

PreparedStatement preparedStatement = connection.prepareStatement(sql);   
preparedStatement设置占位符的值，是从1开始。
 
 
第四步：执行CRUD
- Statement   
   - int executeUpdate(String sql)
   - ResultSet executeQuery(String sql) throws SQLException;
   
 - PreparedStatement
    - int executeUpdate() throws SQLException;
    - ResultSet executeQuery() throws SQLException;

第五步：处理 ResultSet


## Statement vs PreparedStatement
1.0 PreparedStatement防止sql注入

2.0 PreparedStatement操作blob

3.0 批量插入性能高

##事务
### 事务的ACID属性

原子性（Atomicity） 原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。

一致性（Consistency） 事务必须使数据库从一个一致性状态变换到另外一个一致性状态。

隔离性（Isolation） 事务的隔离性是指一个事务的执行不能被其他事务干扰，即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。

持久性（Durability） 持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来的其他操作和数据库故障不应该对其有任何影响。

### 数据库的并发问题

对于同时运行的多个事务, 当这些事务访问数据库中相同的数据时, 如果没有采取必要的隔离机制, 就会导致各种并发问题:

脏读: 对于两个事务 T1, T2, T1 读取了已经被 T2 更新但还没有被提交的字段。之后, 若 T2 回滚, T1读取的内容就是临时且无效的。

不可重复读: 对于两个事务T1, T2, T1 读取了一个字段, 然后 T2 更新了该字段。之后, T1再次读取同一个字段, 值就不同了。

幻读: 对于两个事务T1, T2, T1 从一个表中读取了一个字段, 然后 T2 在该表中插入了一些新的行。之后, 如果 T1 再次读取同一个表, 就会多出几行。

数据库事务的隔离性: 数据库系统必须具有隔离并发运行各个事务的能力, 使它们不会相互影响, 避免各种并发问题。

一个事务与其他事务隔离的程度称为隔离级别。数据库规定了多种事务隔离级别, 不同隔离级别对应不同的干扰程度, 隔离级别越高, 数据一致性就越好, 但并发性越弱。

### 四种隔离级别


## 数据库连接池

####C3P0

https://www.mchange.com/projects/c3p0/

 https://github.com/swaldman/c3p0
 
####DBCP
http://commons.apache.org/proper/commons-dbcp/

####druid
https://druid.apache.org/docs/latest/design/index.html
https://github.com/apache/druid
https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98


#### DbUtils
http://commons.apache.org/proper/commons-dbutils/