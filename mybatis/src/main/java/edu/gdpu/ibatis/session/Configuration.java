package edu.gdpu.ibatis.session;

import edu.gdpu.ibatis.binding.MapperMethod;
import edu.gdpu.ibatis.binding.MapperRegistry;
import edu.gdpu.ibatis.exceptions.MapperException;
import edu.gdpu.ibatis.executor.SimpleExecutor;
import edu.gdpu.ibatis.executor.parameter.DefaultParameterHandler;
import edu.gdpu.ibatis.executor.parameter.ParameterHandler;
import edu.gdpu.ibatis.executor.resultset.DefaultResultHandler;
import edu.gdpu.ibatis.executor.resultset.ResultSetHandler;
import edu.gdpu.ibatis.executor.statement.PrepareStatementHandler;
import edu.gdpu.ibatis.executor.statement.StatementHandler;
import edu.gdpu.ibatis.mapping.BoundSql;
import edu.gdpu.ibatis.mapping.Environment;
import edu.gdpu.ibatis.mapping.MappedStatement;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import edu.gdpu.ibatis.executor.Executor;

/**
 * @author mazebin
 * @date 2021年 03月24日 14:40:53
 * 全局配置类
 */
public class Configuration {
    private Environment environment;
    //产生代理对象
    private final MapperRegistry mapperRegistry = new MapperRegistry(this);

    //存储已经封装好的MappedStatement，在MapperScanRegistrar.scanMappedStatement()方法中初始化
private Map<String, MappedStatement> mappedStatementMap = new ConcurrentHashMap<>();
    public Configuration() {
    }

    public Configuration(Environment environment) {
        this.environment = environment;

    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }


    public <T> T getMapper(Class<T> mapperInterface,SqlSession sqlSession) throws MapperException {
     return mapperRegistry.getMapper(mapperInterface,sqlSession);
    }
    //扫描mapper注解
    public <T> void  addMapper(Class<T> mapperInterface){
        mapperRegistry.addMapper(mapperInterface);
    }
    public Executor newExecutor(){
       return new SimpleExecutor(this);
    }
    public void addMappedStatement(MappedStatement mappedStatement){
     mappedStatementMap.put(mappedStatement.getId(),mappedStatement);
    }
    public MappedStatement getMappedStatement(String id){
        return  mappedStatementMap.get(id);
    }


    @Override
    public String toString() {
        return "Configuration{" +
                "environment=" + environment +
                ", mapperRegistry=" + mapperRegistry +
                ", mappedStatementMap=" + mappedStatementMap +
                '}';
    }

    public StatementHandler newStatementHandler(BoundSql boundSql,Object parameterObject,MappedStatement mappedStatement) {
        return new PrepareStatementHandler(this,boundSql,parameterObject,mappedStatement);
    }
    public ParameterHandler newParameterHandler(BoundSql boundSql ,Object parameterObject){
        return  new DefaultParameterHandler(boundSql,parameterObject);
    }
    public ResultSetHandler newResultSetHandler(MappedStatement mappedStatement){
        return  new DefaultResultHandler(mappedStatement);
    }


}