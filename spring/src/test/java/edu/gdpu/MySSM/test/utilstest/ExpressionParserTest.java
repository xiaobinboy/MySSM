package edu.gdpu.MySSM.test.utilstest;

import edu.gdpu.spring.core.util.ExpressionParser;
import edu.gdpu.spring.exception.BeanExpressionException;

import org.junit.Test;

/**
 * @author mazebin
 * @date 2021年 02月27日 18:09:45
 */
public class ExpressionParserTest {
    @Test
    public void test() {
        String parse = null;
        try {
            parse = ExpressionParser.parse("#{hello?tomcat?}");
        } catch ( BeanExpressionException bean ) {
            bean.printStackTrace();
        }
        System.out.println(parse);
    }
}