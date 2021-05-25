package edu.gdpu.spring.aop.support;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.AopConfig;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.core.util.ReflectUitls;

import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 03月15日 16:14:45
 * 这里我们要考虑获取切面,这个切面从ioc容器中获取
 */
public class BeforeAdvice implements Advice,MethodInterceptor {
 private  static ApplicationContext context= ApplicationContextFactory.getApplicationContext();


  @Override
    public Object invokeMethod(){
        Object aspect =  context.getBean(AopConfig.getAspect());

        Method beforeMethod = AopConfig.getBeforeMethod();
        ReflectUitls.makeAccessible(beforeMethod);
        if(aspect !=null) {
            return ReflectUitls.invokeMethod(beforeMethod, aspect);
        }
        return null;
    }
}