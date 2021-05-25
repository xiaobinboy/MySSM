package edu.gdpu.spring.exception;

/**
 * @author mazebin
 * @date 2021年 03月17日 10:00:17
 */
public  class PointcutNotFoundException extends RuntimeException{
    public PointcutNotFoundException() {
    }

    public PointcutNotFoundException(String message) {
        super(message);
    }
}