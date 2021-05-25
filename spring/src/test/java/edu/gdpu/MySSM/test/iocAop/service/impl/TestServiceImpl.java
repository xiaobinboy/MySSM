package edu.gdpu.MySSM.test.iocAop.service.impl;

import edu.gdpu.MySSM.test.iocAop.service.TestService;
import edu.gdpu.spring.context.annotation.Service;

/**
 * @author mazebin
 * @date 2021年 03月20日 23:15:46
 */
@Service("testservice")
//@Transactional
public class TestServiceImpl implements TestService {

    @Override
    public String print() {
        System.out.println("testService print");
        return "testService print";
    }
}