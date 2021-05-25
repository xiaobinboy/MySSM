package edu.gdpu.spring.aop.aspectj;

import edu.gdpu.spring.aop.Signature;

/**
 * 对插入点方法的一些其他描述
 * @author mazebin
 * @date 2021年 03月15日 15:34:52
 */
public class DefaultSignature implements Signature {
   private String name;//方法名

   private String declaringTypeName;//返回类型名

   private Class[] parameterTypes;//参数类型

    public DefaultSignature(String name, String declaringTypeName, Class[] parameterTypes) {
        this.name = name;
        this.declaringTypeName = declaringTypeName;
        this.parameterTypes = parameterTypes;
    }

    public DefaultSignature() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeclaringTypeName(String declaringTypeName) {
        this.declaringTypeName = declaringTypeName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String getName() {
        return this.name;
    }



    @Override
    public String getDeclaringTypeName() {
        return this.declaringTypeName;
    }
}