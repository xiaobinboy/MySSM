package edu.gdpu.ibatis.executor.statement;

import edu.gdpu.ibatis.executor.parameter.ParameterHandler;
import edu.gdpu.ibatis.executor.resultset.ResultSetHandler;
import edu.gdpu.ibatis.mapping.BoundSql;
import edu.gdpu.ibatis.mapping.MappedStatement;
import edu.gdpu.ibatis.session.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * @author mazebin
 * @date 2021年 03月24日 10:34:47
 *
 *
 */
public class PrepareStatementHandler implements StatementHandler {
    private Configuration configuration;
    private BoundSql boundSql;
    private ParameterHandler parameterHandler;
    private ResultSetHandler resultSetHandler;
    private Object parameterObject; //方法运行时参数,是一个数组
    private MappedStatement mappedStatement;

    public PrepareStatementHandler(Configuration configuration,BoundSql boundSql,Object parameterObject,MappedStatement mappedStatement) {
        this.configuration = configuration;
        this.boundSql = boundSql;
        this.parameterObject = parameterObject;
        this.parameterHandler = configuration.newParameterHandler(boundSql,parameterObject);
        this.mappedStatement = mappedStatement;
        this.resultSetHandler = configuration.newResultSetHandler(mappedStatement);

    }

    @Override
    public void parameterSize(Statement statement) throws SQLException {
//给占位符赋值
        parameterHandler.setParameters((PreparedStatement) statement);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        return ps.getUpdateCount();
    }

    @Override
    public <E> List<E> query(Statement statement) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;
         ps.execute();
        //返回结果集封装
        return resultSetHandler.handleResultSets(ps);
    }

    @Override
    public BoundSql getBoundSql() {
        return this.boundSql;
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException{
        return  connection.prepareStatement(boundSql.getSql());
    }
}