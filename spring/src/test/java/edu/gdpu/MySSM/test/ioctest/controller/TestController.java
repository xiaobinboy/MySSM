package edu.gdpu.MySSM.test.ioctest.controller;

import edu.gdpu.MySSM.test.ioctest.service.TestService;
import edu.gdpu.spring.context.annotation.AutoWrited;
import edu.gdpu.spring.context.annotation.Controller;

/**
 * @author mazebin
 * @date 2021年 03月02日 21:11:53
 */
@Controller
public class TestController {
    @AutoWrited
    private TestService service;

    public void print() {
        System.out.println("TestController print");

        service.print();
    }
}