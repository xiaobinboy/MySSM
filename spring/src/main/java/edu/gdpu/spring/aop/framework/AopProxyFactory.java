package edu.gdpu.spring.aop.framework;

import edu.gdpu.spring.aop.AopConfig;
import edu.gdpu.spring.aop.support.ProxyFactoryBean;

/**
 * @author mazebin
 * @date 2021年 03月03日 12:00:44
 */
public class AopProxyFactory {
    private static volatile AopProxy aopProxy ;
    private static volatile   AopProxyFactory aopProxyFactory;
    @SuppressWarnings("unchecked")
    public static synchronized AopProxyFactory getInstance() {
        if (aopProxyFactory == null) {
            aopProxyFactory = new AopProxyFactory();
        }
        return aopProxyFactory;
    }
    @SuppressWarnings("unchecked")
    public AopProxy createAopProxy(ProxyFactoryBean proxyFactoryBean){

        if (aopProxy == null&& AopConfig.isUseCglib()==false) {
            aopProxy = new JDKAopProxy(proxyFactoryBean);
        }else if(aopProxy == null&&AopConfig.isUseCglib()==true){
            aopProxy=new CglibAopProxy(proxyFactoryBean);
        }
        return aopProxy;
    }


}