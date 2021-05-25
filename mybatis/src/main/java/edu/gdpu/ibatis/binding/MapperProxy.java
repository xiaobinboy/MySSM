package edu.gdpu.ibatis.binding;

import edu.gdpu.ibatis.mapping.ParameterMapping;
import edu.gdpu.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:38:58
 */
public class MapperProxy<T> implements InvocationHandler {
    private SqlSession sqlSession;
    private Class<T> mapperInterface;
    private Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface,Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       if(Object.class.equals(method.getDeclaringClass())){
        System.out.println("mapperProxy: is Object"+mapperInterface);
           System.out.println("method:"+method);
           return null;
       }
        if(method.isDefault()){
            System.out.println("this is default method");
            return null;
        }
       final MapperMethod mapperMethod = cachedMapperMethod(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        //设置参数类型

        return mapperMethod.execute(sqlSession,args);

    }
    public MapperMethod cachedMapperMethod(Method method){
        return methodCache.computeIfAbsent(method,k-> new MapperMethod(mapperInterface,method,sqlSession.getConfiguration()));
    }

}