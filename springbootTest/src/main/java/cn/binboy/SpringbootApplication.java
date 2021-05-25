package cn.binboy;

import edu.gdpu.spring.context.annotation.ComponentScan;
import edu.gdpu.springboot.SpringApplication;

/**
 * @author mazebin
 * @date 2021年 05月11日 16:18:12
 */
@ComponentScan("cn.binboy")
public class SpringbootApplication {
    public static void main(String[] args) {
        new SpringApplication().run(SpringbootApplication.class);
    }
}