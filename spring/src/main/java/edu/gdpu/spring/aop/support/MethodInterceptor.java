package edu.gdpu.spring.aop.support;

import edu.gdpu.spring.aop.JoinPoint;

public interface MethodInterceptor {
    default Object invokeMethod() {
        return null;
    }

    default Object invokeMethod(JoinPoint joinPoint) {
        return null;
    }
}
