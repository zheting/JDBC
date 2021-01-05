package cn.java.money.jdbc.transaction;

import cn.java.money.jdbc.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;


public class ConnectionTest {
	
	@Test
	public void testGetConnection() throws Exception{
		Connection conn = JDBCUtils.getConnection();
		System.out.println(conn);
	}
}
