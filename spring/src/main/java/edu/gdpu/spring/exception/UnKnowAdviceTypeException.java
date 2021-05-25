package edu.gdpu.spring.exception;

/**
 * @author mazebin
 * @date 2021年 03月20日 11:00:32
 */
public class UnKnowAdviceTypeException extends Exception {
    public UnKnowAdviceTypeException() {
    }

    public UnKnowAdviceTypeException(String message) {
        super(message);
    }
}