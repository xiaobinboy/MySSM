package edu.gdpu.spring.core.util;

import edu.gdpu.ibatis.io.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 02月25日 17:10:04
 * 获取资源文件工具类
 */
public class ResourceUtils {
    /**
     * 遇到加载的第一个同名文件就会返回
     *
     * @param fileName
     * @return
     */
    public static InputStream loadInClassPath(String fileName) {
        return ClassUtils.getDefaultClassLoader().getResourceAsStream(fileName);
    }

    /**
     * 加载所有同名文件，包括第三方jar包下的
     *
     * @param fileName
     * @return
     */
    public static List<InputStream> getFiles(String fileName) {
        List<InputStream> ins = new ArrayList<>();
        //获取当前线程的类加载器
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        try {
            Enumeration<URL> resources = cl.getResources(fileName);
            while (resources.hasMoreElements()) {
                ins.add(resources.nextElement().openStream());
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return ins;
    }
}