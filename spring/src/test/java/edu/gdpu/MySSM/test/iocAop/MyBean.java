package edu.gdpu.MySSM.test.iocAop;

/**
 * @author mazebin
 * @date 2021年 03月01日 11:36:04
 */
public class MyBean {
    private String str = " this is a bean";

    public MyBean() {
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public MyBean(String pro) {
        this.str= pro;
    }
}