package edu.gdpu.springmvc.bind.annotation;

import java.lang.annotation.*;

/**
 * @author mazebin
 * @date 2021年 04月10日 20:16:00
 */
@Documented
@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    String name() default "";

}