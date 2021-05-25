package edu.gdpu.spring.aop.support;

import edu.gdpu.spring.aop.Advice;
import edu.gdpu.spring.aop.Pointcut;

/**管理切面的通知器
 * @author mazebin
 * @date 2021年 03月15日 17:32:23
 */
public class Advisor {
    //结合pointcut和advice
    //获取通知注解的类型
    private Advice advice;
    //获取pointcut注解的value
    private Pointcut pointcut;

    public Advisor() {
    }

    public Advisor(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    public Advice getAdvice(){
    return this.advice;
};


}