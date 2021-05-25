package edu.gdpu.MySSM.test.javaseTest;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mazebin
 * @date 2021年 03月05日 00:05:19
 */
public class RegExpTest {
    @Test
    public void test() throws Exception{
         Pattern classNamePattern = Pattern.compile("[a-zA-Z.*]*");
        Matcher matcher = classNamePattern.matcher("edu.gdpu.*.impl");
        boolean b = matcher.find();
        System.out.println(b);
        String s="edu.gdpu.*.impl";
        boolean matches = s.matches("[a-zA-Z.*]*");
        System.out.println(matches);
        Method test = Class.forName("edu.gdpu.MySSM.test.javaseTest.RegExpTest").getMethod("test");
        System.out.println(test.getName());

    }
    @Test
    public void testPointcut(){
       String classExp ="aop.service.impl.([\\w[^\\.]]*)";
       String name= "edu.gdpu.MySSM.test.ioc_aop.service.impl.TestServiceImpl";
        boolean matches = name.matches(classExp);
        System.out.println(matches);
    }
}