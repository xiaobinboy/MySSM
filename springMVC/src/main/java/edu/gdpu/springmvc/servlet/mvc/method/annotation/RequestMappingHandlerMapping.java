package edu.gdpu.springmvc.servlet.mvc.method.annotation;

import edu.gdpu.springmvc.servlet.HandlerExecutionChain;
import edu.gdpu.springmvc.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mazebin
 * @date 2021年 04月10日 23:39:13
 * Spring mvc默认的注解处理器映射器
 */
public class RequestMappingHandlerMapping extends AbstractHandlerMapping {

    public RequestMappingHandlerMapping(Object defaultHandler) {
        super(defaultHandler);
    }

    /**
     * 返回handlerExecutionChain对象
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
     return    super.getHandler(request);
    }

}