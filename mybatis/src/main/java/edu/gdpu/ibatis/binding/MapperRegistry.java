package edu.gdpu.ibatis.binding;

import edu.gdpu.ibatis.exceptions.MapperException;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.session.SqlSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:39:37
 */
public class MapperRegistry {

    private Map<Class<?> ,MapperProxyFactory<?>> knowMappers = new ConcurrentHashMap<>();
    private Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T getMapper(Class<T> mapperInterface, SqlSession sqlSession)throws MapperException {
        MapperProxyFactory<?> mapperProxyFactory = knowMappers.get(mapperInterface);
        if(mapperProxyFactory == null){
            throw  new MapperException("创建mapperFactory异常");
        }
        T t = (T) mapperProxyFactory.newInstance(sqlSession);
        return  t;
    }
    public <T> void addMapper(Class<T> type){
if(knowMappers.containsKey(type)){
    return;
}else{
    MapperProxyFactory<T> factory = new MapperProxyFactory<T>(type);
    knowMappers.put(type,factory);
}
    }
}