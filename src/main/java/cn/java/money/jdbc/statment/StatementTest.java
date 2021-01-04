package cn.java.money.jdbc.statment;


import cn.java.money.jdbc.vo.User;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/*
表结构：

 CREATE TABLE `user_table` (
  `user` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `balance` int(20) DEFAULT NULL
);
insert  into `user_table`(`user`,`password`,`balance`) values ('AA','123456',1000),('BB','654321',1000),('CC','abcd',2000),('DD','abcder',3000);
commit;
 */
public class StatementTest {

	// 使用Statement的弊端：需要拼写sql语句，并且存在SQL注入的问题
	//如何避免出现sql注入：只要用 PreparedStatement(从Statement扩展而来) 取代 Statement
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("请输入用户名：");
		String user = scanner.nextLine();
		System.out.print("请输入密码：");
		String password = scanner.nextLine();
		/*
			SELECT user,password FROM user_table WHERE user = '1' or ' AND password = '=1 or '1' = '1'
			sql 注入： user 输入为 1' or
					  password 输入为 =1 or '1' = '1
			 其实就是把 and 条件变为一个字符串 并转换为 or 1=1
		*/

		String sql = "SELECT user,password FROM user_table WHERE user = '"+ user +"' AND password = '"+ password +"'";
		User returnUser = get(sql,User.class);
		if(returnUser != null){
			System.out.println("登录成功");
		}else{
			System.out.println("用户名不存在或密码错误");
		}
	}

	// 使用Statement实现对数据表的查询操作
	public static  <T> T get(String sql, Class<T> clazz) {
		T t = null;

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 1.加载配置文件
			InputStream is = StatementTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties pros = new Properties();
			pros.load(is);

			// 2.读取配置信息
			String user = pros.getProperty("user");
			String password = pros.getProperty("password");
			String url = pros.getProperty("url");
			String driverClass = pros.getProperty("driver");

			// 3.加载驱动
			Class.forName(driverClass);

			// 4.获取连接
			conn = DriverManager.getConnection(url, user, password);

			st = conn.createStatement();

			rs = st.executeQuery(sql);

			// 获取结果集的元数据
			ResultSetMetaData rsmd = rs.getMetaData();

			// 获取结果集的列数
			int columnCount = rsmd.getColumnCount();

			if (rs.next()) {

				t = clazz.newInstance();

				for (int i = 0; i < columnCount; i++) {
					// //1. 获取列的名称
					// String columnName = rsmd.getColumnName(i+1);

					// 1. 获取列的别名
					String columnName = rsmd.getColumnLabel(i + 1);

					// 2. 根据列名获取对应数据表中的数据
					Object columnVal = rs.getObject(columnName);

					// 3. 将数据表中得到的数据，封装进对象
					Field field = clazz.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(t, columnVal);
				}
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

}
