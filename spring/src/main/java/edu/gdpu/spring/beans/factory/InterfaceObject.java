package edu.gdpu.spring.beans.factory;

/**
 * @author mazebin
 * @date 2021年 02月28日 16:30:27
 * 这个类的目的是为了处理接口（因为接口没有构造方法，无法通过反射实例化，必须通过动态代理），让spring在将接口存入ioc容器中的对象class为此类,
 */
public class InterfaceObject {
    private Class aClass;

    public Class getaClass() {
        return aClass;
    }

    public InterfaceObject(Class aClass) {
        this.aClass = aClass;
    }
}