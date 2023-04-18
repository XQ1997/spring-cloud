package com.x.demo.test;

/**
 * @ClassName Demo2
 * @Description 适配器模式
 * @Author X
 * @Date 2022/8/5 15:01
 * @Version 1.0
 **/
public class Demo2 {
  public static void main(String[] args) {
    new MyWindows().close();
  }
}
interface Windows{
    void max();
    void min();
    void close();
}
/**适配器模式，实现接口所有的方法，但是不写方法体 */
class AdapterWindows implements Windows {
    @Override
    public void max(){}
    @Override
    public void min(){}
    @Override
    public void close(){}
}
class MyWindows extends AdapterWindows{

  /**覆写父类的方法 */
  public void close() {
      System.out.println("这个实现的是关闭功能");
  }
}