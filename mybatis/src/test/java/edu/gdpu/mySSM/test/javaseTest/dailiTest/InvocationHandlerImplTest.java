package edu.gdpu.mySSM.test.javaseTest.dailiTest;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author mazebin
 * @date 2021年 04月03日 10:52:31
 */
public class InvocationHandlerImplTest {

    /**
     * 测试参数转换
     */
    sayImpl say;
   @Test
   public void test(){
        say = new sayImpl();
       say o = (InvocationHandlerImplTest.say) Proxy.newProxyInstance(say.class.getClassLoader(), say.getClass().getInterfaces(), new InvocationHandlerImpl());
   o.sayHello("hello World");
   }
    interface  say{
       public void  sayHello(String hello);
    }
    public static  class sayImpl implements say{

        @Override
        public void sayHello(String hello) {
            System.out.println(hello);
        }
    }

    public static  class InvocationHandlerImpl implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("实际参数"+args[0]);
            System.out.println(args);
            Object value = (Object) args;
            System.out.println(value);
            Object[] values= (Object[]) value;
            System.out.println(values[0]);
            return null;
        }
    }
}
