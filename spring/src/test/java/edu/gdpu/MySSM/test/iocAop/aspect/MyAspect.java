package edu.gdpu.MySSM.test.iocAop.aspect;

import edu.gdpu.spring.aop.JoinPoint;
import edu.gdpu.spring.aop.annotation.*;
import edu.gdpu.spring.context.annotation.Component;

/**
 * @author mazebin
 * @date 2021年 03月17日 23:22:32
 */
@Component
@Aspect
public class MyAspect {
    @Pointcut("edu.gdpu.MySSM.test.iocAop.service.impl.*.*()")
    public void pt1(){

    }
//    @Before("pt1()")
//    public void before(){
//        System.out.println("myAspect before");
//    }
//    @After("pt1()")
//    public void after(){
//        System.out.println("myAspect after");
//    }
//    @AfterReturn("pt1()")
//    public void afterReturn(){
//        System.out.println("myAspect afterReturn");
//    }
//    @AfterThrow("pt1()")
//    public void afterThrowing(){
//        System.out.println("myAspect afterThrow");
    //    }

    @Around("pt1()")
    public void around(JoinPoint joinPoint){
        System.out.println("环绕通知-begin");
        try {
            joinPoint.proceed();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println("环绕通知-end");

    }
}