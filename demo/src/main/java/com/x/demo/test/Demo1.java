package com.x.demo.test;

/**
 * @ClassName Demo1
 * @Description 工厂模式
 * @Author X
 * @Date 2022/8/5 14:40
 * @Version 1.0
 **/
public class Demo1 {
  public static void main(String[] args) {
    Factory.show("Iphone");
    Factory.show("AndroidPhone");
    Factory.show("MyPhone");
    Factory.show("YourPhone");
    Factory.show("");
  }
}

interface Phone{
  /**制定规则，都要实现send()方法 */
  public void send();
}
class Iphone implements Phone{
    @Override
    public void send(){
        System.out.println("Iphone手机在发短信");
    }
}
class AndroidPhone implements Phone{
    @Override
    public void send() {
        System.out.println("AndroidPhone手机在发短信");
    }
}
class MyPhone implements Phone{
    @Override
    public void send() {
        System.out.println("MyPhone手机在发短信");
    }
}
class Factory{
    public static void show(String type){
        //传入参数，根据不同的类型个性化定制
        if(type.equals("")){
            // 为空的情况，不用往下执行
            System.out.println("对不起，类型为空，请重新输入！");
            return;
        }
        Phone p = null;
        if("Iphone".equals(type)){
            p = new Iphone();
        }else if("AndroidPhone".equals(type)){
            p = new AndroidPhone();
        }else {
            p = new MyPhone();
        }
        p.send();
    }
}