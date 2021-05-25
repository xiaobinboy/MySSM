package edu.gdpu.ibatis.annotation;

import java.lang.annotation.*;

/**
 * @author mazebin
 * @date 2021年 03月29日 18:01:59
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ResultMap {
    String id();
}