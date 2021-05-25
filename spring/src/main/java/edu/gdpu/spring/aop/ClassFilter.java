package edu.gdpu.spring.aop;


/**
 * @author mazebin
 * @date 2021年 03月03日 15:08:36
 */
public interface ClassFilter {
    boolean match(Class<?> cls) ;
}