package edu.gdpu.ibatis.mapping;

import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:37:38
 * 这个类是mybatis的sql语句封装类
 * 会在sqlSession和mappedStatement中使用到
 */
public class BoundSql {
    //真正执行的sql
    private String sql;
    private List<ParameterMapping> parameterMappings;//sql语句的参数
    private  Object parameterObject;//方法传入的参数，需要判断是否为数据封装类型，是就是空,集合类型不做判断

    public BoundSql(String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;

        this.parameterObject = parameterObject;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public void setParameterObject(Object parameterObject) {
        this.parameterObject = parameterObject;
    }

    @Override
    public String toString() {
        return "BoundSql{" +
                "sql='" + sql + '\'' +
                ", parameterMappings=" + parameterMappings +
                ", parameterObject=" + parameterObject +
                '}';
    }
}