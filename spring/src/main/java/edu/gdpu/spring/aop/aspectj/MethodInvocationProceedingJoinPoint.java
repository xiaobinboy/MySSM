package edu.gdpu.spring.aop.aspectj;

import edu.gdpu.spring.aop.JoinPoint;
import edu.gdpu.spring.aop.Signature;
import edu.gdpu.spring.core.util.ReflectUitls;

import java.lang.reflect.Method;

/**
 * spring框架中将这一部分分为MethodInvocation
 * 和MethodInvocationProceedingJoinPoint
 * JoinPoint的proceed方法来调用MethodInvocation 的proceed方法,继而调用
 * MethodInterceptor的invoke方法（即afterAdvice等类）
 *
 * @author mazebin
 * @date 2021年 03月15日 15:23:05
 */
public class MethodInvocationProceedingJoinPoint implements JoinPoint {

    private Object[] args;//方法参数
    private Signature signature;//类型要求：defaultSignature
    private Object target;//目标对象
    private Method method;//方法对象

    public MethodInvocationProceedingJoinPoint(Object[] args, Signature signature, Object target, Method method) {
        this.args = args;
        this.signature = signature;
        this.target = target;
        this.method = method;
    }
    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public Signature getSignature() {
        return this.signature;
    }

    @Override
    public Object proceed() throws Exception {
        return ReflectUitls.invokeMethod(method,target,args);

    }


}