package edu.gdpu.spring.aop;

import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 03月20日 16:58:49
 */
public class AopConfig {
    //存储切面对象
    private static Class aspect =null;
    //以下五个集合存储aspect Method
    private static Method beforeMethod = null;
    private  static Method afterMethod = null;
    private  static Method afterThrowMethod = null;
    private  static Method afterReturnMethod = null;
    private  static Method aroundMethod = null;

    //默认不使用cglib
    private  static boolean useCglib = false;

    public static Class getAspects() {
        return aspect;
    }

    public static void setUseCglib(boolean useCglib) {
        AopConfig.useCglib = useCglib;
    }

    public static Method getBeforeMethod() {
        return beforeMethod;
    }

    public static Method getAfterMethod() {
        return afterMethod;
    }

    public static Method getAfterThrowMethod() {
        return afterThrowMethod;
    }

    public static Method getAfterReturnMethod() {
        return afterReturnMethod;
    }

    public static Method getAroundMethod() {
        return aroundMethod;
    }

    public static Class getAspect() {
        return aspect;
    }

    public static void setAspect(Class aspect) {
        AopConfig.aspect = aspect;
    }



    public static void setBeforeMethod(Method beforeMethods) {
        AopConfig.beforeMethod = beforeMethods;
    }



    public static void setAfterMethod(Method afterMethods) {
        AopConfig.afterMethod = afterMethods;
    }



    public static void setAfterThrowMethod(Method afterThrowMethods) {
        AopConfig.afterThrowMethod = afterThrowMethods;
    }

    public static void setAfterReturnMethod(Method afterReturnMethods) {
        AopConfig.afterReturnMethod = afterReturnMethods;
    }



    public static void setAroundMethod(Method aroundMethods) {
        AopConfig.aroundMethod = aroundMethods;
    }

    public static boolean isUseCglib() {
        return useCglib;
    }
}