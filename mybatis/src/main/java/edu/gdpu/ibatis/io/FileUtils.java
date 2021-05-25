package edu.gdpu.ibatis.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 02月26日 00:46:02
 * 加载文件工具类
 * getAllFiles(File file)传入的file是一个全绝对路径，如edu.gdpu.mySSM.test;
 */
public class FileUtils {
    /**
     * 非递归方式获取所有文件
     *
     * @param file （参数值一般为：new file(urL.getFile())
     * @return //D:\javaprogram\springboot_quick\MySSM\spring\target\classes\edu\gdpu\myspring\beans\factory\InterfaceObject.class
     * @throws FileNotFoundException
     */

    public static List<File> getAllFiles(File file) throws FileNotFoundException {

        List<File> files = new ArrayList<>();
        ArrayList<File> dirs = new ArrayList<>();
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        } else {
            //第一次获取目录
            dirs.addAll(Arrays.asList(file.listFiles()));
            /**
             * file.listFiles()获取的是数组，通过arrays工具类转化成集合
             */
            //循环获取目录
            //新建一个集合用来存储是目录的file对象
            ArrayList<File> sondir = new ArrayList<>();
            while (dirs.size() != 0) {
                for (File dir : dirs) {
                    if (!dir.isDirectory()) {
                        files.add(dir);
                    } else {
                        sondir.addAll(Arrays.asList(dir.listFiles()));
                    }
                }
                dirs.clear();
                //将子目录作为循环的对象
                dirs.addAll(sondir);
                sondir.clear();
            }
        }
        return files;
    }

    public static List<String> getPackageName(List<File> files, String parentName) {
        List<String> name = new ArrayList<>();
        for (File file : files) {
            // File file1 = new File(file.getAbsolutePath());
            if (file.getName().equals(parentName)) {
                name.add("");
                continue;
            }
            String s = "";
            while (!file.getName().equals(parentName)) {
                s = file.getName() + "." + s;
                file = file.getParentFile();
            }
            name.add(s);
        }
        return name;
    }

    public static String getClassName(File file, String packageName) {
        String[] split = packageName.split("\\.");
        String s = "";
        while (!file.getName().equals(split[split.length - 1])) {
            s = file.getName() + "." + s;
            file = file.getParentFile();
            if (file == null) {
                break;
            }
        }
        String className = packageName + "." + s.split("\\.class")[0];
        return className;
    }
}