package com.x.demo.reflection;

import java.lang.reflect.Constructor;

/**
 * @ClassName ObjDemo2
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 16:56
 * @Version 1.0
 **/
public class ObjDemo2 {
    public  static  void  main(String[]  args)   throws  Exception  {
        Class<Per>  c  =  Per.class;
        /*证明利用无参的可以 需要该类的构造方法不可以私有化。
        System.out.println(c.newInstance());*/
        //1.先获得需要被调用的构造器private修饰的构造方法
        Constructor<Per> con = c.getDeclaredConstructor();
        //使用默认的，什么都不要写
        System.out.println(con);
        //输出结果为：private com.x.demo.reflection.Per()
        /*con = c.getDeclaredConstructor(String.class);//获取指定的构造方法
        System.out.println(con);//输出结果为：private com.x.demo.reflection.Per(java.lang.String)*/
        //2.现在只需要执行这个构造器
        /* T  newInstance(Object...  initargs)
         使用此  Constructor对象表示的构造方法来创建该构造方法的声明
         类的新实例，并用指定的初始化参数初始化该实例。
         */
        //私有的成员是受保护的,不能直接访问，若要访问私有的成员,得先申请一下
        con.setAccessible(true);
        //允许访问
        Per  p =  con.newInstance();
        //成功，通过私有的受保护的构造方法创建了对象
        System.out.println("无参构造方法"+p);
        //输出结果为：无参构造方法对象！！！
        con  =  c.getDeclaredConstructor(String.class);
        System.out.println(con);
        //输出结果为：private com.x.demo.reflection.Per(java.lang.String)
        con.setAccessible(true);
        //允许访问 调用一次构造方法执行一次
        p  = con.newInstance("哈哈");
        //成功，通过私有的受保护的构造方法创建了对象
        System.out.println("String构造方法"+p);
        //输出结果为：String构造方法对象！！！
    }
}
class  Per{
    private  String  name;
    private  int  age;
    private  Per(){}
    private  Per(String  name){}
    public  String  toString()  {
        return  "对象！！！";
    }
}