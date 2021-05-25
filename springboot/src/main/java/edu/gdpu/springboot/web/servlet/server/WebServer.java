package edu.gdpu.springboot.web.servlet.server;



/**
 * @author mazebin
 * @date 2021年 05月09日 15:38:55
 */
public interface WebServer {
    /**
     * 启动webServer
     * @throws WebServerException
     */

    void start() throws WebServerException;
    /**
     * 关闭WebServer
     * @throws WebServerException
     */
    void stop() throws WebServerException;

    /**
     * 获取端口号
     * @return
     */
    int getPort();
}