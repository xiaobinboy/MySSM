package edu.gdpu.ibatis.exceptions;

/**
 * @author mazebin
 * @date 2021年 04月02日 14:29:35
 */
public class ToomManyResultsExcepetion extends RuntimeException {
    public ToomManyResultsExcepetion(String message) {
        super(message);
    }

    public ToomManyResultsExcepetion() {
    }
}