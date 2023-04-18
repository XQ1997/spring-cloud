package com.x.demo.test;

/**
 * @ClassName Demo
 * @Description 模板模式
 * @Author X
 * @Date 2022/8/5 14:21
 * @Version 1.0
 **/
public class Demo {
    public static void main(String[] args){
        Square s = new Square(5d);
        s.show();
        Cirle c = new Cirle(4d);
        c.show();
    }
}
/**模板模式 抽象类中包含很多的抽象方法，子类必须去覆写 */
abstract class Method {
  /** 返回值类型如果是void的话，下面报错，因为没有返回值，无法引用*/
  abstract double mul();

    abstract double divid();
    void show(){
        System.out.println("周长是：" + mul());
        System.out.println("面积是：" + divid());
    }
}

class Square extends Method{
    double d;
    public Square(double d){
        super();
        this.d = d;
    }
    @Override
    double mul(){
        return d*d;
    }
    @Override
    double divid(){
        return 4*d;
    }
}
class Cirle extends Method{
    double r;
    public Cirle(double r){
        super();
        this.r = r;
    }
    @Override
    double mul(){
        return 2 * 3.14 * r;
    }
    @Override
    double divid(){
        return 3.14 * r * r;
    }
}
