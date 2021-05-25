package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.Component;
import edu.gdpu.spring.core.util.BeanUtils;
import edu.gdpu.spring.exception.BeanException;
import edu.gdpu.spring.exception.BeanExpressionException;

/**
 * @author mazebin
 * @date 2021年 02月28日 19:42:22
 */
public class ComponentPostProcessor implements AnnotationPostProcessor {
    @Override
    public Object handle(Object o) throws BeanExpressionException {
        Class cl = (Class) o;
        return register(cl);
    }

    @SuppressWarnings("unchecked")
    public Object register(Class aClass) {

        ApplicationContext applicationContext = ApplicationContextFactory.getApplicationContext();
        Object target = null;
        Component annotation = (Component) aClass.getAnnotation(Component.class);
        try {
            target = BeanUtils.createBean(aClass);
        } catch ( BeanException e ) {
            e.printStackTrace();
        }
        if (annotation.value().equals("")) {
            String s = aClass.getSimpleName().toLowerCase();
            if (s.endsWith("impl")) {
                applicationContext.setBean(s.split("impl")[0], target);
            } else {
                applicationContext.setBean(s, target);
            }

        } else {
            applicationContext.setBean(annotation.value(), target);
        }
        return target;
    }
}