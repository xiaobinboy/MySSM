package edu.gdpu.MySSM.test;

import edu.gdpu.spring.boot.ApplicationContextFactory;
import edu.gdpu.spring.context.ApplicationContext;
import org.junit.Test;

/**
 * @author mazebin
 * @date 2021年 02月28日 14:52:40
 */
public class ApplicationContextTest {
    @Test
    public void test() {

        ApplicationContext myApplicationContext = ApplicationContextFactory.getApplicationContext();
        myApplicationContext.setBean("person", new Person(10));
        Person bean = myApplicationContext.getBean(Person.class);
        Person person = (Person) myApplicationContext.getBean("person");
        System.out.println(bean + "-----" + person);
    }
}


