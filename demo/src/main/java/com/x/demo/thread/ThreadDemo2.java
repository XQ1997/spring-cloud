package com.x.demo.thread;

/**
 * @ClassName ThreadDemo2
 * @Description join方法
 * @Author X
 * @Date 2022/8/8 17:05
 * @Version 1.0
 **/
public class ThreadDemo2 {
  public static void main(String[] args) {
    new Thread(new MyThreadDemo(),"嘿嘿").start();
    new Thread(new MyThreadDemo(),"哇哇").start();
  }
}
class MyThreadDemo implements Runnable{
    @Override
    public void run(){
        for(int i = 0;i< 50;i++){
            System.out.println(Thread.currentThread().getName() + "正在运行！" + i);
            if(i == 25){
                try{
                    //join作用使所属的线程对象线程正常执行run方法，而使当前线程进行无限期的阻塞，等待线程对象销毁后再继续执行当前线程后面的代码，
                    //具有使线程排队运行的作用，有些类似同步的运行效果
                    new Thread(new MyThreadDemo(),"哈哈").join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
