package edu.gdpu.springmvc.servlet.mvc.method.support;

import java.lang.reflect.Parameter;

/**
 * @author mazebin
 */
public interface HandlerMethodArgumentResolver {
    /**
     * 解析方法参数
     * @param parameter
     * @return
     * @throws Exception
     */
    Object resolveArgument(Parameter parameter) throws Exception;
}
