package edu.gdpu.springmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mazebin
 * 异常处理器
 */
public interface HandlerExceptionResolver {
    /**
     * 配置全局异常处理方法
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    ModelAndView resolveException(HttpServletRequest request , HttpServletResponse response,Object handler,Exception ex) ;
}
