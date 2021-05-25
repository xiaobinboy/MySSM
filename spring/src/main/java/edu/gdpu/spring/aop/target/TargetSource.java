package edu.gdpu.spring.aop.target;

/**
 * @author mazebin
 * @date 2021年 03月03日 17:29:45
 */
public interface TargetSource {
    //获取class
    Class<?> getaClass();
    //获取目标对象
    Object getTarget();
    //获取目标接口对象
    Class<?>[] getInterfaces();

}