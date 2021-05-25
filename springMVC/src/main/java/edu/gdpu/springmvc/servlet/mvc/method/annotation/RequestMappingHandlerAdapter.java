package edu.gdpu.springmvc.servlet.mvc.method.annotation;




import edu.gdpu.springmvc.bind.annotation.ResponseBody;
import edu.gdpu.springmvc.method.HandlerMethod;
import edu.gdpu.springmvc.method.MethodParameter;
import edu.gdpu.springmvc.servlet.ModelAndView;
import edu.gdpu.springmvc.servlet.mvc.method.support.HandlerMethodArgumentResolver;
import edu.gdpu.springmvc.servlet.view.MappingJacksonJsonView;
import edu.gdpu.springmvc.servlet.view.MappingJsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 04月12日 21:10:06
 * Spring Mvc默认的注解处理器适配器,这里仅有一个成员变量就是参数解析器
 */
public class RequestMappingHandlerAdapter extends  AbstractHandlerMethodAdapter {
private Logger logger = LoggerFactory.getLogger(getClass());
    public RequestMappingHandlerAdapter() {
        super();
    }

    /**
     * doDispatch方法中使用,返回类型有两种(ModelAndView and MappingJacksonJsonView),取决于controller.Method有没有被ResponseBody注解修饰
     */
    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.handle(request,response,handler);
    }

    @Override
    public Boolean support(Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            return true;
        }
        return  false;
    }

    /**
     * 这里的handlerMethod是一个空对象，需要使用参数解析器设置参数类型和参数名，使用
     * @param handlerMethod
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public Object invokeHandlerMethod(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HandlerMethodArgumentResolver argumentResolver = this.getArgumentResolver();
        Method method = handlerMethod.getMethod();
        logger.info("实际运行的controller类方法："+method.getName());
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            MethodParameter methodParameter = (MethodParameter) argumentResolver.resolveArgument(parameters[i]);
            handlerMethod.addMethodParameter(methodParameter);
        }
        List<MethodParameter> methodParameters = handlerMethod.getMethodParameters();
        //设置方法实际参数
        for (MethodParameter methodParameter : methodParameters) {
            Type methodParameterType = methodParameter.getMethodParameterType();
            Object actualArg = null;
            String parameterName = methodParameter.getMethodParameterName();
            if (methodParameterType.equals(String.class)) {
                actualArg = request.getParameter(parameterName);
            } else if (methodParameterType.equals(Integer.class)) {
                actualArg = Integer.valueOf(request.getParameter(parameterName));
            }
            if (actualArg != null) {
                handlerMethod.addMethodArg(actualArg);
            }

        }


         if(handlerMethod .getMethod().isAnnotationPresent(ResponseBody.class)){
             Object returnValue=  handlerMethod.getMethod().invoke(handlerMethod.getBean(),handlerMethod.getMethodArgs().toArray());

              return new MappingJacksonJsonView(new MappingJsonValue(returnValue));
         }
         else if(!handlerMethod.getMethod().isAnnotationPresent(ResponseBody.class) && isModelAndView(handlerMethod.getMethod())){

             return (ModelAndView) handlerMethod.getMethod().invoke(handlerMethod.getBean(), handlerMethod.getMethodArgs().toArray());
         }else if(!handlerMethod.getMethod().isAnnotationPresent(ResponseBody.class) && isString(handlerMethod.getMethod())){
             //返回值是String类型，自动转ModelAndView

               String viewName =(String) handlerMethod.getMethod().invoke(handlerMethod.getBean());

            logger.info("controller类方法返回的是String+viewName："+viewName);
            return getModelAndView(viewName);
             //logger.info("内部生成的ModelAndView:"+modelAndView);

        }
         return null;

    }
    private  boolean isModelAndView(Method method){
        if(method.getReturnType().equals(ModelAndView.class)){
            return  true;
        }else{
            return  false;
        }
    }
    private boolean isString(Method method){
        if(method.getReturnType().equals(String.class)){
            return  true;
        } else{
            return false;
        }
    }
    private ModelAndView getModelAndView(String viewName){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
//        System.out.println(modelAndView);
        return  modelAndView;
    }
}