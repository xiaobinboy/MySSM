package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.Bean;
import edu.gdpu.spring.context.annotation.Configuration;

import edu.gdpu.spring.core.util.BeanUtils;
import edu.gdpu.spring.core.util.PropertiesLoaderUtils;
import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.exception.BeanException;
import edu.gdpu.spring.exception.BeanExpressionException;
import edu.gdpu.spring.exception.BeanUnSupportedExcepetion;
import edu.gdpu.spring.postProcessor.facory.AnnotationPostProcessorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Properties;

/**
 * @author mazebin
 * @date 2021年 02月28日 20:48:32
 */
public class ConfigurationPostProcessor implements AnnotationPostProcessor {
    @Override
    public Object handle(Object o) throws BeanExpressionException {
        Class cl = (Class) o;
        Object register = register(cl);//这个就是被configuration注解修饰的类的实例(调用的构造方法实现哦)

        Method[] declaredMethods = cl.getDeclaredMethods();
        //注入value
        injectValue(cl, register);
        injectBean(declaredMethods, register);//将bean注入到前面实例化的对象

        return register;
    }

    public Object register(Class cl) {
        Object target = null;
        String s = cl.getSimpleName().toLowerCase();
        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();
        Configuration annotation = (Configuration) cl.getAnnotation(Configuration.class);
        String value = annotation.value();
        // Map<String, Object> beans = myApplicationContext.getBeans();
        try {
            target = BeanUtils.createBean(cl);
        } catch ( BeanException e ) {
            e.printStackTrace();
        }
        if (value.equals("")) {
            //存进iod容器
            myApplicationContext.setBean(s, target);
        } else {
            myApplicationContext.setBean(value, target);
        }
        return target;
    }

    /**
     * 注入bean
     */
    public void injectBean(Method[] methods, Object o) {
        BeanPostProcessor beanPostProcessor = (BeanPostProcessor) AnnotationPostProcessorFactory.getPostProcessor("BeanPostProcessor");
        try {
            for (Method method : methods) {
                if (!method.isAnnotationPresent(Bean.class)) {
                    continue;
                } else {
                    beanPostProcessor.setInvoker(o);
                    beanPostProcessor.handle(method);
                }
            }
        } catch ( BeanExpressionException e ) {
            e.printStackTrace();
        }
    }

    /**
     * 注入value
     * 如何获取properties文件的内容
     * 先通过propertiesSourcePostProcessor获取properties文件名，再通过ProertiesLoaderUtils加载properties
     */

    public void injectValue(Class cl, Object o) {

        Field[] fields = ReflectUitls.getDeclaredFields(cl);
        String fileName = getFileName(cl);
        try {
            if (!fileName.endsWith(".properties")) {
                throw new BeanUnSupportedExcepetion("只支持配置文件类型：properties");
            } else {
                Properties load = PropertiesLoaderUtils.load(fileName);
                doInjectValue(load, fields, o);
            }
        } catch ( BeanUnSupportedExcepetion e ) {
            e.printStackTrace();
        }


    }

    /**
     * 获取propertiesResource处理器的文件名
     */
    public String getFileName(Class cl) {
        String filename = null;
        AnnotationPostProcessor propertiesSourcePostProcessor = AnnotationPostProcessorFactory.getPostProcessor("PropertiesSourcePostProcessor");
        try {
            filename = (String) propertiesSourcePostProcessor.handle(cl);
        } catch ( BeanExpressionException e ) {
            e.printStackTrace();
        }
        return filename;
    }

    /**
     *
     */
    public void doInjectValue(Properties prop, Field[] fields, Object o) {
        AnnotationPostProcessor valuePostProcessor = AnnotationPostProcessorFactory.getPostProcessor("ValuePostProcessor");

        String key = null;

        for (Field field : fields) {
            try {
                ReflectUitls.makeAccessible(field);
                key = (String) valuePostProcessor.handle(field);
               if(key !=null){
                if (prop.get(key) == null) {
                    continue;
                }
                Class<?> type = field.getType();
                Object value = this.swithType(prop, type.getSimpleName(), key);

                ReflectUitls.setFiled(field,o,value);

            }
            }catch ( Exception e ) {
                e.printStackTrace();
            }

        }

    }

    private Object swithType(Properties properties, String name, String key) {
        switch (name) {
            case "Integer":
                return Integer.valueOf(properties.getProperty(key));
            case "String":
                return properties.getProperty(key);
        }
        return properties.get(key);
    }
}

