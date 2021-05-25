package edu.gdpu.mySSM.test.javaseTest;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 * @author mazebin
 * @date 2021年 04月03日 10:18:57
 */
public class JdbcTest {
    @Test
    public  void  test() throws Exception{
        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/eesy_mybatis?serverTimezone=UTC";
        String username ="root";
        String password ="18050910ads";

        Connection connection = DriverManager.getConnection(url, username, password);
        DatabaseMetaData metaData = connection.getMetaData();
        boolean b = metaData.supportsMultipleOpenResults();
        System.out.println(b);
        boolean b1 = metaData.supportsOpenStatementsAcrossCommit();
        System.out.println(b1);

    }
}