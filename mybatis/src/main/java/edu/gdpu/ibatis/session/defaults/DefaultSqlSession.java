package edu.gdpu.ibatis.session.defaults;

import edu.gdpu.ibatis.exceptions.MapperException;
import edu.gdpu.ibatis.executor.Executor;
import edu.gdpu.ibatis.mapping.BoundSql;
import edu.gdpu.ibatis.mapping.MappedStatement;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.session.SqlSession;
import edu.gdpu.ibatis.exceptions.ToomManyResultsExcepetion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月24日 14:32:38
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    public Executor getExecutor() {
        return executor;
    }


    @Override
    public <T> T getMapper(Class<T> mapperInterface) throws MapperException {
        //获取映射代理类
        //return null;
        return configuration.getMapper(mapperInterface, this);
    }


    @Override
    public <T> T selectOne(String id, Object parameter) {
        //根据id获取statement

        List<T> objects = selectList(id, parameter);
        if (objects != null && objects.size() == 1) {
            return  objects.get(0);
        } else if (objects.size() > 1) {
            throw new ToomManyResultsExcepetion("结果集不唯一");
        } else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String id) {

        return selectList(id, null);
    }

    /**
     * id:全类名.方法名
     * parameter:方法参数 ,是一个数组
     *
     * @return
     */

    @Override
    public <E> List<E> selectList(String id, Object parameter) {
        //System.out.println("执行sqlSession.selectList"+parameter);
        MappedStatement mappedStatement = configuration.getMappedStatement(id);
        //先模拟数据库查询
//      BoundSql boundSql = mappedStatement.getBoundSql(parameter);
//      System.out.println("获取到的sql:"+boundSql);
     // return  new ArrayList<>();
        List<E> result = new ArrayList<>();
        try {
            result = (List<E>) executor.query(mappedStatement, parameter);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public int insert(String id) {
        return insert(id, null);
    }

    @Override
    public int insert(String id, Object parameter) {
        return update(id,parameter);
    }

    @Override
    public int update(String id) {
        return update(id, null);
    }

    @Override
    public int update(String id, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(id);
        BoundSql boundSql = ms.getBoundSql(parameter);
        int result =0;
        try {
           result =  executor.update(ms,parameter,boundSql);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(String id) {
        return delete(id, null);
    }

    @Override
    public int delete(String id, Object parameter) {
        return update(id,parameter);
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = configuration.getEnvironment().getDataSource().getConnection();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return connection;
    }
}