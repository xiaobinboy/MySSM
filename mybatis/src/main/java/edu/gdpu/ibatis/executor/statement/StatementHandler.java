package edu.gdpu.ibatis.executor.statement;

import edu.gdpu.ibatis.mapping.BoundSql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {
    //获取Statement对象
    Statement prepare(Connection connection) throws SQLException;
    //对Statement对象占位符进行赋值
    void parameterSize(Statement statement) throws SQLException;
    int update(Statement statement)
            throws SQLException;

    <E> List<E> query(Statement statement)
            throws SQLException;

    BoundSql getBoundSql();
}
