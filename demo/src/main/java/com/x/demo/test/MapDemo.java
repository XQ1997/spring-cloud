package com.x.demo.test;

import java.util.*;
import java.util.Map.Entry;
/**
 * @ClassName MapDemo
 * @Description Map-Entry
 * @Author X
 * @Date 2022/8/9 15:20
 * @Version 1.0
 **/
public class MapDemo {
  public static void main(String[] args) {
    /*Map<Integer,String> map = new HashMap<Integer, String>();
    map.put(1,"jack");
    map.put(2,"rose");
    map.put(3,"lucy");
    //第一种方法
      Set s = map.entrySet();
      Iterator it = s.iterator();
      while(it.hasNext()){
          Map.Entry me = (Entry) it.next();
          System.out.println(me.getKey() + "--" + me.getValue());
      }
     //第二种方法
     s = map.keySet();//得到的是key的集合
     it = s.iterator();//然后将key迭代出来
     while(it.hasNext()){
         int i = (int)it.next();
         System.out.println(i + "-" + map.get(i));
      }*/
      List  list  =  Arrays.asList("will","Lucy","小强");
      System.out.println(list);//[will, Lucy, 小强]
      list.set(0,"你好");
      System.out.println(list);//[你好, Lucy, 小强]
      //list.add("22");//错误，返回一个受指定数组支持的固定大小的列表，不可以在添加
      //list.remove(0);//错误，不能删除
    System.out.println(list);
  }
}
