package edu.gdpu.spring.exception;

/**
 * @author mazebin
 * @date 2021年 02月24日 16:56:54
 * ioc容器：HashMap map = new HashMap();
 * map的key也就是bean的id已经存在异常
 */
public class BeanExistedException extends BeanCreatingException {
    public BeanExistedException(String message) {
        super(message);
    }
}