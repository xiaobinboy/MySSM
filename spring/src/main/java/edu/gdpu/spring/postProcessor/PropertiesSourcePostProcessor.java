package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.exception.BeanExpressionException;
import edu.gdpu.spring.context.annotation.PropertiesSource;

/**
 * @author mazebin
 * @date 2021年 02月28日 23:31:47
 */
public class PropertiesSourcePostProcessor implements AnnotationPostProcessor {

    @Override
    public Object handle(Object o) throws BeanExpressionException {
        Class cl = (Class) o;
        String propertiesname = null;
        if (cl.isAnnotationPresent(PropertiesSource.class)) {
            PropertiesSource annotation = (PropertiesSource) cl.getAnnotation(PropertiesSource.class);

            if (annotation.value().equals("")) {
                propertiesname = "application.properties";
            } else {
                propertiesname = annotation.value();
            }
        } else {
            propertiesname = "application.properties";
        }
        return propertiesname;
    }
}