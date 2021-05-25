package edu.gdpu.MySSM.test.aopTest;

import edu.gdpu.spring.aop.support.ProxyFactoryBean;
import edu.gdpu.spring.aop.support.Advisor;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author mazebin
 * @date 2021年 03月17日 14:28:46
 * 测试 通知器
 */
public class AdvisorTest {
    @Test
    public void testAdvisor(){
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        Advisor advisor = new Advisor();
        proxyFactoryBean.setAdvisors(advisor);
    }
    @Test
    public void testMethod() throws Exception{
        Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.aopTest.AdvisorTest");
        Method saying = aClass.getMethod("saying", String.class, int.class);
        System.out.println(saying);
        Parameter[] parameters = saying.getParameters();
        String[] strs = new String[parameters.length];
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getName()+parameter.getType().getName());
        }
        for (int i = 0; i < parameters.length; i++) {
           strs[i]=parameters[i].getType().getName()+" "+parameters[i].getName();

        }
        for (String str : strs) {
            System.out.println(str);
        }





    }
    public void saying(String name,int i){
        System.out.println("名字为："+name+"saying"+i+"次");
    }
}