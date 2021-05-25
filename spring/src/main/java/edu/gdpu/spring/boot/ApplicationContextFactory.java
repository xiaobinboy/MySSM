package edu.gdpu.spring.boot;


import edu.gdpu.spring.context.AnnotationApplicationContext;
import edu.gdpu.spring.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author mazebin
 * @date 2021年 02月26日 00:57:06
 * 产生applicationContext的工厂
 */

public class ApplicationContextFactory {
    private static volatile ApplicationContext myApplicationContext;

    public static ApplicationContext getApplicationContext() {
        if (myApplicationContext == null) {
            synchronized (ApplicationContext.class) {

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }

                myApplicationContext = new AnnotationApplicationContext();
            }
        }

        return myApplicationContext;
    }



}
