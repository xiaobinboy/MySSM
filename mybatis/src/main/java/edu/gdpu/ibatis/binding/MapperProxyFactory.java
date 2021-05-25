package edu.gdpu.ibatis.binding;

import edu.gdpu.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:39:24
 */
public class MapperProxyFactory<T> {
    //接口类对象
    private Class<T> mapperInterface;
    //方法缓存
    private Map<Method , MapperMethod> methodCache = new ConcurrentHashMap<>();

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Map<Method, MapperMethod> getMethodCache() {
        return methodCache;
    }

    public T newInstance(SqlSession sqlSession){
        //构建mapperProxy
        MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface,methodCache);
        return  newInstance(mapperProxy);
    }
    public T newInstance(MapperProxy mapperProxy){
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),new Class[]{mapperInterface},mapperProxy);
    }
}