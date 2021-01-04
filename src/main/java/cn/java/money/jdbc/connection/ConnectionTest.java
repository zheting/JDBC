package cn.java.money.jdbc.connection;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionTest {

    @Test
    public void testConnection() throws Exception {

        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=GMT";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }

    @Test
    public void testConnection2() throws Exception {

        // 通过反射获取Driver对象
        Driver driver = (Driver)Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=GMT";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }

    @Test
    public void testConnection3() throws Exception {
        // 通过反射获取Driver对象
        Driver driver = (Driver)Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        // 注册驱动
        DriverManager.registerDriver(driver);

        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=GMT";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        //Connection connection =DriverManager.getConnection(url,info);
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        System.out.println(connection);
    }

    @Test
    public void testConnection4() throws Exception {
        /* 加载驱动，其实就是加载类com.mysql.cj.jdbc.Driver，
         加载类就会执行静态代码块 DriverManager.registerDriver(new Driver()); 完成驱动注册，因此可以把注册驱动一步省略掉
         */
        // mysql-connector-java-8.0.22.jar!\META-INF\services\java.sql.Driver
        // 文件中配置了驱动 com.mysql.cj.jdbc.Driver， 因此加载驱动也可以省略，但是仅限mysql
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=GMT";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        System.out.println(connection);
    }

    @Test
    public void testConnection5() throws Exception {
        //InputStream inputStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        Class.forName(properties.getProperty("driverClass"));
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

}
