package edu.gdpu.spring.transaction;

/**
 * @author mazebin
 * @date 2021年 03月22日 11:22:01
 */
//事务的隔离级别 transaction的填充
public class TransactionIsolevel {
    public static  final  int TRANSACTION_READ_UNCOMMITTED = 1;
    public static  final  int TRANSACTION_READ_COMMITTED   = 2;
    public static  final  int TRANSACTION_REPEATABLE_READ  = 4;
    public static  final  int TRANSACTION_SERIALIZABLE     = 8;
}