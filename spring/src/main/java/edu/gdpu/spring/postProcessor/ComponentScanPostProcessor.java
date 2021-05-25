package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.AopConfig;
import edu.gdpu.spring.aop.annotation.Aspect;
import edu.gdpu.spring.aop.annotation.EnableAopProxy;
import edu.gdpu.spring.aop.support.ProxyFactoryBean;
import edu.gdpu.spring.aop.support.Advisor;
import edu.gdpu.spring.aop.target.SingletonTargetSource;
import edu.gdpu.spring.aop.target.TargetSource;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.annotation.*;
import edu.gdpu.spring.core.util.PackageScanUtils;

import edu.gdpu.spring.core.util.ReflectUitls;
import edu.gdpu.spring.exception.BeanExpressionException;
import edu.gdpu.spring.postProcessor.facory.AnnotationPostProcessorFactory;
import edu.gdpu.spring.transaction.annotation.EnableTransaction;
import edu.gdpu.spring.transaction.annotation.Transactional;
import edu.gdpu.spring.ListenStarter;

import javax.servlet.annotation.HandlesTypes;
import java.lang.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mazebin
 * @date 2021年 03月01日 19:20:53
 * 获取包名和加载包下所有的文件，转变成class对象
 */
public class ComponentScanPostProcessor implements AnnotationPostProcessor {
    public ComponentScanPostProcessor() {
    }

    public Set<Class> getThirdPartyClasses() {
        return thirdPartyClasses;
    }

    public void setThirdPartyClasses(Set<Class> thirdPartyClasses) {
        this.thirdPartyClasses = thirdPartyClasses;
    }

    private  Set<Class> thirdPartyClasses = new HashSet<>();

    @Override
    public Object handle(Object o) throws BeanExpressionException {
        //强转为cl
        Class cl = (Class) o;
        ListenStarter starter = new ListenStarter();
        EnableAopProxy enableAopProxy = null;
        EnableTransaction enableTransaction = null;
        Transactional transactional = null;
        EnableAopProxyPostProcessor enableAopProxyPostProcessor = null;
        EnableTransactionPostProcessor enableTransactionPostProcessor = null;
        ProxyFactoryBean support = new ProxyFactoryBean();
        if (cl.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan annotation1 = (ComponentScan) cl.getAnnotation(ComponentScan.class);

            Set<Class> classes = PackageScanUtils.scanAllClasses(annotation1.value());
            if(thirdPartyClasses != null&&thirdPartyClasses.size()!=0 ){
           classes.addAll(thirdPartyClasses);}
            //扫描注解,获取注解后置处理器，初始化ioc容器
            for (Class aClass : classes) {
                Annotation[] annotations = aClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Target || annotation instanceof Documented || annotation instanceof Inherited ||
                            annotation instanceof ComponentScan || annotation instanceof Retention || annotation instanceof HandlesTypes  ) {
                        continue;
                    }
                    //是否开启aop功能
                    else if (annotation instanceof EnableAopProxy) {
                        enableAopProxy = (EnableAopProxy) annotation;
                        AopConfig.setUseCglib(enableAopProxy.useCglib());
                        continue;
                    } else if (annotation instanceof Aspect) {
                        AspectPostProcessor postProcessor = (AspectPostProcessor) AnnotationPostProcessorFactory.getPostProcessor(annotation);

                        List<Advisor> handle = (List<Advisor>) postProcessor.handle(aClass);
                        support.setAdvisors(handle);
                        continue;
                    }
                    //设置开启事务处理
                    else if (annotation instanceof EnableTransaction) {
                        enableTransaction = (EnableTransaction) annotation;
                        continue;
                    } else if (annotation instanceof Transactional) {
                        transactional = (Transactional) annotation;
                        AnnotationPostProcessor postProcessor = AnnotationPostProcessorFactory.getPostProcessor(annotation);
                        System.out.println("---"+postProcessor);
                        if (postProcessor != null) {
                            List<Advice> handle = (List<Advice>) postProcessor.handle(aClass);

                            support.setTransactionAdvices(handle);
                        }

                        continue;
                    } else {
                        AnnotationPostProcessor annotationPostProcessor = AnnotationPostProcessorFactory.getPostProcessor(annotation);
                        if (annotationPostProcessor != null) {
                            annotationPostProcessor.handle(aClass);
                        }
                    }
                }
            }

            starter.completeScan();
            //设置依赖注入处理器
            AutoWritedPostProcessor autoWritedPostProcessor = (AutoWritedPostProcessor) AnnotationPostProcessorFactory.getPostProcessor("AutoWritedPostProcessor");
            //设置是否需要增强
            if (enableAopProxy != null) {
                enableAopProxyPostProcessor = (EnableAopProxyPostProcessor) AnnotationPostProcessorFactory.getPostProcessor("EnableAopProxyPostProcessor");

            }
            if (enableTransaction != null) {
                enableTransactionPostProcessor = (EnableTransactionPostProcessor) AnnotationPostProcessorFactory.getPostProcessor(enableTransaction);
            }
            //获取IOC容器
            Map<String, Object> beans = ApplicationContextFactory.getApplicationContext().getBeans();


            Object target = null;
            //依赖注入
            for (String s : beans.keySet()) {
                //从容器中获取对象，判断是否需要注入和增强
                target = beans.get(s);

                TargetSource singletonTargetSource = new SingletonTargetSource(target);
                //获取AOP通知器
                List<Advisor> advisors = support.getAdvisors();
                for (Advisor advisor : advisors) {
                    //判断类是否符合增强规则
                    if (advisor.getPointcut().match(singletonTargetSource.getaClass()) && enableAopProxyPostProcessor != null) {
                        support.setTargetSource(singletonTargetSource);
                        Object handle = enableAopProxyPostProcessor.handle(support);
                        //重新注入
                        beans.put(s, handle);
                    }

                }
                if (target.getClass().isAnnotationPresent(Transactional.class) && enableTransactionPostProcessor != null) {
                    support.setTargetSource(singletonTargetSource);

                    Object handle = enableTransactionPostProcessor.handle(support);

                    beans.put(s, handle);
                }

                autoWritedPostProcessor.setInvoker(target);
                autoWritedPostProcessor.handle(ReflectUitls.getDeclaredFields(target.getClass()));
            }

        }


        return null;
    }
}