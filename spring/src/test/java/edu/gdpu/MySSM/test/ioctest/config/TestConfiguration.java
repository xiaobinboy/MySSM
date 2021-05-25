package edu.gdpu.MySSM.test.ioctest.config;

import edu.gdpu.MySSM.test.ioctest.MyBean;
import edu.gdpu.spring.context.annotation.*;

/**
 * @author mazebin
 * @date 2021年 03月02日 21:11:40
 */
@PropertiesSource("test.properties")
@Configuration
@ComponentScan("edu.gdpu.MySSM.test.ioctest")
public class TestConfiguration {
    @Value("#{test.user}")
    String user;

    @Bean
    public MyBean MyBean() {
        MyBean myBean = new MyBean();
        return myBean;
    }

    public String getUser() {
        return user;
    }
}