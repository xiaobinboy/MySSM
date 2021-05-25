package edu.gdpu.spring.aop;

/**
 * 对切入点的一些描述
 */
public interface Signature {
    //获取方法名
    String getName();
    String getDeclaringTypeName();
//获取参数类型
    Class[] getParameterTypes();

}
