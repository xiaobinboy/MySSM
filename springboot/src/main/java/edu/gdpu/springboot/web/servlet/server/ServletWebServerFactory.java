package edu.gdpu.springboot.web.servlet.server;

/**
 * @author mazebin
 * @date 2021年 05月09日 15:38:23
 */
public interface ServletWebServerFactory {
    /**
     * 获取WebServer
     * @return
     */
    WebServer getWebServer();
}