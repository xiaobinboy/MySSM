package edu.gdpu.MySSM.test.aopTest;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.AopConfig;
import edu.gdpu.spring.aop.annotation.*;
import edu.gdpu.spring.aop.support.*;
import edu.gdpu.spring.aop.target.SingletonTargetSource;
import edu.gdpu.spring.aop.target.TargetSource;
import edu.gdpu.ibatis.io.ClassUtils;
import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.exception.PointcutNotFoundException;
import org.junit.Test;


import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 03月20日 11:16:18
 */
public class AopProxyTest {
     private TargetSource targetSource;
    @Test
    public void test() throws Exception {
        //模拟从ioc容器中获取需要增强的bean
        Class objectClass = ClassUtils.loadClass(ClassUtils.getDefaultClassLoader(), "edu.gdpu.MySSM.test.aopTest.MyServiceImpl");
        MyServiceImpl o = (MyServiceImpl) objectClass.getDeclaredConstructor().newInstance();
        Method method = objectClass.getMethod("say");


        //创建目标源
        System.out.println(o.getClass());
        targetSource = new SingletonTargetSource(o);
        //模拟获取切面类
        Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.aopTest.MyAspect");
        MyService handle = (MyService) handle(aClass);
       // System.out.println(handle.getClass());
        handle.say();

    }

    //模拟使用后置处理器中获取aspect注解，advice相关注解，pointcut注解
    public Object handle(Class cl) {

        String pointcutMethodname = null;
        ExecutionPointcut executionPointcut = null;
        ProxyFactoryBean support = new ProxyFactoryBean();
        if (cl.isAnnotationPresent(Aspect.class)) {
            AopConfig.setAspect(cl);
            Method[] methods = ReflectUitls.getDeclaredMethods(cl);
            for (Method method : methods) {
                if (method.isAnnotationPresent(Pointcut.class)) {
                    Pointcut annotation = method.getAnnotation(Pointcut.class);
                    executionPointcut = new ExecutionPointcut(annotation.value());
                    pointcutMethodname = method.getName()+"()";
                }
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(Before.class)) {
                    Before annotation = method.getAnnotation(Before.class);
                    Advice beforeAdvice = new BeforeAdvice();
                    if (annotation.value().equals(pointcutMethodname)) {
                        //创建通知器
                        Advisor advisor = new Advisor(beforeAdvice, executionPointcut);
                        //设置通知器到通知器拦截链中
                        support.setAdvisors(advisor);
                        //设置方法到aopConfig
                        AopConfig.setBeforeMethod(method);
                    } else {
                        throw new PointcutNotFoundException("找不到已配置好的切入点表达式");
                    }
                }
                if (method.isAnnotationPresent(After.class)) {
                    After annotation = method.getAnnotation(After.class);
                    Advice afterAdvice = new AfterAdvice();

                    if (annotation.value().equals(pointcutMethodname)) {
                        //创建通知器
                        Advisor advisor = new Advisor(afterAdvice, executionPointcut);
                        //设置通知器到通知器拦截链中
                        support.setAdvisors(advisor);
                        //设置方法到aopConfig
                        AopConfig.setAfterMethod(method);

                    } else {
                        throw new PointcutNotFoundException("找不到已配置好的切入点表达式");
                    }
                }
                if (method.isAnnotationPresent(AfterReturn.class)) {
                    AfterReturn annotation = method.getAnnotation(AfterReturn.class);
                    Advice afterReturnAdvice = new AfterReturnAdvice();
                    if (annotation.value().equals(pointcutMethodname)) {
                        //创建通知器
                        Advisor advisor = new Advisor(afterReturnAdvice, executionPointcut);
                        //设置通知器到通知器拦截链中
                        support.setAdvisors(advisor);
                        //设置方法到aopConfig
                        AopConfig.setAfterReturnMethod(method);

                    } else {
                        throw new PointcutNotFoundException("找不到已配置好的切入点表达式");
                    }
                }
                if (method.isAnnotationPresent(AfterThrow.class)) {
                    AfterThrow annotation = method.getAnnotation(AfterThrow.class);
                    Advice afterThrowing = new AfterThrowAdvice();

                    if (annotation.value().equals(pointcutMethodname)) {
                        //创建通知器
                        Advisor advisor = new Advisor(afterThrowing, executionPointcut);
                        //设置通知器到通知器拦截链中
                        support.setAdvisors(advisor);
                        //设置方法到aopConfig
                      AopConfig.setAfterThrowMethod(method);

                    } else {
                        throw new PointcutNotFoundException("找不到已配置好的切入点表达式");
                    }
                }
                if (method.isAnnotationPresent(Around.class)) {
                    Around annotation = method.getAnnotation(Around.class);
                    Advice aroundAdvice = new AroundAdvice();
                    if (annotation.value().equals(pointcutMethodname)) {
                        //创建通知器
                        Advisor advisor = new Advisor(aroundAdvice, executionPointcut);
                        //设置通知器到通知器拦截链中
                        support.setAdvisors(advisor);
                        //设置方法到aopConfig
                        AopConfig.setAroundMethod(method);

                    } else {
                        throw new PointcutNotFoundException("找不到已配置好的切入点表达式");
                    }
                }
            }
        }
        support.setTargetSource(targetSource);

        return support.getProxy();
    }



}