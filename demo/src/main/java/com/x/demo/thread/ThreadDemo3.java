package com.x.demo.thread;

/**
 * @ClassName ThreadDemo3
 * @Description TODO
 * @Author X
 * @Date 2022/8/8 17:46
 * @Version 1.0
 **/
public class ThreadDemo3 {
  public static void main(String[] args) {
    SellDemo sellDemo = new SellDemo();
    new Thread(sellDemo,"A").start();
    new Thread(sellDemo,"B").start();
    new Thread(sellDemo,"C").start();
  }
}
class SellDemo implements Runnable{
  private int num = 50;
  @Override
  public void run(){
    for(int i = 0;i< 200;i++){
      if(num > 0){
        try{
          //因为它不可以直接调用getName()方法，所以必须要获取当前线程
          Thread.sleep(10);
        }catch (InterruptedException e){
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "卖出第" + num-- + "张票！");
      }
    }
  }
}
