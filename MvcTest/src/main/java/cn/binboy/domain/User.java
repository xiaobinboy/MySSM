package cn.binboy.domain;

import edu.gdpu.spring.context.annotation.Component;

/**
 * @author mazebin
 * @date 2021年 05月02日 15:02:10
 */
@Component
public class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" +name + '\'' +
                ", age=" + age +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}