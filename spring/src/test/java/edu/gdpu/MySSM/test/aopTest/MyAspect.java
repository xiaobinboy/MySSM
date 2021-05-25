package edu.gdpu.MySSM.test.aopTest;

import edu.gdpu.spring.aop.annotation.*;
import edu.gdpu.spring.context.annotation.Component;

/**
 * @author mazebin
 * @date 2021年 03月17日 23:22:32
 */
@Component
@Aspect
public class MyAspect{
    @Pointcut("edu.gdpu.MySSM.test.aopTest.*.*()")
    public void pt1(){

    }
    @Before("pt1()")
    public void before(){
        System.out.println("myAspect before");
    }
    @After("pt1()")
    public void after(){
        System.out.println("myAspect after");
    }
    @AfterReturn("pt1()")
    public void afterReturn(){
        System.out.println("myAspect afterReturn");
    }
    @AfterThrow("pt1()")
    public void afterThrowing(){
        System.out.println("myAspect afterThrow");
    }
}