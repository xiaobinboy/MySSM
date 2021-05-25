package edu.gdpu.MySSM.test.javaseTest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 03月18日 14:51:05
 */
public class StaticFileldTest {
    public static Map<String, String> getBeans() {
        return beans;
    }

    private static Map<String,String> beans =new HashMap();
    @Test
    public void test(){
        beans.put(new String("123"),new String("345"));
        for (String s : beans.keySet()) {
            System.out.println(s+beans.get(s));
        }
        test1();
    }
    @Test
    public void test1(){
        Map<String, String> beans = new StaticFileldTest().getBeans();
        for (String s : beans.keySet()) {
            System.out.println(s+beans.get(s));
        }
    }

}