package edu.gdpu.spring.transaction.advice;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.support.MethodInterceptor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.transaction.DataSourceTransactionManager;
import edu.gdpu.spring.transaction.TransactionConfig;

import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 03月22日 13:38:57
 */
public class TransactionAfterThrowAdvice implements Advice, MethodInterceptor {

    private static ApplicationContext context = ApplicationContextFactory.getApplicationContext();
    @Override
    public Object invokeMethod() {
        DataSourceTransactionManager transactionManger = context.getBean(DataSourceTransactionManager.class);

        Method afterThrowMethod = TransactionConfig.getAfterThrowMethod();

        ReflectUitls.makeAccessible(afterThrowMethod);
        if(transactionManger!=null) {
            return ReflectUitls.invokeMethod(afterThrowMethod, transactionManger);
        }
        return null;
    }
}