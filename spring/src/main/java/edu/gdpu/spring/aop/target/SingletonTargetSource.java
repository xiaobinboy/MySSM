package edu.gdpu.spring.aop.target;

/**
 * @author mazebin
 * @date 2021年 03月03日 17:34:20
 *  存储用于Proxy.newInstance()方法所需的参数
 */
public class SingletonTargetSource implements TargetSource {

  private Object target;


    public SingletonTargetSource( Object target) {

        this.target = target;

    }

    public SingletonTargetSource() {
    }

    public Class<?> getaClass() {
        return target.getClass();
    }



    public void setTarget(Object target) {
        this.target = target;
    }




    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Class<?>[] getInterfaces() {
        return target.getClass().getInterfaces();
    }


}