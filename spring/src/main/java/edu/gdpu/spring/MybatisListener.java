package edu.gdpu.spring;

import edu.gdpu.ibatis.session.SqlSession;
import edu.gdpu.ibatis.spring.SqlSessionFactoryBean;
import edu.gdpu.spring.ApplicationListener;
import edu.gdpu.spring.beans.factory.InterfaceObject;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;

import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 05月10日 21:59:30
 */
public class MybatisListener implements ApplicationListener {
    @Override
    public void begin() {

    }

    @Override
    public void completeScan() {
        ApplicationContext applicationContext = ApplicationContextFactory.getApplicationContext();
        Map<String, Object> beans = applicationContext.getBeans();
        Object target = null;

        for (String s : beans.keySet()) {
            target = beans.get(s);
            if(target instanceof InterfaceObject){
               InterfaceObject interfaceObject = (InterfaceObject) target;
                SqlSessionFactoryBean bean = applicationContext.getBean(SqlSessionFactoryBean.class);

                SqlSession sqlSession = bean.getSqlSession();

                Object mapper = sqlSession.getMapper(interfaceObject.getaClass());
                System.out.println(mapper);
                beans.put(s,mapper);
            }
        }

    }
}