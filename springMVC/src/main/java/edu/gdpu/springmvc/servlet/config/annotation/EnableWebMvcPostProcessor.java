package edu.gdpu.springmvc.servlet.config.annotation;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.annotation.Configuration;

import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 05月01日 18:29:17
 */
public class EnableWebMvcPostProcessor implements AnnotationPostProcessor {

    @Override
    public Object handle(Object o) {
        Class cl = (Class)o;
        Class superclass = cl.getSuperclass();
        if(superclass!= WebMvcConfigurerSupport.class||!WebMvcConfigurer.class.isAssignableFrom(cl)){
            throw new IllegalArgumentException(cl.getName()+"未继承WebMvcConfigurerSupport类或未实现webConfigurer接口");
        }
        register(cl);
        return o;
    }

    private void register(Class cl){
        Map<Class, String> mapping = ApplicationContextFactory.getApplicationContext().getMapping();
        if(!cl.isAnnotationPresent(Configuration.class)){
            throw new IllegalArgumentException(cl.getName()+"未用@Configuration标注");
        }
        Configuration annotation = (Configuration)cl.getAnnotation(Configuration.class);
        if(annotation.value().equals("")){
            mapping.put(cl.getSuperclass(),cl.getSimpleName().toLowerCase());
        }else {
            mapping.put(cl.getSuperclass(),annotation.value());
        }
    }
}