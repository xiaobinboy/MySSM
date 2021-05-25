package edu.gdpu.springmvc.servlet;

import java.util.Map;

/**
 * @author mazebin
 */
public interface Model {
    /**
     * 添加数据
     * @param attributeName
     * @param attributeValue
     * @return
     */
    Model addAttribute(String attributeName, Object attributeValue);

    /**
     * 获取数据
     * @param attributeName
     * @return
     */
    Object getAttribute(String attributeName);
    Model addAllAttributes(Map<String,?> model);
   Model removeAttribute(String attributeName);
}
