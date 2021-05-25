package edu.gdpu.spring.exception;

/**
 * @author mazebin
 * @date 2021年 03月17日 10:00:17
 */
public  class AopConfigException extends RuntimeException{
    public AopConfigException() {
    }

    public AopConfigException(String message) {
        super(message);
    }
}