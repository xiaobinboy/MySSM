package edu.gdpu.springmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author mazebin
 */
public interface View  {
    void render( Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
