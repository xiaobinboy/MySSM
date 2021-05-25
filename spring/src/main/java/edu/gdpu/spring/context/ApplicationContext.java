package edu.gdpu.spring.context;

import edu.gdpu.spring.beans.factory.BeanFactory;

import java.util.Map;

public interface ApplicationContext extends BeanFactory {
public BeanFactory getBeanFactory();

}
