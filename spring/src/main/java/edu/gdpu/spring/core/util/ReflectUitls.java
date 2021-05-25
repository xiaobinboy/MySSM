package edu.gdpu.spring.core.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * @author mazebin
 * @date 2021年 03月15日 17:13:42
 * 反射工具类
 * 参考spring
 */
public class ReflectUitls {
    //设置空参数
    private static  final  Object[] emptyArgs =null;

    public ReflectUitls() {
    }

    public static void makeAccessible(Method method){
        if(!Modifier.isPublic(method.getModifiers())|| !Modifier.isPublic(method.getDeclaringClass().getModifiers())||!method.isAccessible()){
            method.setAccessible(true);
        }
    }
    public static void makeAccessible(Field filed){
        if(!Modifier.isPublic(filed.getModifiers())||
                Modifier.isPublic(filed.getDeclaringClass().getModifiers())
                ||!Modifier.isFinal(filed.getModifiers())
                ||!filed.isAccessible()){
           filed.setAccessible(true);
        }
    }
    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        } else if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        } else {
            if (ex instanceof InvocationTargetException) {
                handleInvocationTargetException((InvocationTargetException)ex);
            }

            if (ex instanceof RuntimeException) {
                throw (RuntimeException)ex;
            } else {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }
    public static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        } else if (ex instanceof Error) {
            throw (Error)ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

    public static  Object invokeMethod(Method method,Object target, Object... args){
        try {
            return method.invoke(target,args);
        } catch (Exception var4) {
            handleReflectionException(var4);
            throw new IllegalStateException("Should never get here");
        }

    }
    public static  Object invokeMethod(Method method,Object target){
        return  invokeMethod(method,target,emptyArgs);
    }

    public static  Method[] getDeclaredMethods(Class<?> cl){
        return cl.getDeclaredMethods();
    }
    public static void setFiled(Field filed,Object target,Object value){
        try {
            filed.set(target,value);
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }
    }
    public static  Object getField(Field field,Object target){
        try {
            return field.get(target);
        } catch ( IllegalAccessException e ) {
            handleReflectionException(e);
            throw new IllegalStateException("Should never get here");
        }
    }
    public static  Field[] getDeclaredFields(Class<?> cl){
        return cl.getDeclaredFields();
    }
    public static boolean isEqualsMethod(Method method) {
        if (method != null && method.getName().equals("equals")) {
            if (method.getParameterCount() != 1) {
                return false;
            } else {
                return method.getParameterTypes()[0] == Object.class;
            }
        } else {
            return false;
        }
    }
    public static boolean isHashCodeMethod( Method method) {
        return method != null && method.getName().equals("hashCode") && method.getParameterCount() == 0;
    }

    public static boolean isToStringMethod( Method method) {
        return method != null && method.getName().equals("toString") && method.getParameterCount() == 0;
    }

    public static boolean isObjectMethod( Method method) {
        return method != null && (method.getDeclaringClass() == Object.class || isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method));
    }
    public static Annotation[] getAnnotations(Class<?> cl){
        return cl.getAnnotations();
    }
    public static  Parameter[] getParameters(Method method){
        return method.getParameters();
    }

}