package edu.gdpu.springmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:46:12
 */
public class HandlerExecutionChain {
    /**
     * spring mvc中这个变量是handlerMethod类型
     */
    private Object handler;
    private List<HandlerInterceptor> handlerInterceptors ;
    private Integer interceptorIndex = 0;

    public HandlerExecutionChain(Object handler) {
      this(handler,(List<HandlerInterceptor>) null);
    }

    public HandlerExecutionChain(Object handler, List<HandlerInterceptor> handlerInterceptors) {
        this.handler = handler;
        this.handlerInterceptors = handlerInterceptors;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public List<HandlerInterceptor> getHandlerInterceptors() {
        return handlerInterceptors;
    }

    public void setHandlerInterceptors(List<HandlerInterceptor> handlerInterceptors) {
        this.handlerInterceptors = handlerInterceptors;
    }

    public void addHandlerInterceptor(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptors.add(handlerInterceptor);
    }

    /**
     * 执行拦截器链preHandle方法
     */
    public boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (this.getHandlerInterceptors() != null&&this.getHandlerInterceptors().size()!=0) {
            for (int i = 0; i < this.handlerInterceptors.size(); i++) {
                {
                    if (handlerInterceptors.get(i) != null) {
                        if (!handlerInterceptors.get(i).preHandle(request, response, this.getHandler())) {
                           triggerAfterCompletion(request,response,null);
                            return false;
                        }
                        this.interceptorIndex = i;

                    }
                }
            }
        }
        return true;
    }

    /**
     * 执行拦截器链postHandle方法
     */
    public void applyPostHandle(HttpServletRequest request,HttpServletResponse response,ModelAndView mv) throws Exception{
        if(this.getHandlerInterceptors() !=null){
            for (int i = this.getHandlerInterceptors().size(); i >=0; i--) {
                 if(this.getHandlerInterceptors().get(i)!=null){

                     this.getHandlerInterceptors().get(i).postHandle(request,response,this.getHandler(),mv);
                 }

            }
        }
    }

    /**
     * 执行拦截器链 afterCompletion方法
     */
    public void triggerAfterCompletion(HttpServletRequest request,HttpServletResponse response,Exception ex) throws Exception{
             for(int i= this.interceptorIndex;i>=0;i--){
                 HandlerInterceptor interceptor = this.getHandlerInterceptors().get(i);
                 interceptor.afterCompletion(request,response,this.getHandler(),ex);
             }
    }

}