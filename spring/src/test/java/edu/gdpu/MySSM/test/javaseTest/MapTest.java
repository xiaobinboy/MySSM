package edu.gdpu.MySSM.test.javaseTest;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mazebin
 * @date 2021年 03月20日 12:44:43
 * 对象传的是引用地址，地址没变，内容变化，也会引起调用该对象的其他内容的变化。
 */
public class MapTest {
    @Test
    public void testMap(){
        Map<String,Student> map = new HashMap<>();
        Student bin = new Student(12, "bin");
        map.put("123", bin);
    Student target=null;
        for (String o : map.keySet()) {
            target=map.get(o);

        }
        target.setAge(13);
        for (String s : map.keySet()) {
            System.out.println(s+map.get(s));
            map.put(s,new Student(20,"boy"));
        }
        for (String s : map.keySet()) {
            System.out.println(s+map.get(s));
        }
    }
    private static List<Method> methods = new ArrayList<>();

    public static List<Method> getMethods() {
        return methods;
    }

    @Test
    public void testList() throws Exception{
        Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.javaseTest.Student");
        Method saying = aClass.getDeclaredMethod("saying");
        getMethods().add(saying);
        for (Method method : methods) {
            System.out.println(method);
        }

    }
    @Test
    public void testBean(){
        Map<String ,Object> beans = new ConcurrentHashMap<>();
        beans.put("1",new Student(12,"binboy"));
        for (String s : beans.keySet()) {
            System.out.println(s+beans.get(s));
        }
        beans.put("1","binboy");
        for (String s : beans.keySet()) {
            System.out.println(s+beans.get(s));
        }
    }


}