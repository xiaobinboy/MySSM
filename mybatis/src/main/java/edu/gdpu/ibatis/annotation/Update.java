package edu.gdpu.ibatis.annotation;

import java.lang.annotation.*;

/**
 * @author mazebin
 * @date 2021年 02月07日 22:47:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Update {
    String value();
}