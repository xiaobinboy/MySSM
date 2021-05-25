package edu.gdpu.springmvc.servlet;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 04月13日 20:53:35
 */
public class ModelMap  extends LinkedHashMap<String,Object> implements Model {
    @Override
    public Model addAttribute(String attributeName, Object attributeValue) {
        put(attributeName,attributeValue);
        return this;
    }

    @Override
    public Object getAttribute(String attributeName) {
        return get(attributeName);
    }

    @Override
    public Model addAllAttributes(Map<String, ?> model) {
        if(model != null){
            putAll(model);
        }
        return this;
    }

    @Override
    public Model removeAttribute(String attributeName) {
        if(attributeName!=null){
            remove(attributeName);
        }
        return this;
    }
}