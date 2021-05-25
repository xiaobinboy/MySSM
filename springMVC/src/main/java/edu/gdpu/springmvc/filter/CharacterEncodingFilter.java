package edu.gdpu.springmvc.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:57:56
 */
public class CharacterEncodingFilter implements Filter {
    private String encoding;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**使用jdk1.8版本必须重写这两个方法
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(request,response);
    }
}