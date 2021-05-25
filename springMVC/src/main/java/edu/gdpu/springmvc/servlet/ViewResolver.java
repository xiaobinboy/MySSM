package edu.gdpu.springmvc.servlet;

/**
 * @author:mab
 * 视图解析器 ,查看源码，发现它是通过转发来实现页面跳转的
 */
public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;

}
