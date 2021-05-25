package edu.gdpu.ibatis.executor;

import edu.gdpu.ibatis.executor.statement.StatementHandler;
import edu.gdpu.ibatis.mapping.BoundSql;
import edu.gdpu.ibatis.mapping.MappedStatement;
import edu.gdpu.ibatis.session.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:31:14
 */
public class SimpleExecutor extends  BaseExecutor {
    public SimpleExecutor(Configuration configuration) {
        super(configuration);
    }

    /**
     *
     * @param ms
     * @param parameter:方法运行实参
     * @param boundSql
     * @param <E>
     * @return
     * @throws SQLException
     */
    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, BoundSql boundSql) throws SQLException {
        Configuration configuration = ms.getConfiguration();
        StatementHandler handler  = configuration.newStatementHandler(boundSql,parameter,ms);
        Statement statement = preparedStatement(handler);
        return  handler.query(statement);
    }

    @Override
    public int doUpdate(MappedStatement ms, Object parameter,BoundSql boundSql) throws SQLException {
        Configuration configuration = ms.getConfiguration();
        StatementHandler handler = configuration.newStatementHandler(boundSql, parameter, ms);
        Statement statement = preparedStatement(handler);

        return handler.update(statement);
    }

    private Statement preparedStatement(StatementHandler statementHandler) throws SQLException{
        Statement stmt;
        Connection connection = getConnection();
        //获取已经预编译的sql
       stmt = statementHandler.prepare(connection);
       //给占位符赋值
        statementHandler.parameterSize(stmt);
       return stmt;
    }


}