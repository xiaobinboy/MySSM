package edu.gdpu.mySSM.test.javaseTest.reflectTest;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 04月02日 11:50:31
 * ParameterizedType详解
 * 参数化类型
 * <p>
 * public interface ParameterizedType extends Type {
 * Type[] getActualTypeArguments();//返回参数化类型的参数
 * <p>
 * Type getRawType();//获取变量的类型
 * <p>
 * Type getOwnerType();
 * }
 */
public class ParameterizedTypeTest {
    List<String> list1;
    List list2;
    Map<String, Long> map1;
    Map map2;
    Map.Entry<Long, Short> map3;

    //从打印结果看来,具有<>符号的变量是参数化类型
    @Test
    public void test() {
        Field[] declaredFields = ParameterizedTypeTest.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName() + ":" + (declaredField.getGenericType() instanceof ParameterizedType));
        }
    }

    @Test
    public void test2() {
        Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getGenericType() instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) f.getGenericType();
                System.out.print("变量：" + type.getTypeName() + "  ");//java.util.List<java.lang.String>
                Type[] types = type.getActualTypeArguments();
                for (Type type1 : types) {
                    System.out.print("类型：" + type1.getTypeName());//java.lang.String
                }
                System.out.println();
            }
        }
    }

    @Test
    public void test3() {
        Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getGenericType() instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) f.getGenericType();
                Type rawType = pType.getRawType();
                System.out.println("变量类型：" + rawType);//interface java.util.List
            }
        }
    }

    /**
     * Returns a {@code Type} object representing the type that this type is a member of
     * For example, if this type is {@code O<T>.I<S>},return a representation of {@code O<T>}
     *
     * 依据解释,我们知道
     *
     * O<T>.I<S>类型的变量,调用getOwnerType()会返回O<T>
     */
    @Test
    public void test4() {
        Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getGenericType() instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) f.getGenericType();
                Type ownerType = pType.getOwnerType();
                System.out.println(ownerType);

            }
        }
    }

}