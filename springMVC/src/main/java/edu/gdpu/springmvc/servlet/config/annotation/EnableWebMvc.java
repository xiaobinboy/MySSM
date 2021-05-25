package edu.gdpu.springmvc.servlet.config.annotation;

import java.lang.annotation.*;

/**
 * @author mazebin
 * @date 2021年 02月23日 22:42:08
 * 加在类上表示这是一个MVC配置类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
//@Import({DelegatingWebMvcConfiguration.class})
public @interface EnableWebMvc {
}