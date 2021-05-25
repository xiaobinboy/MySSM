package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.Bean;


import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.exception.BeanExpressionException;

import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 02月28日 20:00:55
 */
public class BeanPostProcessor implements AnnotationPostProcessor {
    //需要注入成员变量的类
    private Object invoker;

    public void setInvoker(Object invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object handle(Object o) throws BeanExpressionException {
        Method method = (Method) o;
        inject(method);
        return null;
    }

    public Object inject(Method method) {
        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();

        Object target = null;
        Bean annotation =  method.getAnnotation(Bean.class);
        String value = annotation.value();
        //获取方法名
        String methodName = method.getName();
        ReflectUitls.makeAccessible(method);
            if (value.equals("")) {
                //将bean注入ioc容器
                target = myApplicationContext.setBean(methodName, ReflectUitls.invokeMethod(method,invoker));
            } else {
                target = myApplicationContext.setBean(value, ReflectUitls.invokeMethod(method,invoker));
            }

        return target;
    }
}