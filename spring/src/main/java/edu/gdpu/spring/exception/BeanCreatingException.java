package edu.gdpu.spring.exception;

/**
 * @author mazebin
 * @date 2021年 02月24日 16:54:17
 * Bean创建期间异常
 */
public class BeanCreatingException extends BeanException {
    public BeanCreatingException(String message) {
        super(message);
    }
}