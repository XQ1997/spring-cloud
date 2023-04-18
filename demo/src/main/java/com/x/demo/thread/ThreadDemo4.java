package com.x.demo.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ThreadDemo4
 * @Description TODO
 * @Author X
 * @Date 2022/8/9 8:51
 * @Version 1.0
 **/
public class ThreadDemo4 {
  public static void main(String[] args) {
      SellDemo3 s = new SellDemo3();
      new Thread(s,"A").start();
      new Thread(s,"B").start();
      new Thread(s,"C").start();
  }
}
class SellDemo3 implements Runnable{
    private int num = 50;
    private final ReentrantLock lock = new ReentrantLock();
    @Override
    public void run(){
        for(int i = 0;i <100;i++){
            gen();
        }
    }
    public void gen(){
        //lock必须紧跟try代码块
        lock.lock();
        try{
            //因为它不可以直接调用getName()方法，所以必须要获取当前线程
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            //unlock必须是finally的第一行
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "卖出第" + num + "张票！");
        }
    }
}