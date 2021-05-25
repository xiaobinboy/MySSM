package edu.gdpu.springmvc.context;

import edu.gdpu.springmvc.SpringMvcListener;
import edu.gdpu.springmvc.servlet.DispatchServlet;
import edu.gdpu.spring.context.ApplicationContext;


import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author mazebin
 * @date 2021年 05月01日 16:24:38
 * 继承这个类可以取代web.xml的作用
 */
public  abstract class SpringWebApplicationInitializer implements WebApplicationInitializer {


    //设置Spring配置类
    private  Class applicationContextClass;
    public void  setApplicationContextClass(){
        this.applicationContextClass  = getApplicationContextClass();
    }

    /**
     * 设置配置类
     * @return
     */
    public abstract Class getApplicationContextClass();
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //添加监听

        //注册DisPatcherServlet
        registerDispatcherServlet(servletContext);


    }
    public  ServletContextListener registerListener(ServletContext servletContext){
        SpringMvcListener listener = new SpringMvcListener();
        this.setApplicationContextClass();
        listener.setApplicationContextClass(applicationContextClass);
        servletContext.addListener(listener);
        return listener;
    }
    public abstract String getServletMappings();

    public  void registerDispatcherServlet(ServletContext servletContext){
        ApplicationContext webApplicationContext = createWebApplicationContext(servletContext);

        DispatchServlet dispatchServlet = createDispatchServlet(webApplicationContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher",dispatchServlet);
         registration.setLoadOnStartup(1);
         registration.addMapping(getServletMappings());
         //注册过滤器
        Filter[] servletFilters = getServletFilters();
        for (Filter servletFilter : servletFilters) {
            registerServletFilters(servletContext,servletFilter);
        }

    }
    public ApplicationContext createWebApplicationContext(ServletContext servletContext){
        SpringMvcListener listener = (SpringMvcListener) registerListener(servletContext);
        ApplicationContext applicationContext =listener.initWebApplicationContext();
        return  applicationContext;
    }
    public FilterRegistration.Dynamic registerServletFilters(ServletContext servletContext,Filter filter){
        FilterRegistration.Dynamic dynamic = servletContext.addFilter(filter.getClass().getSimpleName(), filter);
        //设置拦截路径，拦截方式
    dynamic.addMappingForUrlPatterns(getDispatcherType(),true,"/*");
        return dynamic;
    }
    public EnumSet<DispatcherType> getDispatcherType(){
        return EnumSet.of(DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR,DispatcherType.ASYNC);
    }
    public DispatchServlet createDispatchServlet(ApplicationContext webApplicationContext){
        return  new DispatchServlet(webApplicationContext);
    }
    /**
     *     设置字符编码过滤器
     */
    public abstract Filter[] getServletFilters();


}