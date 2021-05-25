package edu.gdpu.springmvc.servlet.config.annotation;

/**
 * @author mazebin
 * SpringMvc配置类接口,我的SpringMVC完全基于ApplicationContext,使用bean注解配置，所以算了
 */
public interface WebMvcConfigurer {
    /**
     * 添加静态资源过滤
     * @param registry
     */
    public  void addResourceHandlers(ResourceHandlerRegistry registry);

}
