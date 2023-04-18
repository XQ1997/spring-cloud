package com.x.demo.thread;

/**
 * @ClassName ThreadDemo1
 * @Description 线程卖票的例子
 * @Author X
 * @Date 2022/8/8 16:17
 * @Version 1.0
 **/
public class ThreadDemo1 {
  public static void main(String[] args) throws Exception{
    new SellTicket("A").start();
    new SellTicket("B").start();
    new SellTicket("C").start();

    new Thread(new MySell(),"D").start();
    new Thread(new MySell(),"E").start();
    new Thread(new MySell(),"F").start();

    for(int i = 10; i > 0;i--){
        System.out.println(i);
        Thread.sleep(1000);
    }
  }
}
class SellTicket extends Thread{
    private String name;
    private int num = 50;
    public SellTicket(String name){
        super();
        this.name = name;
    }
    public void run(){
        for(int i = 1;1 <= num;i++){
            System.out.println(name + "卖出了第" + i + "张票！");
        }
    }
}
class MySell implements Runnable{
    private int num = 50;
    @Override
    public void run(){
        for(int i = 1;i <= num; i++){
            System.out.println(Thread.currentThread().getName() + "卖出了第" + i + "张票！");
        }
    }
}
