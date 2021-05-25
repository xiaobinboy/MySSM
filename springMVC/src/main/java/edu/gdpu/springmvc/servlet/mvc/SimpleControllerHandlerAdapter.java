package edu.gdpu.springmvc.servlet.mvc;

import edu.gdpu.springmvc.servlet.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:49:27
 * 实现controller接口方式的处理器适配器，了解即可
 */
public class SimpleControllerHandlerAdapter  implements HandlerAdapter {
    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return null;
    }

    @Override
    public Boolean support(Object handler) throws Exception {
        return null;
    }
}