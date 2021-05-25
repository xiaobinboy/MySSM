package edu.gdpu.spring.transaction.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {

    boolean readOnly() default  false;
    //mysql默认的隔离级别
    int TransactionIsolation() default  4;//1 2 4 8

}

