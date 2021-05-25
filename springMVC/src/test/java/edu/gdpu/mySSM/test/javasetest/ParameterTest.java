package edu.gdpu.mySSM.test.javasetest;

import edu.gdpu.springmvc.bind.annotation.RequestParam;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 04月12日 23:57:54
 */
public class ParameterTest {
   @Test
    public void test() throws Exception{
       Class<?> aClass = Class.forName("edu.gdpu.mySSM.test.javasetest.ParameterTest");
       Method init = aClass.getMethod("init",String.class);
       Parameter[] parameters = init.getParameters();
       for (Parameter parameter : parameters) {

           System.out.println(parameter.getType());
           System.out.println(parameter.isAnnotationPresent(RequestParam.class));
           System.out.println(parameter.getAnnotation(RequestParam.class).name());
       }
       List<Parameter> parameters1 = Arrays.asList(parameters);
       for (Parameter parameter : parameters1) {
           System.out.println(parameter.getType());
       }

   }
    public void init(@RequestParam(name = "str")String str){

    }
}