package edu.gdpu.spring;

/**
 * @author mazebin
 * @date 2021年 05月10日 20:58:42
 */
public interface ApplicationListener {
    /**
     * 开启监听
     */
    public void begin();

    /**
     * 完成扫描
     */
    public void completeScan();

}