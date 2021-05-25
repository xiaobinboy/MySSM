package edu.gdpu.spring.core.util;

import edu.gdpu.spring.beans.factory.InterfaceObject;
import edu.gdpu.spring.exception.BeanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author mazebin
 * @date 2021年 02月24日 18:21:26
 * 生成bean工具类
 * 生成的bean尚未初始化，也未加入ioc容器中
 */
public class BeanUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);

    @SuppressWarnings("unchecked")
    public static <T> T createBean(Class<T> cl) throws BeanException {
        T t = null;
        try {
            if (cl.isInterface()) {
                t = (T) new InterfaceObject(cl);
                LOGGER.info(cl + " is a interface, \n返回对象类型为： " + t.getClass());
                return t;
            }
            t = cl.getDeclaredConstructor().newInstance();
            return t;
        } catch ( InstantiationException e ) {
            e.printStackTrace();
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        } catch ( InvocationTargetException e ) {
            e.printStackTrace();
        } catch ( NoSuchMethodException e ) {
            e.printStackTrace();
        }
        return null;
    }

}