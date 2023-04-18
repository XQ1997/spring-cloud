package com.x.demo.reflection;

import  java.lang.reflect.Constructor;
/**
 * @ClassName Demo
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 14:20
 * @Version 1.0
 **/
public class Demo {
  public static void main(String[] args) throws Exception {
      //得到所有的构造器(先得到类)
      Class<Emp> c = Emp.class;
      /* Constructor<?>[]  getConstructors()
      返回一个包含某些Constructor对象的数组，这些对象反映此
      Class对象所表示的类的所有公共构造方法。*/
      //前面的修饰符必须是public才可以在这个方法下获取到
      Constructor[]  con   =  c.getConstructors();
      for(Constructor cons : con){
          //如果上面的某构造器public去掉，则显示不出
          System.out.println("公共构造方法(必须带public修饰)：" + cons);
      }
      //上面的for循环输出结果为：公共构造方法(必须带public修饰)：public com.x.demo.reflection.Emp(java.lang.String,int)
      //得到指定的构造器,也是必须public
      Constructor  c1  =  c.getConstructor(String.class,int.class);
      System.out.println(c1);
      //输出为：public com.x.demo.reflection.Emp(java.lang.String,int)
      System.out.println("====================================");
      //现在想获得不受public影响的,getDeclaredConstructors(),暴力反射
      con  =  c.getDeclaredConstructors();
      for  (Constructor  cons  :  con)   {
          //此时不受修饰符的影响
          System.out.println("构造方法(所有，不只是public)："+cons);
      }
      /*上面的for循环输出结果为
        构造方法(所有，不只是public)：private com.x.demo.reflection.Emp()
        构造方法(所有，不只是public)：com.x.demo.reflection.Emp(java.lang.String)
        构造方法(所有，不只是public)：public com.x.demo.reflection.Emp(java.lang.String,int)*/
  }
}
class Emp{
    private String name;
    private int age;
    private Emp(){}
    Emp(String name){}
    public Emp(String name,int age){}
}
