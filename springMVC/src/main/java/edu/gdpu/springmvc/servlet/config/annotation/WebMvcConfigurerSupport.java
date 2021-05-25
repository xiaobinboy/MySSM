package edu.gdpu.springmvc.servlet.config.annotation;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:51:26
 */
public  abstract class WebMvcConfigurerSupport implements WebMvcConfigurer{

    @Override
    public abstract void addResourceHandlers(ResourceHandlerRegistry registry);
}