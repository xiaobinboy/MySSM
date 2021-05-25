package edu.gdpu.spring.aop;

/**
 * @author mazebin
 * @date 2021年 03月03日 15:07:32
 */
public interface Pointcut extends ClassFilter, MethodMatcher {
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}