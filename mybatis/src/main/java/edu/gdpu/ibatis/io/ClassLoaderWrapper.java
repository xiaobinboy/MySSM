package edu.gdpu.ibatis.io;

import java.io.InputStream;

/**
 * @author mazebin
 * @date 2021年 03月24日 08:50:15
 */
public class ClassLoaderWrapper {
    ClassLoader defaultClassLoader;
    ClassLoader systemClassLoader;
    ClassLoaderWrapper() {
        try {
            this.systemClassLoader = ClassLoader.getSystemClassLoader();
        } catch (SecurityException var2) {
        }

    }

    public InputStream getReourcesAsStream(String resource) {
        return this.getReourcesAsStream(resource,this.getClassloaders((ClassLoader)null));
    }
    public InputStream getReourcesAsStream(String resource,ClassLoader[] classLoaders){
        ClassLoader[] var1=classLoaders;
        int var2=var1.length;
        for (int i = 0; i < var2; i++) {
            ClassLoader classLoader = var1[i];
            if(null!=classLoader){
                InputStream returnValue = classLoader.getResourceAsStream(resource);
                if(null == returnValue){
                    returnValue=classLoader.getResourceAsStream("/"+resource);
                }
                if(null !=returnValue){
                    return returnValue;
                }
            }

        }
        return null;

    }
    public ClassLoader[] getClassloaders(ClassLoader classLoader){
        return  new ClassLoader[]{classLoader,this.defaultClassLoader, Thread.currentThread().getContextClassLoader(),this.getClass().getClassLoader(),this.systemClassLoader};
    }
}