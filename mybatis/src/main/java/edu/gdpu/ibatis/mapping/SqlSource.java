package edu.gdpu.ibatis.mapping;

/**
 * @author mazebin
 * @date 2021年 03月27日 10:56:25
 * Sql语句接口
 */
public interface SqlSource {
    public BoundSql getBoundSql(Object parameterObject);
}