package edu.gdpu.spring.exception;

/**
 * @author mazebin
 * @date 2021年 02月24日 16:57:47
 * Bean不允许被创建异常
 */
public class BeanIsNotAllowedExcepetion extends BeanCreatingException {
    public BeanIsNotAllowedExcepetion(String message) {
        super(message);
    }
}