package edu.gdpu.spring.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author mazebin
 * @date 2021年 03月21日 18:17:59
 * 连接的管理类，它用于从数据源中获取一个连接，并且实现和线程的绑定
 */

public class ConnectionManager {
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    //dataSource设置默认值
    private static DataSource dataSource ;


    public static   void setDataSource(DataSource dataSource) {
        ConnectionManager.dataSource = dataSource;
    }
    /**
     * 获取当前线程的链接
     *
     * @return
     */
    public  static  Connection getLocalConnection(){
        try {
            Connection connection = connectionThreadLocal.get();
            if(connection == null){
                connection = dataSource.getConnection();
                connectionThreadLocal.set(connection);
            }
            return connection;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 线程解绑
     */
    public static void release() {
        connectionThreadLocal.remove();
    }
}