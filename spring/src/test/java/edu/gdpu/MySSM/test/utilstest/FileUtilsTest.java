package edu.gdpu.MySSM.test.utilstest;

import edu.gdpu.ibatis.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 03月01日 22:59:19
 */
public class FileUtilsTest {
    @Test
    public void test() {
        // String s ="/D:/javaprogram/springboot_quick/MySSM/spring/target/test-classes/edu/gdpu/MySSM/test/utilstest";
        //String s ="D:\\javaprogram\\springboot_quick\\MySSM\\spring\\src\\main\\java\\edu\\gdpu\\myspring\\beans";//这种方法得到的是java文件
        String s = "/D:/javaprogram/springboot_quick/MySSM/spring/target/classes/edu/gdpu/myspring/beans"; //这种方法得到是class文件，这种string是url.getfile()得到的
        File file = new File(s);

        List<File> allFiles = null;

        try {
            allFiles = FileUtils.getAllFiles(file);
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
        for (File allFile : allFiles) {
            if (allFile.isFile())
                System.out.println("is file:" + allFile);
            if (allFile.isDirectory()) {
                System.out.println(" is directory:");
            }
        }
    }

    @Test
    public void test2() {
        String s = "/D:/javaprogram/springboot_quick/MySSM/spring/target/test-classes/edu/gdpu/MySSM/test/utilstest";
        String packageName = "edu.gdpu.MySSM";
        File file = new File(s);

        List<File> allFiles = null;

        try {
            allFiles = FileUtils.getAllFiles(file);
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
        String[] split = packageName.split("\\.");
        List<String> packageNames = FileUtils.getPackageName(allFiles, split[split.length - 1]);
        for (String name : packageNames) {
            System.out.println(name);
        }

    }
}