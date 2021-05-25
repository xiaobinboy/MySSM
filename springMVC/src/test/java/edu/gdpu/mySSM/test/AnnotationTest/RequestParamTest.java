package edu.gdpu.mySSM.test.AnnotationTest;

import edu.gdpu.springmvc.bind.annotation.RequestParam;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 04月10日 22:17:39
 */
public class RequestParamTest {
    @Test
    public void test() throws Exception{
        Class<?> aClass = Class.forName("edu.gdpu.mySSM.test.AnnotationTest.RequestParamTest");
        Method init = aClass.getMethod("init", String.class);
        Annotation[][] parameterAnnotations = init.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {

                if(annotation instanceof RequestParam){
                    RequestParam param = (RequestParam) annotation;
                    System.out.println(param.name());

                }
            }
        }
    }
    public  void init(@RequestParam(name = "username") String username){

    }
}