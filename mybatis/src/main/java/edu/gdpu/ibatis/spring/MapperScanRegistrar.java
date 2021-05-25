package edu.gdpu.ibatis.spring;

import edu.gdpu.ibatis.builder.MapperAnnotationBuilder;
import edu.gdpu.ibatis.session.Configuration;
import edu.gdpu.ibatis.spring.utils.PackageScanUtils;



import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author mazebin
 * @date 2021年 03月24日 10:39:51
 * 扫描接口类，创建mappedStatement和mapperMethod
 */
public class MapperScanRegistrar {
   private String packageName;

   private Configuration configuration;

    public MapperScanRegistrar() {
    }


    public MapperScanRegistrar(String packageName,Configuration configuration) {
        this.packageName = packageName;
        this.configuration = configuration;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
   //完成MappedStatement的封装
    public void scanMappedStatement(){
        Set<Class> classes = PackageScanUtils.scanAllClasses(packageName);
        for (Class cl : classes) {
            if(cl.isInterface()){
                Method[] methods = cl.getDeclaredMethods();
                for (Method method : methods) {
                    MapperAnnotationBuilder builder = new MapperAnnotationBuilder(configuration);
builder.addMappedStatement(method);
                }
            }
        }
    }
    //完成Mapper的注册
    public void  scanMapper(){
        Set<Class> classes = PackageScanUtils.scanAllClasses(packageName);
        for (Class cl : classes) {
            if(cl.isInterface()){
                configuration.addMapper(cl);
                //将生成的代理对象放入到ioc容器中
                //context.setBean(cl.getSimpleName().toLowerCase(),);

            }
        }
    }
}