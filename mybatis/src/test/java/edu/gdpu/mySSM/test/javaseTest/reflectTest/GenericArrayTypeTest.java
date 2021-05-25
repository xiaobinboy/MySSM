package edu.gdpu.mySSM.test.javaseTest.reflectTest;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 04月02日 13:55:35
 * GenericArrayType: 表示一种元素类型是参数化类型或者类型变量的数组类型
 */
public class  GenericArrayTypeTest {
  String [] key;
List<String> key1;
Student[] students;
  @Test
  public void test() throws Exception{
      Type key = this.getClass().getDeclaredField("students").getGenericType();
      System.out.println(key instanceof GenericArrayType);
      System.out.println(key);
      if(key instanceof GenericArrayType){
          GenericArrayType arrayType = (GenericArrayType) key;
          System.out.println(arrayType.getGenericComponentType());
      }

  }
  public static class  Student{

  }
}
