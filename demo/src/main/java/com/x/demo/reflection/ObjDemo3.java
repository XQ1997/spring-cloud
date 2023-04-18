package com.x.demo.reflection;

import java.lang.reflect.Constructor;

/**
 * @ClassName ObjDemo3
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 17:18
 * @Version 1.0
 **/
public class ObjDemo3 {
    public  static  void  main(String[]  args)   throws  Exception  {
        Class<Color>  c  =  Color.class;
        Constructor<Color> con  =   c.getDeclaredConstructor();
        //（错误在这一行发生java.lang.NoSuchMethodException，就是说对枚举而言这种方法连构造器都获得不了，）
        // 编译可以通过，但是运行就通不过了！
        Color  co  =  (Color)  con.newInstance();
        System.out.println(co);
        //失败，证明对枚举而言不行，所以枚举的单例模式更加安全
        System.out.println(c.isEnum());
        //true是枚举
    }
}
enum  Color{
    RED,BLUE,GREEN;
    private  Color(){}
}