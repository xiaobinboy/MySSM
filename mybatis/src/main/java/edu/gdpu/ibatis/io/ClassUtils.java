package edu.gdpu.ibatis.io;

/**
 * @author mazebin
 * @date 2021年 02月25日 17:15:17
 * 获取类加载器
 */
public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch ( Exception e ) {

        }
        if (cl == null) {
            cl = ClassUtils.getDefaultClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch ( Exception e ) {

                }
            }
        }
        return cl;
    }

    public static  Class<?> loadClass(ClassLoader classLoader, String name) {
        try {
            return  classLoader.loadClass(name);

        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return null;
    }
}