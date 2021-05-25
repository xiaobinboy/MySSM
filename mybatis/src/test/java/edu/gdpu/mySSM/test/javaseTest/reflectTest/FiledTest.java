package edu.gdpu.mySSM.test.javaseTest.reflectTest;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author mazebin
 * @date 2021年 04月03日 15:54:25
 */
public class FiledTest {
    private String name;
    private int age;
    @Test
    public void test()throws Exception{
        Class<?> aClass = Class.forName("edu.gdpu.mySSM.test.javaseTest.reflectTest.FiledTest");
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }

        Field name = aClass.getDeclaredField("name");
        System.out.println(name);


    }
}