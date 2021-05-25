package edu.gdpu.spring.exception;

/**
 * @author mazebin
 * @date 2021年 02月24日 16:51:44
 * 抽象类
 */
public abstract class BeanException extends Exception {
    public BeanException(String message) {
        super(message);
    }
}