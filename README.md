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
