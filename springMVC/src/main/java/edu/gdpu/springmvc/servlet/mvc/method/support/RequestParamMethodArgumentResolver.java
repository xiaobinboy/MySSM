package edu.gdpu.springmvc.servlet.mvc.method.support;

import edu.gdpu.springmvc.bind.annotation.RequestParam;
import edu.gdpu.springmvc.method.MethodParameter;

import java.lang.reflect.Parameter;

/**
 * @author mazebin
 * @date 2021年 04月12日 23:43:03
 */
public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 解析参数的requestParam注解，获取参数名
     **/
    @Override
    public Object resolveArgument(Parameter parameter) throws Exception {
      String name = null;
        if(parameter .isAnnotationPresent(RequestParam.class)){
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
             name = requestParam.name();
        }
        //获取参数类型
if(name !=null) {
    return new MethodParameter(parameter.getType(), name);

}
else{
    return  null;
}


    }
}