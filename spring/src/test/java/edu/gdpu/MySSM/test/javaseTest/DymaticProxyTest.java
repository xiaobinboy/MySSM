package edu.gdpu.MySSM.test.javaseTest;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author mazebin
 * @date 2021年 03月21日 11:50:30
 */
public class DymaticProxyTest implements InvocationHandler {
    private seller seller = new sellerImpl();
    @Test
    public void test(){


        seller o = (seller) Proxy.newProxyInstance(seller.getClass().getClassLoader(), seller.getClass().getInterfaces(), this::invoke);
        System.out.println(o+"...."+o.getClass().getName());
        o.sell();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

       Object var = method.invoke(seller, args);
        System.out.println("hello 动态代理");
        return var;
    }
}
interface seller{
    void sell();
        }
        class  sellerImpl implements seller{

            @Override
            public void sell() {
                System.out.println("卖东西");
            }
        }