package cn.java.money.jdbc2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyDataSource extends AbstractMyDataSource {

    //空闲连接
    private final List<ConnectionProxy> idleConnections = new ArrayList<>();
    //活动的连接
    private final List<ConnectionProxy> activeConnections = new ArrayList<>();

    // 最大的正在使用的连接数
    private int poolMaxActiveConnections = 10;
    // 最大空闲连接数
    private int poolMaxIdleConnections = 5;
    // 从连接池中获取一个连接最大等待多少毫秒
    private int poolTimeToWait = 30000;
    //监视器
    private final Object monitor = new Object();

    // 返回的是代理连接
    @Override
    public Connection getConnection() throws SQLException {
        ConnectionProxy connectionProxy = getConnectionProxy();
        return connectionProxy.getProxyConnection();
    }

    public ConnectionProxy getConnectionProxy() throws SQLException {
        boolean wait = false;
        ConnectionProxy connectionProxy = null;
        while (connectionProxy == null) {
            synchronized (monitor) {
                //如果有空闲连接，就获取一个
                if (!idleConnections.isEmpty()) {
                    connectionProxy = idleConnections.remove(0);
                } else {
                    // 如果没有空闲连接。且活动的连接小于最大活动连接数，就创建一个连接
                    if (activeConnections.size() < poolMaxActiveConnections) {
                        connectionProxy = new ConnectionProxy(super.getConnection(), this);
                    }
                    // 否则不能创建连接，需要等待 等poolTimeToWait 毫秒
                }
            }

            if (!wait) {
                wait = true;
            }

            if (connectionProxy == null) {
                try {
                    monitor.wait(poolTimeToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 万一等待被线程打断，退出一下循环
                    break;
                }
            }
        }

        if (connectionProxy != null) {
            activeConnections.add(connectionProxy);
        }
        return connectionProxy;
    }

    //关闭连接把活动的连接放入空闲连接池
    public void closeConnection(ConnectionProxy connectionProxy) {
        synchronized (monitor) {
            activeConnections.remove(connectionProxy);
            if (idleConnections.size() < poolMaxIdleConnections) {
                idleConnections.add(connectionProxy);
            }
            // 通知一下，唤醒上面那个等待获取连接的线程
            monitor.notifyAll();
        }
    }

}
