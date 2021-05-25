package edu.gdpu.ibatis.executor.resultset;

import edu.gdpu.ibatis.annotation.Result;
import edu.gdpu.ibatis.mapping.MappedStatement;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月28日 00:32:11
 */
public class DefaultResultHandler implements ResultSetHandler {
    public MappedStatement mappedStatement;

    public DefaultResultHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        List<E> object = new ArrayList<>();
        ResultSet firstResultSet = getFirstResultSet(stmt);

        //获取结果类型，通过反射创建实例，使用反射或者内省，完成封装
        String resultType = mappedStatement.getResultType();
        while (firstResultSet.next()) {
            ResultSetMetaData metaData = firstResultSet.getMetaData();
            Class<?> aClass = null;
            E o = null;

            try {
                if(resultType .contains("List")){
                    int i = resultType.indexOf("<");
                    int i1 = resultType.indexOf(">");
                    resultType = resultType.substring(i+1,i1);

                }
                aClass = Class.forName(resultType);
                if (aClass != null) {
                    o = (E) aClass.getDeclaredConstructor().newInstance();
                    if (o != null) {
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                             //获取字段名
                            String columnName = metaData.getColumnName(i);

                            //获取字段值
                            Object value = firstResultSet.getObject(i);

                            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, aClass);
                            Method method = propertyDescriptor.getWriteMethod();
                            method.invoke(o,value);

                        }
                        object.add(o);
                    }
                }

            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return object;
    }

    @Override
    public void handleOutputParameters(CallableStatement cs) throws SQLException {

    }

    public ResultSet getFirstResultSet(Statement stmt) throws SQLException {
        ResultSet resultSet = stmt.getResultSet();
        while (resultSet == null) {
            if (stmt.getMoreResults()) {
                resultSet = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    break;
                }
            }
        }
        return resultSet;
    }

}