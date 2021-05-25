package edu.gdpu.springmvc.servlet.mvc.method.annotation;

import edu.gdpu.springmvc.servlet.HandlerInterceptor;
import edu.gdpu.springmvc.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:54:49
 * Spring mvc拦截器的两种实现方式
 * 1.继承HandlerInterceptorAdapter
 * 2.实现HandlerInterceptor
 */
public  abstract  class HandlerInterceptorAdapter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            ModelAndView modelAndView) throws Exception {
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 Exception ex) throws Exception {
    }

}