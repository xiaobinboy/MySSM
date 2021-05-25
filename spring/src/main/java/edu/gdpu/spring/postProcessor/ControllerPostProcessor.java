package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.Controller;
import edu.gdpu.spring.core.util.BeanUtils;
import edu.gdpu.spring.exception.BeanException;
import edu.gdpu.spring.exception.BeanExpressionException;

/**
 * @author mazebin
 * @date 2021年 02月28日 19:16:51
 */
public class ControllerPostProcessor implements AnnotationPostProcessor {
    @Override
    public Object handle(Object o) throws BeanExpressionException {
        Class cl = (Class) o;
        return register(cl);
    }

    public Object register(Class aClass) {

        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();
        Object target = null;
        Controller annotation = (Controller) aClass.getAnnotation(Controller.class);
        try {
            target = BeanUtils.createBean(aClass);
        } catch ( BeanException e ) {
            e.printStackTrace();
        }
        if (annotation.value().equals("")) {
            myApplicationContext.setBean(aClass.getSimpleName().toLowerCase(), target);
        } else {
            myApplicationContext.setBean(annotation.value(), target);
        }
        return target;
    }
}