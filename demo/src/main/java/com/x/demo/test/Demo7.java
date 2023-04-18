package com.x.demo.test;

import  java.beans.BeanInfo;
import  java.beans.IntrospectionException;
import  java.beans.Introspector;
import  java.beans.PropertyDescriptor;
import  java.lang.reflect.InvocationTargetException;
import  java.lang.reflect.Method;

import  org.apache.commons.beanutils.BeanUtils;

/**
 * @ClassName Demo7
 * @Description TODO
 * @Author X
 * @Date 2022/8/11 15:00
 * @Version 1.0
 **/
public class Demo7 {
    public  static  void  main(String[]  args)   throws  Exception  {
        Student p  =  new  Student();
        p.setName("刘昭");
        String  propertiesName  =  "name";
        String  name  =  extracted(p,  propertiesName);
        System.out.println("1" + name);
        String  propertiesAge  =  "age";
        int  age  =  23;
        SetAge(p,propertiesAge,age);
        String name1 = BeanUtils.getProperty(p,"name");
        // 使用beanUtils工具包进行获取和设置属性（尽管这些属性是私有的，可是有方法啊）
        //使用beanUtils工具包获取实体类获取不到内部类的，只能获取public类型的实体类
        System.out.println(BeanUtils.getProperty(p,"name").getClass().getName());
        //System.out.println("3" + name1);
        BeanUtils.setProperty(p,"age",19);
        System.out.println("4" + p.getAge());
        /*打印结果
        *刘昭
        23
        java.lang.String
        刘昭
        19*/
    }
    private static void SetAge(Student p, String propertiesAge, int age)
            throws  IntrospectionException,  IllegalAccessException,
            InvocationTargetException  {
        PropertyDescriptor bp1 =  new PropertyDescriptor(propertiesAge,p.getClass());
        Method  methodSetAge  =  bp1.getWriteMethod();
        methodSetAge.invoke(p,age);
        System.out.println("2" + p.getAge());
    }
    private static String    extracted(Object p,  String propertiesName)
            throws  IntrospectionException,  IllegalAccessException,
            InvocationTargetException {
        /*PropertyDescriptor bp = new PropertyDescriptor(propertiesName,  p.getClass());
        Method  methodGetName  =  bp.getReadMethod();
        Object  readVal  =  methodGetName.invoke(p);
        System.out.println(readVal);*/
        BeanInfo beanInfo =  Introspector.getBeanInfo(p.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        Object  retVal  =  null;
        for(PropertyDescriptor  pd  :  pds){
            if(pd.getName().equals(propertiesName))            {
                Method  methodGetX  =  pd.getReadMethod();
                retVal  =  (String)methodGetX.invoke(p);
                break;
            }
        }
        return  (String)  retVal;
    }
}