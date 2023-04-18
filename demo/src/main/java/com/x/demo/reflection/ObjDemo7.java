package com.x.demo.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ObjDemo7
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 18:00
 * @Version 1.0
 **/
public class ObjDemo7 {
    Map<String,Integer> map  =  new HashMap<String,Integer>();
    public  static  void  main(String[]  args)   throws  Exception  {
        Class  c  =  ObjDemo7.class;
        Field f  =  c.getDeclaredField("map");
        System.out.println(f);
        //输出结果为：java.util.Map com.x.demo.reflection.ObjDemo7.map
        //map
        System.out.println(f.getName());
        //输出结果为：map
        //  Class<?>  getType()返回一个Class对象，它标识了此Field对象所表示字段的声明类型。
        Class  cl  =  f.getType();
        System.out.println("获得其类型："+cl);
        //输出结果为：获得其类型：interface  java.util.Map
        /*Type  getGenericType()返回一个Type对象，它表示此
        Field对象 所表示字段的声明类型。 Type是Class的接口;*/
        Type t  =  f.getGenericType();
        //包含泛型的类型
        System.out.println(t);
        //输出结果为：java.util.Map<java.lang.String,  java.lang.Integer>
        /*Type这个类里面没有任何的方法，所以需要调用子类的方法，那么大的类型
         转到小的类型，需要强转！*/
        ParameterizedType pt  =  (ParameterizedType)t;
        //强转到其子类
        /* Type[]  getActualTypeArguments()
         返回表示此类型实际类型参数的  Type对象的数组。
         Type  getOwnerType()
         返回  Type对象，表示此类型是其成员之一的类型。
         Type  getRawType()
         返回  Type对象，表示声明此类型的类或接口。*/
        t  =  pt.getRawType();
        //类型的类或接口
        System.out.println(t);
        //输出结果为：interface java.util.Map
        Type[]  ts  =  pt.getActualTypeArguments();
        for  (Type  type  :  ts)  {
            System.out.println(type);
        }
        /*循环的输出结果为： class  java.lang.String
         class  java.lang.Integer */
    }
}
