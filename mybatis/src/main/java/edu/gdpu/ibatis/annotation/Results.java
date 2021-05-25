package edu.gdpu.ibatis.annotation;

import java.lang.annotation.*;

/**
 * @author mazebin
 * @date 2021年 02月07日 22:48:08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Results {
    String id() default "";
    Result[] value() default {};
}