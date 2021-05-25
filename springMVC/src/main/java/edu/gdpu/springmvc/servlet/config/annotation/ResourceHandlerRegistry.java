package edu.gdpu.springmvc.servlet.config.annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:52:50
 * 注册静态资源过滤器
 */
public class ResourceHandlerRegistry {
    /**
     * 一般情况下只有这三个文件夹下的资源会被识别为静态资源
     */
    private static String[] resourceLocations = {"/css/","/js/","/image/"};
   private  static List<String> resourceLocationList= new ArrayList<String>();
   private static  String separator = ",";

    public static String[] getResourceLocations() {
        return resourceLocations;
    }

    public static List<String> getResourceLocationList() {
        return resourceLocationList;
    }
    public  void addResourceLocations(String locationName){
        if(locationName.contains(separator)){
            String[] locationNames = locationName.split(",");
            for (String name : locationNames) {
                resourceLocationList.add(name);

            }
        }else {
            resourceLocationList.add(locationName);
        }
       resourceLocations = (String[]) resourceLocationList.toArray();
    }



}