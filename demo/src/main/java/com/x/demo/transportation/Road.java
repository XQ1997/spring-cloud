package com.x.demo.transportation;

import  java.util.ArrayList;
import  java.util.List;
import  java.util.Random;
import  java.util.concurrent.ExecutorService;
import  java.util.concurrent.ScheduledExecutorService;
import  java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/**
 * @ClassName Road
 * @Description 每个Road对象代表一条路线，总共有12条路线，即系统中总共要产生12个Road实例对 象。
 * 每条路线上随机增加新的车辆，增加到一个集合中保存。
 * 每条路线每隔一秒都会检查控制本路线的灯是否为绿，
 * 是则将本路线保存车的集合中的 第一辆车移除，即表示车穿过了路口。
 * @Author X
 * @Date 2022/8/12 10:43 @Version 1.0
 */
public class Road {
    private List<String> vechicles  =   new ArrayList<String>();
    private  String  name  =null;
    /*在这个构造函数中,传回哪个方向的车,
    先开启一个线程池用于产生车辆,一个定时器用于观察交通灯状态*/
    public  Road(String  name){
        this.name  =  name;
        //模拟车辆不断随机上路的过程
        //使用线程池，通过产生单个线程的方法，创建一个线程池
        ExecutorService pool  =  newSingleThreadExecutor();
        pool.execute(new  Runnable(){
            public  void  run(){
                for(int  i=1;i<1000;i++){
                    try  {
                        Thread.sleep((new Random().nextInt(10) +  1) * 1000);
                    }  catch  (InterruptedException  e)  {
                        e.printStackTrace();
                    }
                    vechicles.add(Road.this.name  +  "_"  +   i);
                }
            }
        });
        //每隔一秒检查对应的灯是否为绿，是则放行一辆车
        //产生一个单线程，创建定时器
        ScheduledExecutorService timer = newScheduledThreadPool(1);
        timer.scheduleAtFixedRate(
                new  Runnable(){
                    public  void  run(){
                        for(int  i=1;i<1000;i++){
                            try {
                                Thread.sleep((new Random().nextInt(10) +  1) * 1000);//判断路上是否有车，有则进行相应的操作
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if(vechicles.size()>0){
                                boolean lighted = Lamp.valueOf(Road.this.name).isLighted();
                                //每隔1秒让车通行，通行前要先判断灯是否亮，亮了才能通行，即从集合中移除
                                if(lighted){
                                    System.out.println(vechicles.remove(0) +  "  is  traversing  !");
                                }
                            }
                        }
                    }
                },
                1,
                1,
                TimeUnit.SECONDS
        );
    }
}
