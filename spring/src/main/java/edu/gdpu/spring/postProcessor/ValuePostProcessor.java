package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.context.annotation.Value;
import edu.gdpu.spring.core.util.ExpressionParser;
import edu.gdpu.spring.exception.BeanExpressionException;

import java.lang.reflect.Field;

/**
 * @author mazebin
 * @date 2021年 02月28日 15:11:50
 */
public class ValuePostProcessor implements AnnotationPostProcessor {

    /**
     * 解析value.value
     *
     * @param o
     * @return
     */
    @Override
    public Object handle(Object o) {
        Field field = (Field) o;
        String parse = null;
        if (field.isAnnotationPresent(Value.class)) {
            Value annotation = field.getAnnotation(Value.class);
            try {
                parse = ExpressionParser.parse(annotation.value());
            } catch ( BeanExpressionException e ) {
                e.printStackTrace();
            }
            return parse;
        }
        return null;
    }

}