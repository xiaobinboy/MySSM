package edu.gdpu.MySSM.test.javaseTest;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author mazebin
 * @date 2021年 03月01日 22:22:42
 */
public class FileMethodTest {
    @Test
    public void test() throws Exception {
        String s = null;
        s = "D:\\javaprogram\\springboot_quick\\MySSM\\spring\\src\\main\\resources";
        File file1 = new File(s);

        File[] files = file1.listFiles();//只会获取到子文件和子目录，获取不到子目录下的文件
        for (File file2 : files) {
            if (file2.isDirectory()) {
                System.out.println("directoy:" + file2);
//                System.out.println("路经：" + file2.getAbsolutePath());
//                System.out.println("包名：" + file2.getParent());
            } else {
                System.out.println("文件：" + file2);
            }

        }

    }

    /**
     * 获取全限定类名测试 由于通过classloader.getresources()方法加载class文件，获取到的是url的枚举,
     * 获取到的文件 ：D:\javaprogram\springboot_quick\MySSM\spring\target\classes\edu\gdpu\myspring\beans\factory\InterfaceObject.class
     * 想要获取的全限定类名：edu.gdpu.myspring.beans.factory.InterfaceObject，包名为：edu.gdpu
     * 其实就是如何获取到 myspring.beans.factory.InterfaceObject
     * 去除.class,String.split("\\.class");
     * 获取myspring.beans.factory ,File.getParentFile()
     * 思考：这两步先后关系如何
     * 先获取父目录，再切割和拼接
     * 获取父目录：（很显然这需要循环获取）
     * String s = "";
     * while(!file..getName().equals(packageName)){
     * s = file.getname()+"."+s;//第一次：interfaceObject.lass+.
     * file = file.getParentFile();
     * //第二次：factory.InterfaceObject.class.
     * }
     * 循环结束后s= myspring.beans.factory.InterfaceObject.class.
     * 切割：
     * s.split("\\.class")[0]=myspring.beans.factory.InterfaceObject;
     * 拼接：
     * packageName +s = 全限定类名
     * 但是出现了空指针异常，思考一下原因：
     * file.getParentFile();
     *
     * @throws IOException
     */
    @Test
    public void test2() throws Exception {
        File file = new File("D:\\javaprogram\\springboot_quick\\MySSM\\spring\\target\\classes\\edu\\gdpu\\myspring" +
                "\\beans\\factory\\InterfaceObject.class");
        String packageName = "edu.gdpu";
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
        System.out.println(className);

    }

    /**
     * spring.split测试
     */
    @Test
    public void test3() {
        String s = "edu";
        String[] split = s.split("\\.");
        System.out.println(split[0]);
    }

}