package edu.gdpu.spring.aop.support;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.AopConfig;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.core.util.ReflectUitls;

/**
 * @author mazebin
 * @date 2021年 03月15日 16:16:51
 * 如何获取要织入的通知方法参数，很显然在生成时反射method.getParameters()
 */

public class AfterReturnAdvice implements Advice, MethodInterceptor {
    private  static ApplicationContext context= ApplicationContextFactory.getApplicationContext();
    @Override
    public Object invokeMethod() {
       Object aspect = context.getBean(AopConfig.getAspect());

        return ReflectUitls.invokeMethod(AopConfig.getAfterReturnMethod(),aspect);
    }
    //写一个invoke方法

}