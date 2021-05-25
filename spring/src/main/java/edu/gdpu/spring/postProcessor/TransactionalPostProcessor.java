package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.exception.BeanExpressionException;
import edu.gdpu.spring.transaction.DataSourceTransactionManager;
import edu.gdpu.spring.transaction.TransactionConfig;
import edu.gdpu.spring.transaction.advice.TransactionAfterAdvice;
import edu.gdpu.spring.transaction.advice.TransactionAfterThrowAdvice;
import edu.gdpu.spring.transaction.advice.TransactionBeforeAdvice;
import edu.gdpu.spring.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月22日 11:14:13
 */
public class TransactionalPostProcessor implements AnnotationPostProcessor {
    @Override
    public Object handle(Object o) throws BeanExpressionException {
        ApplicationContext context = ApplicationContextFactory.getApplicationContext();

        List<Advice> adviceList = new ArrayList<>();

            Class cl = (Class) o;

            try {
                Transactional annotation = (Transactional) cl.getAnnotation(Transactional.class);
                //设置是否可读
                TransactionConfig.setReadOnly(annotation.readOnly());
                //设置隔离级别
                TransactionConfig.setTransactionIsolation(annotation.TransactionIsolation());
                //获取事务管理器方法
                Method[] mangerMethods = ReflectUitls.getDeclaredMethods(DataSourceTransactionManager.class);

                for (Method mangerMethod : mangerMethods) {

                    if (mangerMethod.getName().contains("before")) {
                        TransactionConfig.setBeforeMethod(mangerMethod);
                        TransactionBeforeAdvice transactionBeforeAdvice = new TransactionBeforeAdvice();
                        adviceList.add(transactionBeforeAdvice);

                    } else if (mangerMethod.getName().contains("after") && !mangerMethod.getName().contains("afterThrow")) {
                        TransactionConfig.setAfterMethod(mangerMethod);
                        adviceList.add(new TransactionAfterAdvice());
                    } else if (mangerMethod.getName().contains("afterThrow")) {
                        TransactionConfig.setAfterThrowMethod(mangerMethod);
                        adviceList.add(new TransactionAfterThrowAdvice());
                    }
                }
            } catch ( Exception e ) {
            }


        return adviceList;
    }
}