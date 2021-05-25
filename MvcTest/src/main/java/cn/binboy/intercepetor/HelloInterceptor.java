package cn.binboy.intercepetor;

import edu.gdpu.spring.context.annotation.Component;
import edu.gdpu.springmvc.servlet.ModelAndView;
import edu.gdpu.springmvc.servlet.mvc.method.annotation.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mazebin
 * @date 2021年 05月11日 18:57:01
 */
@Component
public class HelloInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("我是拦截器.preHandle");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("我是拦截器.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("我是拦截器.afterCompletion");
    }
}