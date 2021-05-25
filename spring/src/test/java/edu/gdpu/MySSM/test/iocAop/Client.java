package edu.gdpu.MySSM.test.iocAop;

import edu.gdpu.MySSM.test.iocAop.service.TestService;
import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.postProcessor.facory.AnnotationPostProcessorFactory;
import org.junit.Test;

/**
 * @author mazebin
 * @date 2021年 03月20日 23:19:21
 */
public class Client {
    @Test
    public  void test() throws  Exception{
        AnnotationPostProcessor componentScanPostProcessor = AnnotationPostProcessorFactory.
                getPostProcessor("ComponentScanPostProcessor");
        Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.iocAop.config.TestConfiguration");
        
        componentScanPostProcessor.handle(aClass);
        ApplicationContext context = ApplicationContextFactory.getApplicationContext();
       // context.printBeans();
        TestService testservice =context.getBean("testservice");
        //context.printBeans();
       //System.out.println("生成的代理类型："+testservice.getClass().getName());
         testservice.print();
    }

}