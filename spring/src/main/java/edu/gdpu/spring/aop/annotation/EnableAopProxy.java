package edu.gdpu.spring.aop.annotation;

import java.lang.annotation.*;

/**
 * 开启aop功能
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAopProxy {
    boolean useCglib() default  false;
}
