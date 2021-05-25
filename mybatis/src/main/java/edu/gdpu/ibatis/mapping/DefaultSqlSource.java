package edu.gdpu.ibatis.mapping;

import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月28日 00:16:51
 * Sql语句获取，在mappedStatement中使用
 */
public class DefaultSqlSource implements SqlSource {
   //真正执行的sql语句
    private String sql;
   //方法参数
   private List<ParameterMapping> parameterMappings;

    public DefaultSqlSource() {
    }

    public DefaultSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
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

    /**
     *
     * @param parameterObject 执行的Sql方法参数
     * @return
     */
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(sql,parameterMappings,parameterObject);
    }

    @Override
    public String toString() {
        return "DefaultSqlSource{" +
                "sql='" + sql + '\'' +
                ", parameterMappings=" + parameterMappings +
                '}';
    }
}