package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.AopConfig;
import edu.gdpu.spring.aop.annotation.*;

import edu.gdpu.spring.aop.support.*;
import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.exception.BeanExpressionException;
import edu.gdpu.spring.exception.PointcutNotFoundException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月20日 19:52:13
 * 这里应该是只需返回 一个list<advisors>
 */
public class AspectPostProcessor implements AnnotationPostProcessor {
//    private TargetSource targetSource;
//
//    public void setTargetSource(TargetSource targetSource) {
//        this.targetSource = targetSource;
//    }

    @Override
    public Object handle(Object o) throws BeanExpressionException {

        Class cl = (Class) o;
        String pointcutMethodname = null;
        ExecutionPointcut executionPointcut = null;
        List<Advisor> advisors = new ArrayList<>();
        //设置切面类class
        AopConfig.setAspect(cl);
        Method[] methods = ReflectUitls.getDeclaredMethods(cl);
        for (Method method : methods) {
            if (method.isAnnotationPresent(Pointcut.class)) {
                Pointcut annotation = method.getAnnotation(Pointcut.class);
                executionPointcut = new ExecutionPointcut(annotation.value());
                pointcutMethodname = method.getName() + "()";

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
                    advisors.add(advisor);
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
                    advisors.add(advisor);
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
                    advisors.add(advisor);
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
                    advisors.add(advisor);
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
advisors.add(advisor);
                    //设置方法到aopConfig
                    AopConfig.setAroundMethod(method);

                } else {
                    throw new PointcutNotFoundException("找不到已配置好的切入点表达式");
                }
            }
        }

        return advisors;
    }
}
