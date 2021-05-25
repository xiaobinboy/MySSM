package edu.gdpu.ibatis.spring.utils;



import edu.gdpu.ibatis.io.ClassUtils;
import edu.gdpu.ibatis.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author mazebin
 * @date 2021年 03月01日 19:24:20
 * 扫描basepackage或jar包工具类
 */
public class PackageScanUtils {
    private static final ClassLoader LOADER = ClassUtils.getDefaultClassLoader();

    public static Set<Class> scanAllClasses(String packageName) {
        Set<Class> classes = new HashSet<>();
        String path = packageName.replace(".", "/");
        //加载资源，获取url
        try {
            Enumeration<URL> resources = LOADER.getResources(path);
            while (resources.hasMoreElements()) {
                //自动从class文件所在的目录读取
                URL url = resources.nextElement();
                String file = url.getFile();
                if (url.getProtocol().contains("jar")) {
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    String jarFilePath = urlConnection.getJarFile().getName();
                    classes.addAll(getfromJar(jarFilePath, path));
                    continue;
                }
                //获取包路径下的所有文件
                List<File> allFiles = FileUtils.getAllFiles(new File(file));
//                String[] split = packageName.split("\\.");
//                //得到的是文件名.class
//                List<String> name = FileUtils.getPackageName(allFiles, split[split.length - 1]);
//                String className = null;
//                for (String s : name) {
//                    className=(packageName+"."+s).split("\\.class")[0];
//                    Class<?> aClass = LOADER.loadClass(className);
//                    classes.add(aClass);
//                    }

                for (File allFile : allFiles) {
                    String className = FileUtils.getClassName(allFile, packageName);
                    Class<?> aClass = ClassUtils.loadClass(LOADER, className);
                    classes.add(aClass);
                }


            }
        } catch ( FileNotFoundException e1 ) {
            e1.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return classes;
    }

    public static Set<Class> getfromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        Set<Class> classes = new HashSet<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            //   entryName：edu/gdpu/MySSM/test/Test.class or edu/gdpu/MySSM/test/test.properties
            String entryName = jarEntry.getName();
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                //转化成全限定类名
                String className = entryName.replace("/", ".").substring(0, entryName.length() - 6);

                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
