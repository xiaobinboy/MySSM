package edu.gdpu.ibatis.builder;

import edu.gdpu.ibatis.mapping.DefaultSqlSource;
import edu.gdpu.ibatis.mapping.SqlSource;
import edu.gdpu.ibatis.parsing.GenericTokenParser;
import edu.gdpu.ibatis.parsing.ParameterMappingTokenHandler;

/**
 * @author mazebin
 * @date 2021年 03月27日 22:33:46
 * Sql语句封装处理
 */
public class SqlSourceBuilder {
    public SqlSource builder(String originalSql){
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String sql = parser.parse(originalSql);
        return  new DefaultSqlSource(sql,handler.getParameterMappings());
    }
}