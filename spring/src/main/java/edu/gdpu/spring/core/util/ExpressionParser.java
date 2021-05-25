package edu.gdpu.spring.core.util;


import edu.gdpu.spring.exception.BeanExpressionException;

/**
 * @author mazebin
 * @date 2021年 02月27日 16:51:18
 * java的转义字符：\\
 * 类似于spring的占位符转换器
 */
public class ExpressionParser {
    private static String suffix = "}";
    private static String prefix = "#{";

    public static String parse(String value) throws BeanExpressionException {
        if (!value.startsWith(prefix)) {
            throw new BeanExpressionException("注入的表达式格式有误");
        }
        if (!value.endsWith(suffix)) {
            throw new BeanExpressionException("注入的表达式格式有误");
        }
        String[] split = value.split("\\{");
        if (split.length != 2) {
            throw new BeanExpressionException("注入的表达式格式有误");
        }
        String[] s = split[1].split("}");
        if (s.length != 1) {
            throw new BeanExpressionException("注入的表达式格式有误");
        } else {

            return s[0];

        }

    }
}