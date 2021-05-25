package edu.gdpu.springmvc.servlet.mvc.method.annotation;

import edu.gdpu.springmvc.method.HandlerMethod;
import edu.gdpu.springmvc.servlet.HandlerAdapter;
import edu.gdpu.springmvc.servlet.mvc.method.support.HandlerMethodArgumentResolver;
import edu.gdpu.springmvc.servlet.mvc.method.support.RequestParamMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mazebin
 * @date 2021年 04月10日 23:38:51
 * 在这个类中生成对应的封装类
 */
public  abstract class AbstractHandlerMethodAdapter implements HandlerAdapter {


    private HandlerMethodArgumentResolver argumentResolver;


    public AbstractHandlerMethodAdapter() {


        this.argumentResolver = new RequestParamMethodArgumentResolver();

    }



    public HandlerMethodArgumentResolver getArgumentResolver() {
        return argumentResolver;
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {


        return invokeHandlerMethod((HandlerMethod) handler,request,response);
    }

    @Override
    public  abstract Boolean support(Object handler) throws Exception ;


    /**
     * 执行方法
     * @param handlerMethod
     * @return
     */
    public abstract Object invokeHandlerMethod(HandlerMethod handlerMethod,HttpServletRequest request,HttpServletResponse response) throws Exception;


}