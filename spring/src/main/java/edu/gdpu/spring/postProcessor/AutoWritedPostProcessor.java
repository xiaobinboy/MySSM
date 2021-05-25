package edu.gdpu.spring.postProcessor;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.context.annotation.AutoWrited;
import edu.gdpu.spring.core.util.ReflectUitls;


import java.lang.reflect.Field;


/**
 * @author mazebin
 * @date 2021年 02月28日 15:11:50
 */
public class AutoWritedPostProcessor implements AnnotationPostProcessor {
    //指代要注入的成员变量
    private Object invoker;

    public void setInvoker(Object invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object handle(Object o) {
        //强转成员变量
        Field[] fields = (Field[]) o;
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoWrited.class)) {
                inject(field);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void inject(Field o) {
        //暴力反射
        ReflectUitls.makeAccessible(o);
        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();
        AutoWrited writed = o.getAnnotation(AutoWrited.class);
        String writedvalue = writed.value();
        //获取成员变量的class类型
        Class<?> type = o.getType();

        Object typebean = null;
        try {
            typebean = myApplicationContext.getBean(type);

        } catch ( NullPointerException e ) {

        }
        if (typebean != null) {
            ReflectUitls.setFiled(o, invoker, typebean);
        } else {
            //尝试类型匹配
            if (writedvalue.equals("")) {
                //接口实现类的注入
                ReflectUitls.setFiled(o, invoker, myApplicationContext.getBean(o.getName().toLowerCase()));
            } else {
                ReflectUitls.setFiled(o, invoker, myApplicationContext.getBean(writedvalue));
            }
        }
    }
}
