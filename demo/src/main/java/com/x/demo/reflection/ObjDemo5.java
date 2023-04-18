package com.x.demo.reflection;

import java.lang.reflect.Method;

/**
 * @ClassName ObjDemo5
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 17:42
 * @Version 1.0
 **/
public class ObjDemo5 {
    public  static  void  main(String[]  args)   throws  Exception{
        Class<VaryMethod>  c  =  VaryMethod.class;
        Method m  =  c.getMethod("show",int[].class);
        m.invoke(null,new  int[]{1,2,3});
        m  =  c.getMethod("show",String[].class);
        //m.invoke(null,new  String[]{"A","B","C"});//ERROR
        m.invoke(null,(Object)new  String[]{"A","B","C"});
        //YES,强转为Object类型
        m.invoke(null,new Object[]{new  String[]{"A","B","C"}});
        //推荐写法
    }
}
class  VaryMethod{
    public  static  void  show(int  ...args){
        System.out.println("基本数据类型传递过来了！");
    }
    /*public  static  void  show(int[]   args){//这是一样的}*/
    public  static  void  show(String  ...args){
        System.out.println("引用数据类型传递过来了！");
    }
}