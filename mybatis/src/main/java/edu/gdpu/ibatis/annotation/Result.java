package edu.gdpu.ibatis.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Result {
    String id();
    String column();
    String property();
//    One one() default @One;
//
//    Many many() default @Many;
}
