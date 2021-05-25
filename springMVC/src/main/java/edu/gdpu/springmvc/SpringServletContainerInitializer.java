package edu.gdpu.springmvc;

import edu.gdpu.springmvc.context.WebApplicationInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author mazebin
 * @date 2021年 05月02日 18:10:31
 * /**通过@HandlesTypes将实现了WebApplicationInitializer接口的对象注入到SpringServletContainerInitializer，
 *    以便容器初始化的时候，初始化我们配置好的实现类。
 */
@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        for (Class<?> aClass : set) {
            try {
                if(!aClass.isInterface()&&!Modifier.isAbstract(aClass.getModifiers())){
                Object o = aClass.getDeclaredConstructor().newInstance();

                WebApplicationInitializer a = null;
                if(WebApplicationInitializer.class.isAssignableFrom(aClass) )
                { a = (WebApplicationInitializer) o;
                a.onStartup(servletContext);
                }
                }
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            } catch ( InvocationTargetException e ) {
                e.printStackTrace();
            } catch ( NoSuchMethodException e ) {
                e.printStackTrace();
            }
        }


    }
}