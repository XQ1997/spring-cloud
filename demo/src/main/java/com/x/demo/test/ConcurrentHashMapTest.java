package com.x.demo.test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author X
 * @ClassName Test
 * @Description 测试ConcurrentHashMap
 * @Date 2022/1/27 10:46
 * @Version 1.0
 **/
public class ConcurrentHashMapTest {

  public static void main(String[] args) {
    //线程不安全
    HashMap hashMap = new HashMap();
    //线程安全
    ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    for(int i = 0;i < 100;i++){
        new Thread(
                () -> {
                    String key;
                    for(int n = 0;n < 100;n++){
                        key = n + Thread.currentThread().getName();
                        hashMap.put(key,n);
                        concurrentHashMap.put(key,n);
                    }
                }
        ).start();
    }
    System.out.println(String.format("hashMap.size()=%s",hashMap.size()));
    System.out.println(String.format("concurrentHashMap.size()=%s",concurrentHashMap.size()));
  }
}
