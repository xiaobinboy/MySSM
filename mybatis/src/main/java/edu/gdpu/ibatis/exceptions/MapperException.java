package edu.gdpu.ibatis.exceptions;

/**
 * @author mazebin
 * @date 2021年 03月28日 16:41:13
 */
public class MapperException extends RuntimeException{
    public MapperException() {
    }

    public MapperException(String message) {
        super(message);
    }
}