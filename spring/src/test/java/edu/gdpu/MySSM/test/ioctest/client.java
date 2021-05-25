package edu.gdpu.MySSM.test.ioctest;

import edu.gdpu.MySSM.test.ioctest.config.TestConfiguration;
import edu.gdpu.MySSM.test.ioctest.controller.TestController;
import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.postProcessor.facory.AnnotationPostProcessorFactory;
import org.junit.Test;

/**
 * @author mazebin
 * @date 2021年 03月02日 21:17:03
 * 测试spring的ioc整体功能
 */

public class client {

    @Test
    public void test() throws Exception {
        //下面这一段相当于@ContextConfiguration(classes=SpringConfiguration.class),但是这个注解我不想实现了
       AnnotationPostProcessor componentScanPostProcessor = AnnotationPostProcessorFactory.getPostProcessor("ComponentScanPostProcessor");

        //System.out.println(componentScanPostProcessor);
     Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.ioctest.config.TestConfiguration");
      componentScanPostProcessor.handle(aClass);

//        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();
        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();
        System.out.println(myApplicationContext);
        //myApplicationContext.printBeans();

        //myApplicationContext.printBeans();
        TestController testController = myApplicationContext.getBean("testcontroller");
        TestConfiguration testConfiguration = myApplicationContext.getBean("testconfiguration");


        System.out.println(testConfiguration.getUser());



    }
}