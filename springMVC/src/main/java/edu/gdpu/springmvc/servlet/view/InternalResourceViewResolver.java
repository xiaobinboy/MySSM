package edu.gdpu.springmvc.servlet.view;


import edu.gdpu.springmvc.servlet.View;
import edu.gdpu.springmvc.servlet.ViewResolver;

/**
 * @author mazebin
 * @date 2021年 04月10日 11:01:02
 * 这个是jsp视图解析器实现类
 */
public class InternalResourceViewResolver implements ViewResolver {
   private String suffix;
   private String prefix;
    @Override
    public View resolveViewName(String viewName) throws Exception {
        String url=  prefix+viewName+suffix;
        return createViewName(url);
    }
    public View createViewName(String url){
return  new InternalResourceView(url);
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}