package edu.gdpu.MySSM.test.aopTest;

import edu.gdpu.spring.aop.annotation.Before;
import edu.gdpu.spring.aop.annotation.Pointcut;
import edu.gdpu.spring.aop.support.ProxyFactoryBean;
import edu.gdpu.spring.aop.support.Advisor;
import edu.gdpu.spring.aop.support.BeforeAdvice;
import edu.gdpu.spring.aop.support.ExecutionPointcut;
import edu.gdpu.spring.aop.target.SingletonTargetSource;
import edu.gdpu.spring.aop.target.TargetSource;
import edu.gdpu.ibatis.io.ClassUtils;
import edu.gdpu.spring.exception.PointcutNotFoundException;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月17日 23:35:37
 */
public class AppTest {
    @Test
    public void test() throws Exception {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        //模拟从ioc容器中获取bean
        Class objectClass = ClassUtils.loadClass(ClassUtils.getDefaultClassLoader(), "edu.gdpu.MySSM.test.aopTest.MyService");
        MyServiceImpl o = (MyServiceImpl) objectClass.getDeclaredConstructor().newInstance();
        Method method = objectClass.getMethod("say");
        TargetSource targetSource = new SingletonTargetSource(o);
        //设置目标对象
        proxyFactoryBean.setTargetSource(targetSource);
        getInstance(proxyFactoryBean, method);


        //advisedSupport要配置targetSource,才可以判断是否产生代理对象
        List<Advisor> advisors = proxyFactoryBean.getAdvisors();
        for (Advisor advisor : advisors) {
            System.out.println(advisor);
        }

    }



    //模拟advisedSupport初始化,在注解购置处理器中运行
    public Object getInstance(ProxyFactoryBean support, Method method) throws Exception {

        //扫描注解//模拟从ioc容器中获取aspect,并获取其中方法，组建通知器
        Class<?> aClass = Class.forName("edu.gdpu.MySSM.test.aopTest.MyAspect");
        Method[] methods = aClass.getDeclaredMethods();
        String pointcutMethodname = null;
        ExecutionPointcut executionPointcut = null;
        for (Method method1 : methods) {
            if (method1.isAnnotationPresent(Pointcut.class)) {
                //获取pointcut切入点表达式
                Pointcut annotation = method1.getAnnotation(Pointcut.class);
                executionPointcut = new ExecutionPointcut(annotation.value());
                System.out.println(executionPointcut);
                pointcutMethodname = method1.getName();

            }
        }
        for (Method method1 : methods) {
            if (method1.isAnnotationPresent(Before.class)) {
                BeforeAdvice beforeAdvice = new BeforeAdvice();

                Before annotation = method1.getAnnotation(Before.class);
                String value = pointcutMethodname + "()";

                if (annotation.value().equals(value)) {
                    //创建通知器
                    Advisor advisor = new Advisor(beforeAdvice, executionPointcut);
                    //设置通知器到通知器拦截链中

                    support.setAdvisors(advisor);
                    // beforeAdvice.invoke();当然这一步需要到JDKAopProxy.invoke方法里面执行，这里只是模拟
                } else {
                    throw new PointcutNotFoundException("找不到已配置好的切入点表达式");
                }
            }
        }


        System.out.println(support.getTargetSource().getaClass());
        //判断是否符合classfiter和methodMatcher拦截
        if (executionPointcut.match(support.getTargetSource().getaClass())) {
            System.out.println("此类符合拦截规则，需创建代理对象进行增强");
        }
        if (executionPointcut.match(method)) {
            System.out.println("此方法符合拦截规则，需增强");
        }
        return null;
    }


}