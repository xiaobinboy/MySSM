package edu.gdpu.springmvc.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 04月12日 23:33:31
 * Method 封装类
 */
public class HandlerMethod {
    /**
     * 参数说明： bean:方法执行对象
     */
    private Object bean;
    private Method method;
    private List<MethodParameter> methodParameters = new ArrayList<>();
    private List<Object> methodArgs = new ArrayList<>();

    public HandlerMethod(Method method) {
    }

    public HandlerMethod(Object bean,Method method) {
    this.bean = bean;
    this.method = method;
    }



    public Method getMethod() {
        return method;
    }

    public Object getBean() {
        return bean;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<MethodParameter> getMethodParameters() {
        return methodParameters;
    }

    public void addMethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }
    public void addMethodParameter(MethodParameter methodParameter) {
        this.methodParameters.add(methodParameter);
    }

    public List<Object> getMethodArgs() {
        return methodArgs;
    }

    public void addMethodArg(Object methodArg) {
        this.methodArgs.add(methodArg);
    }

    @Override
    public String toString() {
        return "HandlerMethod{" +
                "bean=" + bean +
                ", method=" + method +
                ", methodParameters=" + methodParameters +
                ", methodArgs=" + methodArgs +
                '}';
    }
}