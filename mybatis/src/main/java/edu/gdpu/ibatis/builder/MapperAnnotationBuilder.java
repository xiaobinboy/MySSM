package edu.gdpu.ibatis.builder;

import edu.gdpu.ibatis.annotation.Delete;
import edu.gdpu.ibatis.annotation.Insert;
import edu.gdpu.ibatis.annotation.Select;
import edu.gdpu.ibatis.annotation.Update;
import edu.gdpu.ibatis.mapping.MappedStatement;
import edu.gdpu.ibatis.mapping.SqlCommandType;
import edu.gdpu.ibatis.mapping.SqlSource;
import edu.gdpu.ibatis.session.Configuration;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 03月28日 00:30:45
 * 创建MappedStatement
 */
public class MapperAnnotationBuilder {
    private Configuration configuration;

    public MapperAnnotationBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public  void addMappedStatement(Method method){
    String id= null;
    String resultType = null;
     SqlCommandType type = null;
     SqlSourceBuilder builder = new SqlSourceBuilder();
    SqlSource sqlSource = null;

    Annotation[] annotations = method.getAnnotations();
    for (Annotation annotation : annotations) {
        if(annotation instanceof  Select){
            Select select = (Select) annotation;
            String originalSql = select.value();
            sqlSource = builder.builder(originalSql);
            id =method.getDeclaringClass().getName()+"."+method.getName();
            resultType = method.getGenericReturnType().getTypeName();
            type = SqlCommandType.SELECT;
            MappedStatement statement = new MappedStatement(id,resultType,type,sqlSource,configuration);

            configuration.addMappedStatement(statement);

        }else if( annotation instanceof  Delete){
            Delete delete = (Delete) annotation;
            String originalSql = delete.value();
            sqlSource = builder.builder(originalSql);
            id =method.getDeclaringClass().getName()+"."+method.getName();
            resultType = method.getGenericReturnType().getTypeName();
            type=SqlCommandType.DELETE;
            MappedStatement statement = new MappedStatement(id,resultType,type,sqlSource,configuration);

            configuration.addMappedStatement(statement);
        }else if(annotation instanceof  Insert){
            Insert insert = (Insert) annotation;
            String originalSql = insert.value();
            sqlSource = builder.builder(originalSql);
            id =method.getDeclaringClass().getName()+"."+method.getName();
            resultType = method.getGenericReturnType().getTypeName();
            type=SqlCommandType.INSERT;
            MappedStatement statement = new MappedStatement(id,resultType,type,sqlSource,configuration);
            configuration.addMappedStatement(statement);
        }else if(annotation instanceof  Update){
            Update update = (Update) annotation;
            String originalSql = update.value();
            sqlSource = builder.builder(originalSql);
            id =method.getDeclaringClass().getName()+"."+method.getName();
            resultType = method.getGenericReturnType().getTypeName();
            type = SqlCommandType.UPDATE;
            MappedStatement statement = new MappedStatement(id,resultType,type,sqlSource,configuration);
            configuration.addMappedStatement(statement);
        }
    }
}

}