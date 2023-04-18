package com.x.demo.reflection;

/**
 * @ClassName ObjDemo1
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 16:49
 * @Version 1.0
 **/
public class ObjDemo1 {
    public  static  void  main(String[]  args)   throws  Exception  {
        //传统方式创建对象
        System.out.println(new  User());
        //使用反射的方式
        Class<User>  c  =  User.class;
        User  u  =  c.newInstance();
        //直接newInstance的话必须保证默认的构造方法正常存在，也就是没有被私有化！这是前提条件）
        System.out.println(u);
    }
}
class  User{
    /**private  User(){//将默认的构造方法私有化的话就不可以再创建对象，两种方法都是这样}*/
    public  String  toString()  {
        return  "User对象创建成功！";
    }
}