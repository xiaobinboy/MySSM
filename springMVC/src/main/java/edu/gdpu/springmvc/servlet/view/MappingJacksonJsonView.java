package edu.gdpu.springmvc.servlet.view;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 04月13日 21:18:42
 * Json解析视图类 ,扫描到responseBody注解,就生成此视图，而不生成modelAndView对象
 */
public class MappingJacksonJsonView extends AbstractView {
    private   ObjectMapper mapper = new ObjectMapper();
    private   String content_type ="application/json";
    private JsonEncoding encoding = JsonEncoding.UTF8;
    /**
     * 这个参数就是返回的字符串封装类
     */
    private MappingJsonValue jsonValue;

    public MappingJacksonJsonView(MappingJsonValue jsonValue) {
        this.jsonValue = jsonValue;
    }

    /**
     *  controller.Method被ResponseBody注解修饰时，不生成ModelMap对象，而是直接生成MappingJsonValue对象
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
           response.setContentType(content_type);
           response.setCharacterEncoding(encoding.getJavaName());
           //如何获取value值,springMVC中responseBody注解  请参考 https://blog.csdn.net/qq_36434742/article/details/109681461
        String content = mapper.writeValueAsString(jsonValue.getValue().toString());
        //写回客户端
        response.getWriter().write(content);


    }
/**
 * 不需要设置到request域中，这个方法空实现就行了
 **/
   @Override
    public void exposeModelAsRequestAttributes(Map<String, ?> model, HttpServletRequest request) throws Exception {

    }

}