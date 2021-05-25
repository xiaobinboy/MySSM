package edu.gdpu.spring.context.annotation;

import java.lang.annotation.*;

/**
 * @author mazebin
 * @date 2021年 02月15日 22:09:16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    String value() default "";
}