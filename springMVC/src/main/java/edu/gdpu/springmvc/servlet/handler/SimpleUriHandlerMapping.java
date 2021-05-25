package edu.gdpu.springmvc.servlet.handler;

import edu.gdpu.springmvc.servlet.HandlerExecutionChain;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:50:02
 * 非注解方式实现，了解即可
 */
public class SimpleUriHandlerMapping extends AbstractHandlerMapping {
    public SimpleUriHandlerMapping(Object defaultHandler) {
        super(defaultHandler);
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        return null;
    }
}