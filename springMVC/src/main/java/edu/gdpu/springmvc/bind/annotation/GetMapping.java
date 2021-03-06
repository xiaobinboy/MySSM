package edu.gdpu.springmvc.bind.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMapping {
    String value()default "";
}
