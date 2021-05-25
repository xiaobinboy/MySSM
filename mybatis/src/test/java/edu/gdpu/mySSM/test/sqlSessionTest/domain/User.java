package edu.gdpu.mySSM.test.sqlSessionTest.domain;

/**
 * @author mazebin
 * @date 2021年 04月01日 23:23:27
 */
public class User {
    private String name = "binboy";
    private int age=11;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}