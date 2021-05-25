package edu.gdpu.MySSM.test.javaseTest;

import edu.gdpu.MySSM.test.Person;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mazebin
 * @date 2021年 02月28日 16:01:55
 */
//@Controller//随便添加的
public class ReflectTest {
    private Person person = new Person(11);

    @Test
    public void test4(){
        if(Say.class.isInterface()){
            Class<?>[] interfaces = Say.class.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                System.out.println("虽然我是接口，但是getInterfaces可获取不到我自己:"+anInterface);
            }
        }
    }
    @Test
    public void test() throws Exception{
        Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.javaseTest.Student");
        Student o = (Student) aClass.getDeclaredConstructor().newInstance();
        Field name = aClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(o,"binboy");
        Field age = aClass.getDeclaredField("age");
        age.setAccessible(true);
       age.set(o,21);
        Method getName = aClass.getDeclaredMethod("gotoSchool",Integer.class,String.class);
    }
    private Say binboy;
    @Test
    public void test2(){
        //测试动态代理+map
        Map<String,Object> beans = new ConcurrentHashMap<>();
        binboy = new Student(12, "binboy");
        beans.put("student",binboy);
        for (String s : beans.keySet()) {
            binboy= (Say) beans.get(s);
            System.out.println(binboy);
        }
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("我是动态代理");
                return method.invoke(binboy, args);
            }
        };

        Say o = (Say) Proxy.newProxyInstance(binboy.getClass().getClassLoader(), binboy.getClass().getInterfaces(), handler);
        o.saying();
        binboy=o;
        for (String s : beans.keySet()) {
            System.out.println(beans.get(s));
        }
    }
}

class Student implements Say {
   private int age;
    private String name;
    private String gotoSchool( Integer  s1,  String name) {
        System.out.println("go to school");
        return "去上学，好好学";
    }
    private  String getName(){
        return  this.name;
    }

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void saying() {

    }
}
interface  Doing{

}
interface  Say extends  Doing{
     void saying();
}

