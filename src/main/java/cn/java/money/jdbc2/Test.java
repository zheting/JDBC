package cn.java.money.jdbc2;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws SQLException {
        MyDataSource myDataSource = new MyDataSource();
        myDataSource.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=GMT&rewriteBatchedStatements=true");
        myDataSource.setUserName("root");
        myDataSource.setPassword("123456");
        Connection connection = myDataSource.getConnection();
        System.out.println(connection);

    }
}
