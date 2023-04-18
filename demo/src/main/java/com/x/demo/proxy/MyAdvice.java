package com.x.demo.proxy;

import java.lang.reflect.Method;

/**
 * @ClassName MyAdvice
 * @Description TODO
 * @Author X
 * @Date 2022/8/12 9:35
 * @Version 1.0
 **/
public  class  MyAdvice  implements   Advice  {
    long  beginTime  =  0;
    public  void  afterMethod(java.lang.reflect.Method method)   {
        System.out.println("从传智播客毕业上班啦！");
        long  endTime  =  System.currentTimeMillis();
        System.out.println(method.getName()  +  "running  time  of  "  +
                (endTime  -  beginTime));
    }
    public  void  beforeMethod(Method method)   {
        System.out.println("到传智播客来学习啦！");
        beginTime  =  System.currentTimeMillis();
    }
}
