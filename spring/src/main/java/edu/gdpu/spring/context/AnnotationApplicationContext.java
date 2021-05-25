package edu.gdpu.spring.context;

import edu.gdpu.spring.beans.factory.BeanFactory;
import edu.gdpu.spring.beans.factory.DefaultListableBeanFactory;

import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 02月24日 17:48:14
 * 这个applicationContext使用了懒汉加载设计模式来保证ApplicationContext的唯一性
 */
public class AnnotationApplicationContext implements ApplicationContext {
    private BeanFactory defaultBeanFactory;

    @Override
    public Map<Class, String> getMapping() {
        return defaultBeanFactory.getMapping();
    }

    public AnnotationApplicationContext() {
        defaultBeanFactory = new DefaultListableBeanFactory();
    }


    @Override
    public <T> T getBean(String name) {
        return defaultBeanFactory.getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> cl) {
        return defaultBeanFactory.getBean(cl);
    }

    @Override
    public Object setBean(String key, Object value) {
        return defaultBeanFactory.setBean(key, value);
    }

    @Override
    public Map<String, Object> getBeans() {
        return defaultBeanFactory.getBeans();
    }

    @Override
    public void printBeans() {
        defaultBeanFactory.printBeans();
    }

    @Override
    public BeanFactory getBeanFactory() {
        return this.defaultBeanFactory;
    }
}