package edu.gdpu.spring.aop.support;

import edu.gdpu.spring.aop.MethodMatcher;
import edu.gdpu.spring.aop.Pointcut;
import edu.gdpu.spring.aop.ClassFilter;
import edu.gdpu.spring.exception.AopConfigException;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 执行pointcut的真正实现类
 * @author mazebin
 * @date 2021年 03月04日 19:37:39
 */
public class ExecutionPointcut implements Pointcut {
public static final Pattern RULE_PATTERN = Pattern.compile("([a-zA-Z.*]*)\\(\\)");

private Pattern classNamePattern;
private Pattern methodNamePattern;


        public ExecutionPointcut(String expression) {
        Matcher rule_matcher = RULE_PATTERN.matcher(expression);
        if (!rule_matcher.find()) {
        throw new AopConfigException("pointcut表达式值有误");
        }

        String aspectExpression = rule_matcher.group(1);
        int index = aspectExpression.lastIndexOf(".");
        if (index < 1 || index == aspectExpression.length() - 1) {
        throw new AopConfigException("pointcut表达式值有误");
        }
        String classExp, methodExp;//正则表达式
        classExp = aspectExpression.substring(0, index);
        methodExp = aspectExpression.substring(index + 1);

        classExp = classExp.replace("*", "([\\w[^\\.]]*)");
        classExp = classExp.replace("..", "[\\w\\.]*");
                
        methodExp = methodExp.replace("*", "[\\w]*");
        classNamePattern = Pattern.compile("^" + classExp + "$");
        methodNamePattern = Pattern.compile(methodExp);
        }

@Override
public ClassFilter getClassFilter() {
        return this;
        }

@Override
public MethodMatcher getMethodMatcher() {
        return this;
        }

@Override
public boolean match(Class<?> cls) {
        return classNamePattern.matcher(cls.getName()).find();
        }

@Override
public boolean match(Method method) {
        return methodNamePattern.matcher(method.getName()).find();
        }
        }