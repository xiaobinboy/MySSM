package edu.gdpu.springmvc.servlet;

/**
 * @author mazebin
 * @date 2021年 04月10日 10:47:23
 */
public class NoHandlerFoundException extends RuntimeException {
    public NoHandlerFoundException() {
    }

    public NoHandlerFoundException(String message) {
        super(message);
    }
}