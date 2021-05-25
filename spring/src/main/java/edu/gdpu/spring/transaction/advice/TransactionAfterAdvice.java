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
public class TransactionAfterAdvice implements Advice, MethodInterceptor {
    private static ApplicationContext context = ApplicationContextFactory.getApplicationContext();

    @Override
    public Object invokeMethod() {
        DataSourceTransactionManager transactionManger = context.getBean(DataSourceTransactionManager.class);

        Method afterMethod = TransactionConfig.getAfterMethod();
        ReflectUitls.makeAccessible(afterMethod);
        if(transactionManger!=null) {
            return ReflectUitls.invokeMethod(afterMethod, transactionManger);
        }
        return null;
    }
}