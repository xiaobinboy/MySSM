package edu.gdpu.springmvc.method;

import java.lang.reflect.Type;

/**
 * @author mazebin
 * @date 2021年 04月13日 00:06:01
 * 参数类型
 * 参数名
 */
public class MethodParameter {
   private Type methodParameterType;
   private String methodParameterName;


    public MethodParameter(Type methodParameterType, String methodParameterName) {
        this.methodParameterType = methodParameterType;
        this.methodParameterName = methodParameterName;
    }

    public Type getMethodParameterType() {
        return methodParameterType;
    }

    public void setMethodParameterType(Type methodParameterType) {
        this.methodParameterType = methodParameterType;
    }

    public String getMethodParameterName() {
        return methodParameterName;
    }

    public void setMethodParameterName(String methodParameterName) {
        this.methodParameterName = methodParameterName;
    }

    @Override
    public String toString() {
        return "MethodParameter{" +
                "methodParameterType=" + methodParameterType +
                ", methodParameterName='" + methodParameterName + '\'' +
                '}';
    }
}