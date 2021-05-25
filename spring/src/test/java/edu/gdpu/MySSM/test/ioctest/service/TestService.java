package edu.gdpu.MySSM.test.ioctest.service;

import edu.gdpu.MySSM.test.ioctest.MyBean;
import edu.gdpu.spring.context.annotation.AutoWrited;
import edu.gdpu.spring.context.annotation.Service;

/**
 * @author mazebin
 * @date 2021年 03月02日 21:15:21
 */
@Service("testservice")
public class TestService {
    @AutoWrited
    private MyBean myBean;
    public void print() {
        System.out.println(myBean);
        System.out.println("testService print");
    }
}