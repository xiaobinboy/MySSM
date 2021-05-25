package edu.gdpu.spring.aop.support;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.AopConfig;
import edu.gdpu.spring.aop.JoinPoint;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.core.util.ReflectUitls;

import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 03月20日 11:10:08
 */
public class AroundAdvice implements Advice,MethodInterceptor {
    private  static ApplicationContext context= ApplicationContextFactory.getApplicationContext();
    @Override
    public Object invokeMethod(JoinPoint joinPoint){
       Object aspect =  context.getBean(AopConfig.getAspect());
        Method aroundMethod = AopConfig.getAroundMethod();
        ReflectUitls.makeAccessible(aroundMethod);
        return ReflectUitls.invokeMethod(aroundMethod,aspect,joinPoint);

    }
}