package edu.gdpu.springmvc.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理器映射器
 */
public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
