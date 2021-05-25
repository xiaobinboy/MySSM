package edu.gdpu.springboot.web.servlet.server;

/**
 * @author mazebin
 * @date 2021年 05月09日 15:48:28
 */
public class WebServerException extends  RuntimeException {
    public WebServerException() {
    }

    public WebServerException(String message) {
        super(message);
    }
}