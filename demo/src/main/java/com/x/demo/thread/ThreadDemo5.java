package com.x.demo.thread;

import lombok.SneakyThrows;

/**
 * @ClassName ThreadDemo5
 * @Description 线程通信
 * @Author X
 * @Date 2022/8/9 9:38
 * @Version 1.0
 **/
public class ThreadDemo5 {
  public static void main(String[] args) {
    Person person = new Person();
    new Thread(new Producer(person)).start();
    new Thread(new Consumer(person)).start();
  }
}
class Person{
    private String name;
    private String sex;
    /** 内容区为空*/
    private Boolean isimpty = Boolean.TRUE;
    @SneakyThrows
    public void set(String name, String sex){
       synchronized (this){
           this.name = name;
           this.sex = sex;
           //创造结束后修改属性
           isimpty = Boolean.FALSE;
           this.notifyAll();
           while(isimpty.equals(Boolean.TRUE)){
               //不为空的话等待消费者消费
               this.wait();
           }
       }
    }
    @SneakyThrows
    public void get(){
       synchronized (this){
           while(isimpty.equals(Boolean.FALSE)){
               System.out.println("消费：" + this);
               isimpty = Boolean.TRUE;
               this.wait();
               this.notifyAll();
           }
       }
    }

    @Override
    public String toString() {
        return name + sex + isimpty;
    }
}
class Producer implements Runnable{
    private Person p;
    public Producer(Person p){
        super();
        this.p = p;
    }
    @Override
    public void run(){
         for(int i = 0;i < 100;i++){
             System.out.println("生产run " + i);
             if(i % 2 == 0){
                p.set("哈哈","男");
             }else {
               p.set("嘿嘿","女");
             }
       }
    }
}
class Consumer implements Runnable{
    private Person person;
    public Consumer(Person person){
        super();
        this.person = person;
    }
    @Override
    public void run(){
        for(int i = 0;i < 100;i++){
            person.get();
       }
    }
}
