package edu.gdpu.MySSM.test.javaseTest;

import edu.gdpu.ibatis.io.ClassUtils;
import org.junit.Test;

import java.net.URL;
import java.net.URLDecoder;

/**
 * @author mazebin
 * @date 2021年 03月01日 23:35:49
 */
public class UrlTest {
    @Test
    public void test() throws Exception {
        URL url = ClassUtils.getDefaultClassLoader().getResource("banner.txt");
        String file = url.getFile();
        // /D:/javaprogram/springboot_quick/MySSM/spring/target/classes/banner.txt
        String decode = URLDecoder.decode(file, "UTF-8");

    }
}