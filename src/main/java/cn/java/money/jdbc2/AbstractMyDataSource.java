package cn.java.money.jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractMyDataSource implements MyDataSourceInterFace {

    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(userName, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return doGetConnection(username, password);
    }

    private Connection doGetConnection(String userName, String password) throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

}
