package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.aop.support.ProxyFactoryBean;
import edu.gdpu.spring.exception.BeanExpressionException;




/**
 * @author mazebin
 * @date 2021年 03月20日 19:57:58
 */
public class EnableAopProxyPostProcessor implements AnnotationPostProcessor {

    @Override
    public Object handle(Object o) throws BeanExpressionException {
       //注入 advisedSupport获取proxy
        ProxyFactoryBean support = (ProxyFactoryBean) o;
        //返回代理对象
        return support.getProxy();
    }
}