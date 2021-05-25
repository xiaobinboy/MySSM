package edu.gdpu.spring.aop;

/**
 * 连接点
 */
public interface JoinPoint {

//获取被代理对象
    Object getTarget();
//获取被代理对象执行方法的参数
    Object[] getArgs();
    //获取被代理对象执行方法的其他描述
    Signature getSignature();
    //执行被代理对象的方法
    Object proceed() throws Exception;
}
