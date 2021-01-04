package cn.java.money.jdbc.vo;

import java.sql.Date;

/*
CREATE TABLE `order` (
  `order_id` int(10) NOT NULL AUTO_INCREMENT,
  `order_name` varchar(20) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  PRIMARY KEY (`order_id`)
);

insert  into `order`(`order_id`,`order_name`,`order_date`) values (1,'AA','2010-03-04'),(2,'BB','2000-02-01'),(4,'GG','1994-06-28');

 */
public class Order {
	private int orderId;
	private String orderName;
	private Date orderDate;
	
	
	public Order() {
		super();
	}
	public Order(int orderId, String orderName, Date orderDate) {
		super();
		this.orderId = orderId;
		this.orderName = orderName;
		this.orderDate = orderDate;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderName=" + orderName + ", orderDate=" + orderDate + "]";
	}
	
	
}
