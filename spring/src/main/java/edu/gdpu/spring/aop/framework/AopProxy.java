package edu.gdpu.spring.aop.framework;

import edu.gdpu.spring.aop.support.ProxyFactoryBean;

/**
 * @author mazebin
 * @date 2021年 03月03日 12:01:01
 */
public interface AopProxy {
    //获取代理
    Object getProxy(ProxyFactoryBean proxyFactoryBean);
}