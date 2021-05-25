package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;

import edu.gdpu.spring.aop.support.ProxyFactoryBean;
import edu.gdpu.spring.exception.BeanExpressionException;



/**
 * @author mazebin
 * @date 2021年 03月22日 11:29:38
 */
public class EnableTransactionPostProcessor implements AnnotationPostProcessor {
    @Override
    public Object handle(Object o) throws BeanExpressionException {
        //获取list<transactionAdvice>;
        ProxyFactoryBean support = (ProxyFactoryBean) o;

        return support.getProxy();
    }
}