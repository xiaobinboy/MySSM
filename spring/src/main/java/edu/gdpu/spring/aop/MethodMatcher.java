package edu.gdpu.spring.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean match(Method method) throws Exception;
}
