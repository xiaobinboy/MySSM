package edu.gdpu.MySSM.test.utilstest;

import edu.gdpu.spring.core.util.PackageScanUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mazebin
 * @date 2021年 03月01日 20:02:59
 */
public class PackageScanUtilsTest {
    @Test
    public void test() {
        HashSet set = new HashSet();
        //edu.gdpu.MySSM.test.utilstest
        Set<Class> classes = PackageScanUtils.scanAllClasses("edu.gdpu.myspring");
        for (Class aClass : classes) {
            System.out.println(aClass);
            String name = aClass.getPackage().getName();
            set.add(name);
        }
        for (Object o : set) {
            System.out.println(o);
        }
    }

}