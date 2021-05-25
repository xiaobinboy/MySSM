package edu.gdpu.ibatis.executor;

import edu.gdpu.ibatis.mapping.BoundSql;
import edu.gdpu.ibatis.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(MappedStatement ms,Object parameter)throws SQLException;
    <E> List<E> query(MappedStatement ms, Object parameter, BoundSql boundSql) throws SQLException;
Connection getConnection();

    int  update(MappedStatement ms, Object parameter,BoundSql boundSql) throws SQLException;
}
