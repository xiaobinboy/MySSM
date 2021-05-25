package edu.gdpu.ibatis.executor;

import edu.gdpu.ibatis.mapping.BoundSql;
import edu.gdpu.ibatis.mapping.MappedStatement;
import edu.gdpu.ibatis.session.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:31:03
 */
public  abstract class BaseExecutor implements Executor {
    private Configuration configuration;

    public BaseExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter)throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameter);
        return query(ms,parameter,boundSql);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, BoundSql boundSql) throws SQLException {
        return doQuery(ms,parameter,boundSql);
    }

    public  abstract <E> List<E> doQuery(MappedStatement ms, Object parameter, BoundSql boundSql) throws SQLException;

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = this.configuration.getEnvironment().getDataSource().getConnection();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public int update(MappedStatement ms, Object parameter,BoundSql boundSql) throws SQLException {
        return doUpdate(ms,parameter,boundSql);
    }
    public  abstract int doUpdate(MappedStatement ms,Object parameter,BoundSql boundSql) throws SQLException;
}