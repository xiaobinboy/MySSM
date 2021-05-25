package edu.gdpu.MySSM.test.iocAop.config;

import com.alibaba.druid.pool.DruidDataSource;
import edu.gdpu.MySSM.test.iocAop.MyBean;
import edu.gdpu.spring.aop.annotation.EnableAopProxy;
import edu.gdpu.spring.context.annotation.*;
import edu.gdpu.spring.transaction.ConnectionManager;
import edu.gdpu.spring.transaction.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author mazebin
 * @date 2021年 03月02日 21:11:40
 */
@PropertiesSource("test.properties")
@Configuration
//@EnableTransaction
@ComponentScan("edu.gdpu.MySSM.test.iocAop")
@EnableAopProxy(useCglib = false)
public class TestConfiguration {
    @Value("#{test.user}")
    String user;

    @Bean
    public MyBean MyBean() {
        MyBean myBean = new MyBean();
        return myBean;
    }
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/eesy?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("18050910ads");
    return dataSource;
    }
    @Bean
    public ConnectionManager connectionManager(){
        //类似于@Qualifer注解，不想实现
        DataSource dataSource = dataSource();
        ConnectionManager.setDataSource(dataSource);
        ConnectionManager manager = new ConnectionManager();
        return manager;
    }
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setConnection();
        return manager;
    }

}