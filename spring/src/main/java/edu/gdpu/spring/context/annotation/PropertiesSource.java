package edu.gdpu.spring.context.annotation;

import java.lang.annotation.*;

/**
 * @author mazebin
 * @date 2021年 02月28日 22:49:07
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertiesSource {
    String value() default "";

}