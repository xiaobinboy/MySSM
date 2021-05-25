package edu.gdpu.ibatis.executor.parameter;

import edu.gdpu.ibatis.mapping.BoundSql;
import edu.gdpu.ibatis.mapping.ParameterMapping;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 04月02日 17:31:59
 * 参数处理器
 */
public class DefaultParameterHandler implements ParameterHandler {
   private BoundSql boundSql;
   private  Object parameterObject;//方法实际运行参数，是一个数组

    public DefaultParameterHandler(BoundSql boundSql, Object parameterObject) {
        this.boundSql = boundSql;
        this.parameterObject = parameterObject;
    }

    @Override
    public Object getParameterObject() {
        return this.parameterObject;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {
       //获取参数集合
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if(parameterMappings !=null){
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                //获取属性名
                String property = parameterMapping.getProperty();
                //重新转化成数组
                Object[] actualArgs = (Object[]) parameterObject;
                if (actualArgs != null && actualArgs.length != 0) {
                    //根据类 类型判断参数类型 Mybatis中使用TypeHandlerRegistry
                    if (actualArgs[0] instanceof Integer) {
                        Integer integer = (Integer) actualArgs[0];
                        ps.setInt(i + 1, integer);

                    } else if (actualArgs[0] instanceof Long) {
                        Long aLong = (Long) actualArgs[0];
                        ps.setLong(i + 1, aLong);
                    } else if (actualArgs[0] instanceof String) {
                        String str = (String) actualArgs[0];
                        ps.setString(i + 1, str);
                    } else {
                        //参数是自定义类型
                        //获取它的类型
                        Class<?> aClass = actualArgs[0].getClass();
                        Object value;
                        Field field = null;
                        try {
                            if (!void.class.equals(aClass)) {
                                if(property.contains(".")){
                                    int i1 = property.indexOf(".");
                                    int length =property.length();
                                    property  = property.substring(i1+1,length);
                                }
                                field = aClass.getDeclaredField(property);
                                field.setAccessible(true);
                            }
                            if (field != null) {
                                value = field.get(actualArgs[0]);
                                if (value instanceof Integer) {
                                    Integer integer = (Integer) value;
                                    ps.setInt(i + 1, integer);

                                } else if (value instanceof Long) {
                                    Long aLong = (Long) value;
                                    ps.setLong(i + 1, aLong);
                                } else if (value instanceof String) {
                                    String str = (String) value;
                                    ps.setString(i + 1, str);
                                }
                            }

                        } catch ( NoSuchFieldException e ) {
                            e.printStackTrace();
                        } catch ( IllegalAccessException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

    }
}