package cn.binboy.config;

import edu.gdpu.springmvc.context.SpringWebApplicationInitializer;
import edu.gdpu.springmvc.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

/**
 * @author mazebin
 * @date 2021年 05月02日 15:19:30
 * tomcat启动时加载
 */

public class ApplicationInit extends SpringWebApplicationInitializer {
    private CharacterEncodingFilter filter = new CharacterEncodingFilter();
    /**设置dispatcherServlet

     */
    @Override
    public String getServletMappings() {
        return new String("/");
    }

    /**
     * 注入字符编码过滤器
     * @return
     */
    @Override
    public Filter[] getServletFilters() {
        filter.setEncoding("UTF-8");
        return new Filter[]{filter};
    }
    /**导入配置类
    */
    @Override
    public Class getApplicationContextClass() {
        return SpringConfig.class;
    }

}