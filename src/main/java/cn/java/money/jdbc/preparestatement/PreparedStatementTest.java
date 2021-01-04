package cn.java.money.jdbc.preparestatement;

import cn.java.money.jdbc.vo.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/*
 * 除了解决Statement的拼串、sql问题之外，PreparedStatement还有哪些好处呢？
 * 1.PreparedStatement操作Blob的数据，而Statement做不到。
 * 2.PreparedStatement可以实现更高效的批量操作。
 *
 */
public class PreparedStatementTest {

	@Test
	public void testLogin() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入用户名：");
		String user = scanner.nextLine();
		System.out.print("请输入密码：");
		String password = scanner.nextLine();
		//SELECT user,password FROM user_table WHERE user = '1' or ' AND password = '=1 or '1' = '1'
		String sql = "SELECT user,password FROM user_table WHERE user = ? and password = ?";  // 因为占位符可以预编译，就是确定sql结构，传入的参数就是一个值不会影响sql的结构。
		User returnUser = getInstance(User.class,sql,user,password);
		if(returnUser != null){
			System.out.println("登录成功");
		}else{
			System.out.println("用户名不存在或密码错误");
		}
	}
	
	/*
	 * 针对于不同的表的通用的查询操作，返回表中的一条记录
	 */
	public <T> T getInstance(Class<T> clazz,String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = JDBCUtils.getConnection();
			conn = null;
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			rs = ps.executeQuery();
			// 获取结果集的元数据 :ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			// 通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();

			if (rs.next()) {
				T t = clazz.newInstance();
				// 处理结果集一行数据中的每一个列
				for (int i = 0; i < columnCount; i++) {
					// 获取列值
					Object columValue = rs.getObject(i + 1);
					// 获取每个列的列名
					// String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);
					// 给t对象指定的columnName属性，赋值为columValue：通过反射
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columValue);
				}
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
}
