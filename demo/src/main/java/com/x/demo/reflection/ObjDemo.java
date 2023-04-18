package com.x.demo.reflection;

import java.lang.reflect.Field;

/**
 * @ClassName ObjDemo
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 16:37
 * @Version 1.0
 **/
public class ObjDemo {
  public static void main(String[] args)throws Exception{
    Stu s = new Stu("哈哈","男",12);
    Class<Stu> c = Stu.class;
    Field f = c.getField("name");
    //修改对象的值 给Stu对象身上取
    f.set(s,"嘿嘿");
    System.out.println(f.get(s));
    //输出结果为：嘿嘿
  }
}
class Stu{
    public String name;
    public String sex;
    public int age;
    public Stu(String name,String sex,int age){
        super();
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
}
