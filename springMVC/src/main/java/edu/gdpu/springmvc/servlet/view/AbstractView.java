package edu.gdpu.springmvc.servlet.view;

import edu.gdpu.springmvc.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 04月16日 22:01:41
 */
public  abstract class AbstractView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
       exposeModelAsRequestAttributes(model,request);
    }
    public abstract   void exposeModelAsRequestAttributes(Map<String, ?> model, HttpServletRequest request) throws Exception;
}