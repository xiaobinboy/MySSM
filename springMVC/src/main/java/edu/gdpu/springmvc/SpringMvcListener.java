package edu.gdpu.springmvc;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.exception.BeanExpressionException;
import edu.gdpu.spring.postProcessor.facory.AnnotationPostProcessorFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author mazebin
 * @date 2021年 05月02日 17:43:15
 */
public class SpringMvcListener implements ServletContextListener {
    private Class applicationContextClass;

    public void setApplicationContextClass(Class applicationContextClass) {
        this.applicationContextClass = applicationContextClass;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //容器初始化

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
     //容器摧毁
    }
    public ApplicationContext initWebApplicationContext(){
        AnnotationPostProcessor componentScanPostProcessor = AnnotationPostProcessorFactory.getPostProcessor("ComponentScanPostProcessor");
        try {

            componentScanPostProcessor.handle(applicationContextClass);
        } catch ( BeanExpressionException e ) {
            e.printStackTrace();
        }
        return  ApplicationContextFactory.getApplicationContext();
    }
}