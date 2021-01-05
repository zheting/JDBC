package cn.java.money.jdbc.dao2;

import cn.java.money.jdbc.vo.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/*
 * 此接口用于规范针对于customers表的常用操作
 */
public interface CustomerDAO {

	void insert(Connection conn, Customer cust);
	void deleteById(Connection conn,int id);
	void update(Connection conn,Customer cust);
	Customer getCustomerById(Connection conn,int id);
	List<Customer> getAll(Connection conn);
	Long getCount(Connection conn);
	Date getMaxBirth(Connection conn);
	
}	
