package edu.gdpu.spring.aop.framework;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.JoinPoint;
import edu.gdpu.spring.aop.Signature;
import edu.gdpu.spring.aop.aspectj.DefaultSignature;
import edu.gdpu.spring.aop.aspectj.MethodInvocationProceedingJoinPoint;
import edu.gdpu.spring.aop.support.*;
import edu.gdpu.spring.aop.target.TargetSource;
import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.exception.UnKnowAdviceTypeException;
import edu.gdpu.spring.transaction.advice.TransactionAfterAdvice;
import edu.gdpu.spring.transaction.advice.TransactionAfterThrowAdvice;
import edu.gdpu.spring.transaction.advice.TransactionBeforeAdvice;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月03日 15:47:56
 * 基于cglib实现代理类,通过在configuration注解修饰的类中
 * 定义一个enableAop 来开启aop
 * 定义一个useCglib 来开启产生的代理类为cglib代理类,，而不是Jdk代理类
 * 代码的改进我们还需继续努力
 */
public class CglibAopProxy implements AopProxy, MethodInterceptor {
    private ProxyFactoryBean proxyFactoryBean;
    public CglibAopProxy(ProxyFactoryBean proxyFactoryBean) {
        this.proxyFactoryBean = proxyFactoryBean;
    }

    @Override
    public Object getProxy(ProxyFactoryBean proxyFactoryBean) {
      return Enhancer.create( proxyFactoryBean.getTargetSource().getaClass(),this);
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //获取通知器
        List<Advisor> advisors = proxyFactoryBean.getAdvisors();
        TargetSource targetSource = proxyFactoryBean.getTargetSource();
        //获取事务通知
        List<Advice> transactionAdvices = proxyFactoryBean.getTransactionAdvices();
        TransactionAfterAdvice transactionAfterAdvice = null;
        TransactionBeforeAdvice transactionBeforeAdvice = null;
        TransactionAfterThrowAdvice transactionAfterThrowAdvice = null;
        BeforeAdvice beforeAdvice = null;
        AfterAdvice afterAdvice = null;
        AfterReturnAdvice afterReturnAdvice =null;
        AfterThrowAdvice afterThrowAdvice = null;
        AroundAdvice aroundAdvice = null;
        for (Advisor advisor : advisors) {
            ExecutionPointcut pointcut = (ExecutionPointcut) advisor.getPointcut();

            if (!pointcut.match(method)) {
                continue;
            }
            Advice advice = advisor.getAdvice();
            //判断advice类型
            try {
                //强转
                if (advice instanceof BeforeAdvice) {
                    beforeAdvice = (BeforeAdvice) advice;

                }

                if (advice instanceof AfterAdvice) {
                    afterAdvice = (AfterAdvice) advice;

                }
                if (advice instanceof AfterReturnAdvice) {
                    afterReturnAdvice = (AfterReturnAdvice) advice;

                }
                if (advice instanceof AfterThrowAdvice) {
                    afterThrowAdvice = (AfterThrowAdvice) advice;

                }else if(advice instanceof  AroundAdvice){
                    aroundAdvice =(AroundAdvice) advice;

                }

            } catch ( Exception e ) {
                if (!(advice instanceof BeforeAdvice) || !(advice instanceof AfterAdvice) || !(advice instanceof AfterReturnAdvice)
                        || !(advice instanceof BeforeAdvice) || !(advice instanceof AroundAdvice)) {

                    throw new UnKnowAdviceTypeException("找不到输入的通知类型，请检查添加的注解是否添加错误");
                }
            }
        }
        if(transactionAdvices!=null && transactionAdvices.size()!=0){
            for (Advice transactionAdvice : transactionAdvices) {
                if(transactionAdvice instanceof  TransactionBeforeAdvice){
                    transactionBeforeAdvice =(TransactionBeforeAdvice) transactionAdvice;
                }
                if(transactionAdvice instanceof  TransactionAfterThrowAdvice){
                    transactionAfterThrowAdvice = (TransactionAfterThrowAdvice) transactionAdvice;
                }
                if(transactionAdvice instanceof  TransactionAfterAdvice){
                    transactionAfterAdvice = (TransactionAfterAdvice) transactionAdvice;
                }
            }
        }
        Object var1=null;
        ReflectUitls.makeAccessible(method);
        if(aroundAdvice ==null) {
            try {
                if (beforeAdvice != null) {
                    beforeAdvice.invokeMethod();
                }if(transactionBeforeAdvice!=null){
                    transactionBeforeAdvice.invokeMethod();
                }
                var1 = method.invoke(targetSource.getTarget(), args);
                if (afterAdvice != null) {
                    afterAdvice.invokeMethod();
                }
                if(transactionAfterAdvice !=null){
                    transactionAfterAdvice.invokeMethod();
                }
                if (afterReturnAdvice != null) {
                    return afterReturnAdvice.invokeMethod();
                }

            } catch ( Exception e ) {
                if (afterThrowAdvice != null) {
                    afterThrowAdvice.invokeMethod();
                }
                if(transactionAfterThrowAdvice !=null){
                    transactionAfterThrowAdvice.invokeMethod();
                }
            }
        }else if(aroundAdvice != null){
//环绕通知的实现方式
            //创建joinPoint对象
            Signature signature = new DefaultSignature(method.getName(),method.getGenericReturnType().getTypeName(),method.getParameterTypes());
            JoinPoint joinPoint = new MethodInvocationProceedingJoinPoint(ReflectUitls.getParameters(method),signature,targetSource.getTarget(),method);

            var1 = aroundAdvice.invokeMethod(joinPoint);
        }


        return var1;


    }
}