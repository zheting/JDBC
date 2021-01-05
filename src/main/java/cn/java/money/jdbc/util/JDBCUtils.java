package cn.java.money.jdbc.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/*
 * 操作数据库的工具类
 */
public class JDBCUtils {

	public static Connection getConnection() throws Exception {
		// 1.读取配置文件中的4个基本信息
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

		Properties pros = new Properties();
		pros.load(is);

		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");

		// 2.加载驱动
		Class.forName(driverClass);

		// 3.获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	//数据库连接池只需提供一个即可。
	private static ComboPooledDataSource cpds = new ComboPooledDataSource("hellc3p0");

	public static Connection getConnection1() throws SQLException{
		Connection conn = cpds.getConnection();
		return conn;
	}

	/*
	 * 使用DBCP数据库连接池技术获取数据库连接
	 */
	//创建一个DBCP数据库连接池
	private static DataSource source;
	static{
		try {
			Properties pros = new Properties();
			InputStream is =  ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
			pros.load(is);
			source = BasicDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection2() throws Exception{
		Connection conn = source.getConnection();
		return conn;
	}

	/**
	 * 使用Druid数据库连接池技术
	 */
	private static DataSource source1;
	static{
		try {
			Properties pros = new Properties();
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
			pros.load(is);
			source1 = DruidDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection3() throws SQLException{
		Connection conn = source1.getConnection();
		return conn;
	}

	/*
	 *  关闭连接和Statement的操作
	 */
	public static void closeResource(Connection conn,Statement ps){
		try {
			if(ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 关闭资源操作
	 */
	public static void closeResource(Connection conn,Statement ps,ResultSet rs){
		try {
			if(ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 *使用dbutils.jar中提供的DbUtils工具类，实现资源的关闭
	 */
	public static void closeResource1(Connection conn,Statement ps,ResultSet rs){
		DbUtils.closeQuietly(conn);
		DbUtils.closeQuietly(ps);
		DbUtils.closeQuietly(rs);
	}

	//通用的增删改操作
	public static void update(String sql, Object... args) {//sql中占位符的个数与可变形参的长度相同！
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//1.获取数据库的连接
			conn = JDBCUtils.getConnection();
			//2.预编译sql语句，返回PreparedStatement的实例
			ps = conn.prepareStatement(sql);
			//3.填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);//小心参数声明错误！！
			}
			//4.执行
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//5.资源的关闭
			JDBCUtils.closeResource(conn, ps);
		}
	}
}
