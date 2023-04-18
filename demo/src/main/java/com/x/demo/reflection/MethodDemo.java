package com.x.demo.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName MethodDemo
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 14:39
 * @Version 1.0
 **/
/*@Deprecated表示不推荐使用，但是还可以使用*/
@Deprecated
public class MethodDemo extends AB{
    void show(){}
    public void say(){}
    private int age;
    public char c;
    private boolean b;
    public static void main(String[] args) throws Exception{
       Class<MethodDemo> c = MethodDemo.class;
       //获取所有的(包含父类的方法)public修饰的方法
        Method[] m = c.getMethods();
        for(Method method : m){
            System.out.println("方法：" + method);
        }
        /*上面的循环的输出结果为：
        * 方法：public static void com.x.demo.reflection.MethodDemo.main(java.lang.String[]) throws java.lang.Exception
        方法：public void com.x.demo.reflection.MethodDemo.say()
        方法：public final void java.lang.Object.wait() throws java.lang.InterruptedException
        方法：public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
        方法：public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
        方法：public boolean java.lang.Object.equals(java.lang.Object)
        方法：public java.lang.String java.lang.Object.toString()
        方法：public native int java.lang.Object.hashCode()
        方法：public final native java.lang.Class java.lang.Object.getClass()
        方法：public final native void java.lang.Object.notify()
        方法：public final native void java.lang.Object.notifyAll()*/
        // 总结：4个方法，获取全部，获取特定；不受修饰符影响的全部，不受修饰符影响的特定；(前两个还是受限制的)
        System.out.println("=========================");
        //获取指定的方法
        Method me = c.getMethod("main",String[].class);
        System.out.println("main方法：" + me);
        // 输出结果为：main方法：public static void com.x.demo.reflection.MethodDemo.main(java.lang.String[])
        // throws java.lang.Exception
        System.out.println("===========================================");
        //访问所有方法，不受访问权限影响
        m = c.getDeclaredMethods();
        for(Method method : m){
            System.out.println("不受影响的：" + method);
        }
        /*上面的循环输出的结果为：
        * 不受影响的：public static void com.x.demo.reflection.MethodDemo.main(java.lang.String[]) throws java.lang.Exception
        不受影响的：void com.x.demo.reflection.MethodDemo.show()
        不受影响的：public void com.x.demo.reflection.MethodDemo.say()*/
        System.out.println("================================");
        me = c.getDeclaredMethod("show");
        System.out.println(me);
        // 输出结果为：void com.x.demo.reflection.MethodDemo.show()
        System.out.println("===========================");
        me = c.getMethod("toString");
        System.out.println(me);
        // 输出结果为：public java.lang.String java.lang.Object.toString()
        /*Method[]  getDeclaredMethods()返回Method对象的一个数组，这些对象反映此
        Class对象表示的类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，
        但不包括继承的方法,只可以对当前类有效*/
        System.out.println("================");
        /*me = c.getDeclaredMethod("toString");
        System.out.println(me);*/
        // 运行报错java.lang.NoSuchMethodException ERROR,c.getDeclaredMethod() 不能得到继承的方法
        System.out.println("===================");
        //得到字段
        Field[] f = c.getFields();
        for(Field field : f){
          // 只得到public的
          System.out.println("字段：" + field);
        }
        // 上面的循环结果为：字段：public char com.x.demo.reflection.MethodDemo.c
        System.out.println("========================");
        //特定字段
        Field fi = c.getField("c");
        System.out.println(fi);
        // 输出结果为：public char com.x.demo.reflection.MethodDemo.c
        System.out.println("==============================");
        //得到不受限定名限定的全部字段
        f = c.getDeclaredFields();
        for(Field field : f){
          // 得到不受修饰符限定的字段，但是只对当前类有效
          System.out.println("全部字段：" + field);
        }
        /*上面的循环结果为：全部字段：private int com.x.demo.reflection.MethodDemo.age
        全部字段：public char com.x.demo.reflection.MethodDemo.c
        全部字段：private boolean com.x.demo.reflection.MethodDemo.b*/
        System.out.println("==============");
        //所有注解
        Annotation[] a = c.getAnnotations();
        System.out.println(a.length);
        //输出结果为：1
        for(Annotation annotation : a){
            System.out.println(annotation);
        }
        // 上面循环的结果为：@java.lang.Deprecated()
        System.out.println("=============================");
        //特定注解
        Deprecated d = c.getAnnotation(Deprecated.class);
        System.out.println(d);
        //输出结果为：@java.lang.Deprecated()
    }
}
class AB{
    protected String name;
    protected String id;
}