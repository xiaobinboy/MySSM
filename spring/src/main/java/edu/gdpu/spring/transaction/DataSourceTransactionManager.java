package edu.gdpu.spring.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author mazebin
 * @date 2021年 03月22日 10:37:55
 */

public class DataSourceTransactionManager implements  TransactionManager {
    private Connection connection;



    /**
     * connection.setReadOnly();
     * connection.setTransactionIsolation(int level),更改隔离级别
     */


    public void setConnection() {
        this.connection = ConnectionManager.getLocalConnection();
    }
    public void  before(){
        try {

            connection.setAutoCommit(false);
            connection.setReadOnly(TransactionConfig.isReadOnly());
            connection.setTransactionIsolation(TransactionConfig.getTransactionIsolation());
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }
    public void  after(){
        try {

            connection.commit();
            connection.setAutoCommit(true);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }
    public void  afterThrow(){
        try {

            connection.rollback();

        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }


}