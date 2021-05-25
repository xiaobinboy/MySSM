package edu.gdpu.springmvc.servlet;


import edu.gdpu.springmvc.method.HandlerMethod;

import edu.gdpu.springmvc.servlet.config.annotation.ResourceHandlerRegistry;
import edu.gdpu.springmvc.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import edu.gdpu.springmvc.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import edu.gdpu.springmvc.servlet.view.MappingJacksonJsonView;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:45:10
 * doService方法和doDispatch方法
 * init方法-Servlet的初始化方法
 * initStrategy方法,这个方法进行初始化处理器映射器等组件
 */
public class DispatchServlet extends HttpServlet {
private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<HandlerMapping> handlerMappings =  new ArrayList<>();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();
    private List<ViewResolver> viewResolvers = new ArrayList<>();
    private List<HandlerExceptionResolver> handlerExceptionResolvers = new ArrayList<>();

    private ApplicationContext webApplicationContext;

    public DispatchServlet(ApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }


    @Override
    public void init() throws ServletException {
       logger.info("init 初始化DispatcherServlet");
        initStrategy(webApplicationContext);
    }

    public void initStrategy(ApplicationContext context) {
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initViewResolvers(context);
        initHandlerExceptionResolvers(context);
    }


    public void initHandlerMappings(ApplicationContext context) {

        scanHandlerMappings(context);
    }
    public void scanHandlerMappings(ApplicationContext context) {
        Map<String, Object> beans = context.getBeans();
        Object target = null;
        for (String s : beans.keySet()) {
            target = beans.get(s);
            if (target.getClass().isAnnotationPresent(Controller.class)) {
                RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping(target);
                handlerMappings.add(handlerMapping);
            }
        }
        logger.info("初始化HandlerMappings,大小为："+this.handlerMappings.size());
    }
    public  void initViewResolvers(ApplicationContext context){

        Map<String, Object> beans = context.getBeans();
        Object target = null;
        for (String s : beans.keySet()) {
            target = beans.get(s);
            if(ViewResolver.class.isAssignableFrom(target.getClass())){
                this.viewResolvers.add((ViewResolver) target);
            }
        }
        logger.info("初始化 viewResolvers,大小为："+this.viewResolvers.size());
    }

    /**
     * 初始化处理器适配器集合
     * @param context
     */
    public void initHandlerAdapters(ApplicationContext context) {

        int size = this.handlerMappings.size();
        for(int i =0; i<size;i++){
            RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
            handlerAdapters.add(handlerAdapter);
        }
        logger.info("初始化 HandlerAdapters,大小为："+this.handlerAdapters.size());
    }
    /**
     * 初始化异常处理器集合
     * @param context
     */
    public  void initHandlerExceptionResolvers(ApplicationContext context){

        Map<String, Object> beans = context.getBeans();
        Object target = null;
        for (String s : beans.keySet()) {
             target = beans.get(s);
             if(HandlerExceptionResolver.class .isAssignableFrom(target.getClass())){
                 HandlerExceptionResolver resolver = (HandlerExceptionResolver) target;
                 this.handlerExceptionResolvers.add(resolver);
             }
        }
        logger.info("初始化HandlerExceptionResolvers,大小为："+this.handlerExceptionResolvers.size());
    }

