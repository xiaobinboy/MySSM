package edu.gdpu.springmvc.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mazebin
 * @date 2021年 04月10日 12:13:59
 */
public class ModelAndView  extends HashMap {
    private Object view;
    private  ModelMap model;


    public ModelAndView() {
    }
    public ModelAndView(String viewName,ModelMap model) {
        this.setViewName(viewName);
        this.getModelMap().addAllAttributes(model);
    }
    public ModelAndView(View view,ModelMap model) {
        this.setView(view);
        this.getModelMap().addAllAttributes(model);
    }

    public  void setViewName(String viewName){
        this.view = viewName;
    }
    public String getViewName(){
         return  (this.view instanceof  String) ? (String) this.view : null;
    }
    public  void setView(View view){
        this.view  = view;
    }
    public View getView(){
        return (this.view instanceof  View)?(View) this.view:null;
    }
    public  ModelAndView addObject(String attributeName,Object attributeValue){
this.getModelMap().addAttribute(attributeName,attributeValue);
        return  this;
    }
    public ModelAndView removeObject(String attributeName){
        this.getModelMap().removeAttribute(attributeName);
        return  this;
    }
    public ModelAndView addAllObjects(Map<String,?> attributesMap){
        this.getModelMap().addAllAttributes(attributesMap);
        return  this;
    }



    public ModelMap getModelMap() {
        if(this.model ==  null){
            this.model  = new ModelMap();
        }
        return this. model;
    }

    public void setModel(ModelMap model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "ModelAndView{" +
                "view=" + view +
                ", model=" + model +
                '}';
    }
}