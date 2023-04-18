package com.x.demo.reflection;

import java.lang.reflect.Method;

/**
 * @ClassName ObjDemo4
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 17:23
 * @Version 1.0
 **/
public class ObjDemo4 {
  public static void main(String[] args)throws Exception {
    //传统方式：String name = new Dept().show("哈哈");
      /*Method  getMethod(String  name, Class<?>...  parameterTypes)
       返回一个  Method对象，它反映此Class对象所表示的类或接口的指定公共成员方法。
       name  -方法名
       parameterTypes  -参数列表*/
      //想要通过反射来调用Dept中的方法
      Class<Dept> c = Dept.class;
      Method m = c.getMethod("show",String.class);
      Object o = m.invoke(c.newInstance(),"哈哈");
      System.out.println(o);
      //私有化的方法 无参方法
      m = c.getDeclaredMethod("privateShow");
      m.setAccessible(true);
      o = m.invoke(c.newInstance());
      //静态方法的调用
      m = c.getMethod("staticShow");
      m.invoke(null);
      //staticShow为静态方法，不需要创建对象，所以这里会是null
  }
}
class Dept{
    public String show(String name){
        //使用反射的方法来调用正常的方法
        return name + "您好！";
    }
    private void privateShow(){
        // 用反射来实现对私有化方法的调用
        System.out.println("privateShow");
    }
    public static void staticShow(){
        System.out.println("staticShow");
    }
}