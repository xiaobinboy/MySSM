package edu.gdpu.spring.aop.support;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.FactoryBean;
import edu.gdpu.spring.aop.framework.AopProxyFactory;
import edu.gdpu.spring.aop.target.TargetSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月03日 17:29:16
 */
public class ProxyFactoryBean implements FactoryBean {
    private List<Advisor> advisors = new ArrayList<>();
    private  AopProxyFactory aopProxyFactory ;
    private TargetSource targetSource;
    private List<Advice> transactionAdvices = new ArrayList<>();
    public List<Advice> getTransactionAdvices() {
        return transactionAdvices;
    }

    public void setTransactionAdvices(List<Advice> transactionAdvices) {
        this.transactionAdvices = transactionAdvices;
    }
    public void setTransactionAdvices(Advice transactionAdvice) {
        this.transactionAdvices .add(transactionAdvice);
    }



    public ProxyFactoryBean(TargetSource targetSource) {
        this.targetSource = targetSource;
        this.aopProxyFactory=AopProxyFactory.getInstance();
    }
    public ProxyFactoryBean(TargetSource targetSource, Advisor advisor) {
        this.targetSource = targetSource;
        this. setAdvisors(advisor);
        this.aopProxyFactory=AopProxyFactory.getInstance();
    }
    public ProxyFactoryBean(TargetSource targetSource, Advice advice) {
        this.targetSource = targetSource;

        this.aopProxyFactory=AopProxyFactory.getInstance();
    }



    public ProxyFactoryBean(List<Advisor> advisors) {
        this.advisors = advisors;
        this.aopProxyFactory=AopProxyFactory.getInstance();
    }

    public ProxyFactoryBean() {
        this.aopProxyFactory=AopProxyFactory.getInstance();
    }





    public Object getAopProxy(){
        return  aopProxyFactory.createAopProxy(this);
    }
    @Override
    public Object getProxy(){
        //需要修改
        return aopProxyFactory.createAopProxy(this).getProxy(this);
    }
    public List<Advisor> getAdvisors(){
        return  this.advisors;
    }
    public void setAdvisors(Advisor advisor){
        advisors.add(advisor);
    }
    public void setAdvisors(List<Advisor> advisors){
        this.advisors=advisors;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

}
