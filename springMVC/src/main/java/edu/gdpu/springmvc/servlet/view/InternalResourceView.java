package edu.gdpu.springmvc.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 04月13日 21:18:05
 * Jsp解析视图
 */
public class InternalResourceView extends AbstractView {
    /**
     * 这个URL怎么生成
     * url = InternalResourceView.prefix +viewName + InternalResourceView.suffix
     * el:/pages/+login+/.jsp
     */
    String url;

    public InternalResourceView(String url) {
        this.url = url;
    }

    public InternalResourceView() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * dispatchServlet.render()方法会调用到这个方法
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //将数据设置到request域中
        //这个model是modelAndView里面的model
        exposeModelAsRequestAttributes(model,request);
        //转发到jsp页面
        String dispatchPath = getUrl();
        request.getRequestDispatcher(dispatchPath).forward(request,response);
    }
    @Override
    public  void exposeModelAsRequestAttributes(Map<String,?> model,HttpServletRequest request){
if(model != null){
    model.forEach((name,value)->{
        if(value!=null) {
            request.setAttribute(name, value);
        }else{
            request.removeAttribute(name);
        }
    });
}
    }


}