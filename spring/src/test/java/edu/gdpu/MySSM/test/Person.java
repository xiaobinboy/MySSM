package edu.gdpu.MySSM.test;

/**
 * @author mazebin
 * @date 2021年 02月28日 19:32:31
 */

public class Person {
    int age;

    public Person(int age) {
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                '}';
    }

}