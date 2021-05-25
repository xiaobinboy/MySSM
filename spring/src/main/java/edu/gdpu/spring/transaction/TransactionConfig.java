package edu.gdpu.spring.transaction;

import java.lang.reflect.Method;

/**
 * @author mazebin
 * @date 2021年 03月22日 11:25:13
 */
public class TransactionConfig {
    //设置是否只读
    private   static boolean  readOnly =false;
    private  static int TransactionIsolation =4;
    //设置切面方法
    private static Method beforeMethod ;
    private static Method afterMethod;
    private static Method afterThrowMethod;
    private static  Class transactionManagerClass = DataSourceTransactionManager.class;

    public static Class getTransactionManagerClass() {
        return transactionManagerClass;
    }

    public static Method getBeforeMethod() {
        return beforeMethod;
    }

    public static void setBeforeMethod(Method beforeMethod) {
        TransactionConfig.beforeMethod = beforeMethod;
    }

    public static Method getAfterMethod() {
        return afterMethod;
    }

    public static void setAfterMethod(Method afterMethod) {
        TransactionConfig.afterMethod = afterMethod;
    }

    public static Method getAfterThrowMethod() {
        return afterThrowMethod;
    }

    public static void setAfterThrowMethod(Method afterThrowMethod) {
        TransactionConfig.afterThrowMethod = afterThrowMethod;
    }

    public static boolean isReadOnly() {
        return readOnly;
    }

    public static void setReadOnly(boolean readOnly) {
        TransactionConfig.readOnly = readOnly;
    }

    public static int getTransactionIsolation() {
        return TransactionIsolation;
    }

    public static void setTransactionIsolation(int transactionIsolation) {
        TransactionIsolation = transactionIsolation;
    }
}