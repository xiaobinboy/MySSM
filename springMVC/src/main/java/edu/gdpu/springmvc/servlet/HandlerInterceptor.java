package edu.gdpu.springmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**@author mazebin
 * 拦截器接口
 */
public interface HandlerInterceptor {
    /**
     * 前置拦截，在controller.Method未执行之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

    /**
     * 中置拦截，在controller.Method方法执行完成之后，视图未跳转之后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception ;

    /**
     * 后置拦截，在视图渲染完成后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception ;
}