    /**
     * 获取处理器执行链
     *在doDispatch方法中调用
     * @param request
     * @return
     * @throws Exception
     */

    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {

        if (handlerMappings != null) {
            for (HandlerMapping mapping : handlerMappings) {
                HandlerExecutionChain handler = mapping.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    /** 获取处理器适配器
     * 参数类型说明：handlerMethod,从handlerExecutionChain获取,在doDispatch方法中调用
     * @param handler el:handlerExecutionChain.getHandler;handlerExecutionChain由getHandler方法获取
     * @return
     * @throws Exception
     */
    public  HandlerAdapter getHandlerAdapter(Object handler) throws Exception{

        if(handlerAdapters != null){
            for (HandlerAdapter handlerAdapter : handlerAdapters) {

                if(handlerAdapter.support(handler)){
                    return  handlerAdapter;
                }
            }
        }
       throw new ServletException(" no handlerFound exception");
    }

   public View resolverViewName(String viewName,Map<String,?> model) throws Exception {

        if(this.viewResolvers !=null){
            for (ViewResolver resolver : viewResolvers) {
                if(resolver != null){
                    View view = resolver.resolveViewName(viewName);
                    if(view !=null){
                        return  view;
                    }
                }
            }
        }
        return  null;
   }

   public void render(View view, Map<String,?> model,HttpServletRequest request,HttpServletResponse response) throws Exception{
       logger.info("执行view.render方法解析视图");
        view.render(model,request,response);

   }
   public ModelAndView processHandlerException(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex)throws Exception{

        ModelAndView exMv = null;
        if(this.handlerExceptionResolvers !=null){
            for (HandlerExceptionResolver exceptionResolver : handlerExceptionResolvers) {
                if(exceptionResolver !=null){
                    exMv =exceptionResolver.resolveException(request,response,handler,ex) ;
                    break;
                }
            }
        }
        if(exMv !=null&&exMv.getView()!=null){
            //解析异常原因对应的视图，如404-404.jsp
            render(exMv.getView(),exMv.getModelMap(),request,response);
        }else if(exMv == null){
            return  null;
        }else if( exMv != null && exMv.getViewName()!=null){
            View view = resolverViewName(exMv.getViewName(), exMv.getModelMap());
            render(view, exMv.getModelMap(),request,response);
        }
        return  exMv;
   }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        doService(request, response);
    }
    public void doService(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{

        doDispatch(request,response);
    }
     public  void doDispatch(HttpServletRequest request,HttpServletResponse response) throws ServletException ,IOException{
//获取uri,判断是否是静态资源，是静态资源就直接放行
         String requestURI = request.getRequestURI();
         String[] resourceLocations = ResourceHandlerRegistry.getResourceLocations();
         for (String resourceLocation : resourceLocations) {
               if(requestURI.contains(resourceLocation)){
                   logger.info("这些是静态资源，不需要添加拦截");
                   ServletOutputStream outputStream = response.getOutputStream();
                   File file = new File(resourceLocation);
//                   byte[] bytes = new byte[2048];
//                   FileInputStream inputStream = new FileInputStream(file);
//
                   System.out.println(requestURI);
                   return;
               }
         }

         HandlerExecutionChain chain = null;
         HandlerMethod hm = null;
         ModelAndView mv = null;
         try {
              chain = getHandler(request);
logger.info("获取到的 HandlerExecutionChain:"+chain+",+拦截器链："+chain.getHandlerInterceptors().size());
              hm = (HandlerMethod) chain.getHandler();
logger.info("获取到的HandlerMethod:"+hm);
              if(hm != null){
                  HandlerAdapter ha = getHandlerAdapter(hm);
logger.info("获取到的HandlerAdapter:"+ha);
                  //调用处理器责任链对象获取拦截器，执行拦截器.preHandle方法
                  if(!chain.applyPreHandle(request,response)){
                      return;
                  }
                  // 调用处理器适配器执行方法
                  System.out.println("匹配结果："+ha.support(hm));
                  Object handle = ha.handle(request, response, hm);

logger.info("执行handlerAdapter.handle方法返回结果：");
                  //返回值是ModelAndView对象
                  if(handle instanceof  ModelAndView) {
                      //强转成ModelAndView

                      mv = (ModelAndView) handle;
                      //chain.applyPostHandle(request,response,mv);
                      if(mv!= null &&mv.getViewName() !=null){
                          //生成对应视图
                          View view = resolverViewName(mv.getViewName(),mv.getModelMap());
                          //渲染视图
                          render(view,mv.getModelMap(),request,response);
                          //视图渲染完成,执行拦截器链方法
                          chain.triggerAfterCompletion(request,response,null);
                      }else if(mv != null && mv.getView()!=null){
                          //直接获取到view对象
                          View view = mv.getView();
                          //渲染视图
                          render(view,mv.getModelMap(),request,response);
                          //视图渲染完成,执行拦截器链方法
                          chain.triggerAfterCompletion(request,response,null);
                      }
                  }else if( handle instanceof  MappingJacksonJsonView){
                      //返回值不是ModelAndView对象,而是直接生成了MappingJacksonJsonView
                      MappingJacksonJsonView mappingJacksonJsonView = (MappingJacksonJsonView) handle;
                      //chain.applyPostHandle(request,response ,null);
                      render(mappingJacksonJsonView,null,request,response);
                      //视图渲染完成,执行拦截器链方法
                      chain.triggerAfterCompletion(request,response,null);
                  }

              }

         } catch ( Exception e ) {
             try {
                 if(chain !=null) {
                     chain.triggerAfterCompletion(request, response, e);
                     processHandlerException(request,response,chain.getHandler(),e);
                 }

             } catch ( Exception ex ) {

             }
         }
     }

}