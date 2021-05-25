package edu.gdpu.mySSM.test.javasetest;

import org.junit.Test;

/**
 * @author mazebin
 * @date 2021年 04月12日 22:45:28
 */
public class InterfaceTest {
    @Test
    public void test(){
        Say say = new SayImpl();
        if(Say.class.isAssignableFrom(say.getClass())){
            System.out.println(true);
            System.out.println((Say) say);
        }
    }
}
interface  Say{
    void say();
}
class SayImpl implements Say{
    @Override
    public void say() {
        System.out.println("hello spring mvc");
    }
}