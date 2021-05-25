package edu.gdpu.MySSM.test.utilstest;

import edu.gdpu.MySSM.test.say;
import edu.gdpu.spring.core.util.BeanUtils;
import edu.gdpu.spring.exception.BeanException;
import org.junit.Test;

/**
 * @author mazebin
 * @date 2021年 02月28日 16:46:39
 */
public class BeanUtilsTest {
    @Test
    public void test() throws BeanException {
        BeanUtils.createBean(say.class);

    }
}
