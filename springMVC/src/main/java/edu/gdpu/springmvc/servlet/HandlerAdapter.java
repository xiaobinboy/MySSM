package edu.gdpu.springmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
   Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
   Boolean support(Object handler) throws Exception;
}
