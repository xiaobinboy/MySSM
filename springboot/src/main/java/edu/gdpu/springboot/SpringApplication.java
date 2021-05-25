package edu.gdpu.springboot;

import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.ComponentScan;
import edu.gdpu.spring.core.util.PackageScanUtils;
import edu.gdpu.spring.exception.BeanExpressionException;
import edu.gdpu.spring.postProcessor.ComponentScanPostProcessor;
import edu.gdpu.spring.postProcessor.facory.AnnotationPostProcessorFactory;
import edu.gdpu.springboot.autoConfiguration.TomcatConfig;
import edu.gdpu.springboot.web.embedded.tomcat.TomcatServletWebServerFactory;
import edu.gdpu.springboot.web.servlet.server.ServletWebServerFactory;
import edu.gdpu.springboot.web.servlet.server.WebServer;

import java.util.Map;
import java.util.Set;

/**
 * @author mazebin
 * @date 2021年 05月09日 15:30:38
 */

public class SpringApplication {
    public void run(Class springApplicationClass)  {
 new ResourceBanner().printBanner();


        ComponentScanPostProcessor postProcessor = (ComponentScanPostProcessor) AnnotationPostProcessorFactory.getPostProcessor("ComponentScanPostProcessor");
//扫描所有第三方autoConfiguration包
        Set<Class> classes = PackageScanUtils.scanAllClasses("edu.gdpu.springboot.autoConfiguration");

        postProcessor.setThirdPartyClasses(classes);
        try {
            postProcessor.handle(springApplicationClass);
        } catch ( BeanExpressionException e ) {
            e.printStackTrace();
        }
        //获取tomcatConfig
        ApplicationContext context = ApplicationContextFactory.getApplicationContext();

        Map<String, Object> beans = context.getBeans();
        TomcatConfig tomcatConfig = null;
        for (String s : beans.keySet()) {
            if(beans.get(s) instanceof  TomcatConfig){
                tomcatConfig = (TomcatConfig)beans.get(s);
            }
        }


       TomcatServletWebServerFactory webServerFactory = (TomcatServletWebServerFactory) getWebServerFactory(tomcatConfig);


        //启动web服务
        getWebServer(webServerFactory);

    }



    public ServletWebServerFactory getWebServerFactory(TomcatConfig tomcatConfig){
        return  new TomcatServletWebServerFactory(tomcatConfig);
    }
    public WebServer getWebServer(ServletWebServerFactory webServerFactory){

        WebServer webServer = webServerFactory.getWebServer();
        webServer.start();
        return webServer;
    }

}