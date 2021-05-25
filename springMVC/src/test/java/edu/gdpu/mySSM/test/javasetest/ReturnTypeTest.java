package edu.gdpu.mySSM.test.javasetest;

import edu.gdpu.springmvc.servlet.ModelAndView;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 04月17日 14:32:53
 */
public class ReturnTypeTest {
    @Test
    public void test()throws Exception {
        Class<?> aClass = Class.forName("edu.gdpu.mySSM.test.javasetest.ReturnTypeTest");
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if(method.getReturnType().equals(String.class)){
                System.out.println("返回string");
            }else if(method.getReturnType().equals(ModelAndView.class)){
                System.out.println("返回ModelAndView");
            }
        }

    }
    public String init(){
        return  "hello";
    }
    public ModelAndView getModelAndView(){
        ModelAndView modelAndView = new ModelAndView();

        return  modelAndView;
    }
}