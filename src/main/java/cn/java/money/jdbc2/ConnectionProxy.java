package cn.java.money.jdbc2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

//采用动态代理对 Connection 进行代理
public class ConnectionProxy implements InvocationHandler {

    private Connection realConnection;
    private Connection proxyConnection;
    private MyDataSource myDataSource;

    //初始化属性，和 代理对象
    public ConnectionProxy(Connection realConnection, MyDataSource myDataSource) {
        this.realConnection = realConnection;
        this.myDataSource = myDataSource;
        //初始化代理对象
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class<?>[]{Connection.class}, this);
    }

    /**
     * 当调用Connection对象里面的方法是，首先会调用invoke方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        //调用close,把连接还给连接池， 其他方法调用被代理对象的方法
        if (methodName.equals("close")) {
            myDataSource.closeConnection(this);
            return null;
        }else {
            return  method.invoke(realConnection, args);
        }
    }

    public Connection getRealConnection() {
        return realConnection;
    }

    public void setRealConnection(Connection realConnection) {
        this.realConnection = realConnection;
    }

    public Connection getProxyConnection() {
        return proxyConnection;
    }

    public void setProxyConnection(Connection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    public MyDataSource getMyDataSource() {
        return myDataSource;
    }

    public void setMyDataSource(MyDataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

}
