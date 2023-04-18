package com.x.demo.thread;

/**
 * @ClassName ThreadDemo
 * @Description 线程的两种方式
 * @Author X
 * @Date 2022/8/8 15:23
 * @Version 1.0
 **/
public class ThreadDemo{
    public static void main(String[] args) {
        for(int i = 0;i < 100;i++){
            if(i == 50){
                new ThreadTest("haha").start();
                new Thread(new YourThread(""),"嘿嘿").start();
            }
        }
    }
}
class ThreadTest extends Thread{
    private String name;
    public ThreadTest(String name){
        super();
        this.name = name;
    }
    public void run(){
        System.out.println("启动");
    }
}
class YourThread implements Runnable{
    private String name;
    public YourThread(String name){
        this.name = name;
    }
    @Override
    public void run(){
        for(int i = 0;i<3;i++){
            System.out.println(Thread.currentThread().getName() + "第" + i + "次启动！");
        }
    }
}


