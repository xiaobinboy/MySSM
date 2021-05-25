package edu.gdpu.spring.beans.factory;

import java.util.Map;

public interface BeanFactory {

    @SuppressWarnings("unchecked")
    public <T> T getBean(String name);

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> cl);

    @SuppressWarnings("unchecked")
    public  Object setBean(String key, Object value);

    @SuppressWarnings("unchecked")
  public   Map<String, Object> getBeans();

    @SuppressWarnings("unchecked")
    public void printBeans();
    public Map<Class,String> getMapping();

}
