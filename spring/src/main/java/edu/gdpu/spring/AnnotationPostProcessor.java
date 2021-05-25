package edu.gdpu.spring;

import edu.gdpu.spring.exception.BeanExpressionException;

/**
 * @author mazebin
 * @date 2021年 02月25日 23:49:24
 * 相关注解的后置处理器
 */
public interface AnnotationPostProcessor {
    public Object handle(Object o) throws BeanExpressionException;
}