package edu.gdpu.spring.beans.factory;

import edu.gdpu.spring.exception.BeanExistedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mazebin
 * @date 2021年 02月16日 16:12:48
 * 将bean放入ioc容器中
 */
public class DefaultListableBeanFactory implements BeanFactory {
    //ioc容器
    private  Map<String, Object> beans = new ConcurrentHashMap<>();

@Override
    public Map<Class, String> getMapping() {
        return mapping;
    }

    private static final Logger logger = LoggerFactory.getLogger(DefaultListableBeanFactory.class);



    public DefaultListableBeanFactory() {
    }

    //ioc初始化容器
    private  Map<Class, String> mapping = new ConcurrentHashMap<>();

@Override
    public  Map<String, Object> getBeans() {
        return beans;
    }

    @Override
    public void printBeans() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {

            logger.info(" ioc容器：\nkey = " + entry.getKey() + ", value = " + entry.getValue());
        }
    }

    @Override
    public <T> T getBean(String name) {
        return (T) beans.get(name);
    }

    @Override
    public <T> T getBean(Class<T> cl) {
        return (T) beans.get(mapping.get(cl));
    }

    /**
     * bean的初始化
     *
     * @param key
     * @param value
     */
    @Override
    public Object setBean(String key, Object value) {
        if (beans.containsKey(key)) {
            if (!(beans.get(key) instanceof InterfaceObject)) {
                try {
                    throw new BeanExistedException("该bean已存在"+beans.get(key));
                } catch ( BeanExistedException e ) {
                    e.printStackTrace();
                }
            } else {
                //ioc容器中有这个key,且对应的value类型为interfaceObject,即传入的key为某个接口名
                InterfaceObject interfaceObject = (InterfaceObject) beans.get(key);
                //将接口的字节码对象存入mapping中
                mapping.put(interfaceObject.getaClass(), key);
                return beans.put(key, value);
            }
        }
        else{
            mapping.put(value.getClass(),key);
            if(value instanceof ServletContext){
                mapping.put(ServletContext.class,key);
            }
            return  beans.put(key,value);
        }
        //ioc容器中没有这个key
        mapping.put(value.getClass(), key);
        return beans.put(key, value);
    }
}