package com.x.demo.bank;

import  java.util.ArrayList;
import  java.util.List;
/**
 * @ClassName NumberManager
 * @Description 1、定义一个用于存储上一个客户号码的成员变量和用于存储所有等待服务的客户号码 的队列集合。
 * 2、定义一个产生新号码的方法和获取马上要为之服务的号码的方法，这两个方法被不 同的线程操作了相同的数据，所以，要进行同步。
 * @Author X @Date 2022/8/12 11:17
 * @Version 1.0
 */
public  class  NumberManager  {
    private  int  lastNumber  =  0;
    private  List  queueNumbers  =   new  ArrayList();
    public  synchronized  Integer  generateNewNumber(){//为客户服务,客户来了取走一个队号
        queueNumbers.add(++lastNumber);
        return  lastNumber;
    }
    public  synchronized Integer  fetchNumber(){//服务于窗口,服务完一个叫下一个
    if (!queueNumbers.isEmpty()) {
            return  (Integer)queueNumbers.remove(0);
        }else{
            return  null;
        }
    }
}