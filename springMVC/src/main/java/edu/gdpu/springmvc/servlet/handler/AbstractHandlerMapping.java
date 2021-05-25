package edu.gdpu.springmvc.servlet.handler;

import edu.gdpu.springmvc.bind.annotation.GetMapping;
import edu.gdpu.springmvc.bind.annotation.PostMapping;
import edu.gdpu.springmvc.method.HandlerMethod;
import edu.gdpu.springmvc.servlet.HandlerExecutionChain;
import edu.gdpu.springmvc.servlet.HandlerInterceptor;
import edu.gdpu.springmvc.servlet.HandlerMapping;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.Controller;
import edu.gdpu.spring.core.util.ReflectUitls;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mazebin
 * @date 2021年 04月12日 21:31:22
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {
  /**准备好相关容器
   *
   */
  private static  Map<String,Method> postMap = new ConcurrentHashMap<>();
  private static Map<String,Method> getMap = new ConcurrentHashMap<>();
  private static ApplicationContext context = ApplicationContextFactory.getApplicationContext();
  /**
   *相对应的Controller类
   */
  private Object defaultHandler;

  public AbstractHandlerMapping(Object defaultHandler) {

    this.defaultHandler = defaultHandler;
  }

    public static Map<String, Method> getPostMap() {
        return postMap;
    }



    public static Map<String, Method> getGetMap() {
        return getMap;
    }



    public Object getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(Object defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
      //扫描ioc容器
      scanMapping(context.getBeans());
      //转型为HandlerMethod
      Object handler = getHandlerInternal(request);
      if(handler == null){
          handler = getDefaultHandler();
      }
        ArrayList<HandlerInterceptor> objects = new ArrayList<>();
        HandlerExecutionChain chain = new HandlerExecutionChain(handler,objects);

scanInterceptor(context.getBeans(),chain);
    return chain;
    }

    /**
     * 初始化url,method映射容器
     * @param beans
     */
    private void scanMapping(Map<String, Object> beans){
      
      Object target = null;
      for (String s : beans.keySet()) {
        target = beans.get(s);
        if(target.getClass().isAnnotationPresent(Controller.class)){
          Method[] methods = ReflectUitls.getDeclaredMethods(target.getClass());
          for (Method method : methods) {
            if(method.isAnnotationPresent(GetMapping.class)) {
              GetMapping getMapping = method.getAnnotation(GetMapping.class);
              getMap.put(getMapping.value(), method);
            }else if(method.isAnnotationPresent(PostMapping.class)){
             PostMapping postMapping = method.getAnnotation(PostMapping.class);
             postMap.put(postMapping.value(),method);
            }
          }
        }
      }
    }


    /**
     * 获取handlerMethod(bean,method)(暂未注入方法参数名，方法参数类型，方法参数值),存入handlerExecutionChain
     * @param request
     * @return
     */
    public Object getHandlerInternal(HttpServletRequest request){
      String uri = request.getRequestURI();
      Method method = null;
      if(getMap.containsKey(uri)){
           method = getMap.get(uri);
      }else if( postMap.containsKey(uri)){
          method = postMap.get(uri);
      }
      return  new HandlerMethod(defaultHandler,method);
    }
    private   void scanInterceptor(Map<String,Object> beans ,HandlerExecutionChain chain){
    HandlerInterceptor target = null;
      for (String s : beans.keySet()) {

        if(beans.get(s) instanceof  HandlerInterceptor){
            target = (HandlerInterceptor) beans.get(s);
          chain.addHandlerInterceptor((HandlerInterceptor) target);
        }
      }
    }



}