package com.x.demo.reflection;

import java.lang.reflect.Field;

/**
 * @ClassName ObjDemo6
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 17:46
 * @Version 1.0
 **/
public class ObjDemo6 {
    public  static  void  main(String[]  args)   throws  Exception  {
        Class<Cat>  clz  =  Cat.class;
        //Field[]  f  =  clz.getDeclaredFields();
       /* for  (Field  field  :  f)  {
            System.out.println(field);
        }*/
        Field fi  =  clz.getDeclaredField("name");
       /* System.out.println(fi);
        System.out.println(fi.getName());*/
        //核心开始
        /* void  set(Object  obj,  Object  value)
         将指定对象变量上此  Field对象表示的字段设置为指定的新值。
         */
        Cat  c  =  clz.newInstance();
        fi.setAccessible(true);
        fi.set(c,  "哈哈");
        //赋值成功
        Object  o  =  fi.get(c);
        System.out.println(o);
        //取出成功
        /*fi  =  clz.getDeclaredField("age");
        fi.setAccessible(true);
        fi.set(c,  21);
        int  i  =  fi.getInt(c);*/
        //左边的接受类型已经写成了int，右边的返回类型就也必须是int
        //System.out.println(i);
        //获取成功
    }
}
class  Cat{
    private  String  name;
    public  int  age;
    private  String  color;
}