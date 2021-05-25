package edu.gdpu.spring.context.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)//允许通过反射创建
@Documented
public @interface Bean {
    String value() default "";
}
