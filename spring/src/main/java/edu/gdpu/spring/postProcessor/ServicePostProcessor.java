package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.Service;
import edu.gdpu.spring.core.util.BeanUtils;
import edu.gdpu.spring.exception.BeanException;
import edu.gdpu.spring.exception.BeanExpressionException;

/**
 * @author mazebin
 * @date 2021年 02月28日 19:42:22
 */
public class ServicePostProcessor implements AnnotationPostProcessor {
    @Override
    public Object handle(Object o) throws BeanExpressionException {
        Class cl = (Class) o;
        return register(cl);
    }

    @SuppressWarnings("unchecked")
    public Object register(Class aClass) {

        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();
        Object target = null;
        Service annotation = (Service) aClass.getAnnotation(Service.class);
        try {
            target = BeanUtils.createBean(aClass);
        } catch ( BeanException e ) {
            e.printStackTrace();
        }
        if (annotation.value().equals("")) {
            String simpleName = aClass.getSimpleName();
            String s = simpleName.toLowerCase();
            if (s.endsWith("impl")) {
                myApplicationContext.setBean(s.split("impl")[0], target);
            } else {
                myApplicationContext.setBean(s, target);
            }

        } else {
            myApplicationContext.setBean(annotation.value(), target);
        }
        return target;
    }
}