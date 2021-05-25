package edu.gdpu.mySSM.test.javaseTest.reflectTest;

import org.junit.Test;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @author mazebin
 * @date 2021年 04月01日 23:57:57
 * Java中可以声明泛型变量的地方有三个class，contructor和method，TypeVariable接口的声明如下：
 *
 * public interface TypeVariable<D extends GenericDeclaration> extends Type, AnnotatedElement {
 *     Type[] getBounds();
 *     D getGenericDeclaration();
 *     String getName();
 *     AnnotatedType[] getAnnotatedBounds();
 * }
 * getBounds是拿获取类型变量的边界
 * getGenericDeclaration是获取到声明类型变量的语法元素，哪个class，哪个class的哪个构造方法，哪个class的哪个方法；
 */

interface  A{
}
interface  B {
}
 class TypeVariableTest<K extends A & B, V> {


    public static void main(String[] args) {
        Type[] types =TypeVariableTest.class.getTypeParameters();

        for (Type type : types) {
            TypeVariable t = (TypeVariable) type;

            System.out.println("declaration:"+t.getGenericDeclaration());

            int size = t.getBounds().length;

            System.out.println("bound:"+t.getBounds()[size - 1]);

            System.out.println("name:"+t.getName() + "\n-------------分割线-------------");

        }


    }
}


