package edu.gdpu.spring.transaction;

public interface TransactionManager {
    void  before();
    void  after();
    void afterThrow();
}
