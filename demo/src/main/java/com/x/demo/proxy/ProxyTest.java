package com.x.demo.proxy;

import  java.lang.reflect.Constructor;
import  java.lang.reflect.InvocationHandler;
import  java.lang.reflect.Method;
import  java.lang.reflect.Proxy;
import  java.util.ArrayList;
import  java.util.Collection;

/**
 * @ClassName ProxyTest
 * @Description TODO
 * @Author X
 * @Date 2022/8/12 9:19
 * @Version 1.0
 **/
public class ProxyTest {
    public  static  void  main(String[]  args)   throws  Exception{
        //接收两个参数，一个是后边参数的字节码的加载器，一个是所要实现代理的接口的字节码
        Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(),Collection.class);
        System.out.println(clazzProxy1.getName());//打印代理的名字 $Proxy0
        System.out.println("----------begin constructors list----------");
        /*想打印出如下格式：
        *  $Proxy0()
        $Proxy0(InvocationHandler,int)*/
        Constructor[] constructors = clazzProxy1.getConstructors();//获取代理的构造函数
        for(Constructor  constructor  :  constructors){
            String  name   =  constructor.getName();//获取代理的构造函数的
            StringBuilder  sBuilder  =  new  StringBuilder(name);
            sBuilder.append('(');
            Class[]  clazzParams  =  constructor.getParameterTypes();//获取代理的构造函数的参数
            for(Class  clazzParam  :  clazzParams){
                sBuilder.append(clazzParam.getName()).append(',');
            }
            //稳妥的判断是否是一个参数，不是就删掉最后的，
            if(clazzParams!=null  &&  clazzParams.length !=  0){
                sBuilder.deleteCharAt(sBuilder.length()-1);
            }
            sBuilder.append(')');
            System.out.println(sBuilder.toString());
            //$Proxy0(java.lang.reflect.InvocationHandler)
        }
        System.out.println("----------begin list----------");
        /*$Proxy0()
        $Proxy0(InvocationHandler,int)*/
        Method[]  methods  =  clazzProxy1.getMethods();//获取代理身上的方法
        for(Method  method  :  methods){
            String  name  =  method.getName();
            StringBuilder  sBuilder  =  new  StringBuilder(name);
            sBuilder.append('(');
            Class[]  clazzParams  =  method.getParameterTypes();
            for(Class  clazzParam  :  clazzParams){
                sBuilder.append(clazzParam.getName()).append(',');
            }
            if(clazzParams!=null  &&  clazzParams.length   !=  0){
                sBuilder.deleteCharAt(sBuilder.length()-1);
            }
            sBuilder.append(')');
            System.out.println(sBuilder.toString());
        }
        System.out.println("----------begin create instance object----------");
        //Object  obj  =  clazzProxy1.newInstance();
        //方式一：通过接口的子类创建对象
        Constructor  constructor  = clazzProxy1.getConstructor(InvocationHandler.class);
        //方式二：匿名内部类
        Collection proxy1=(Collection)constructor.newInstance(new MyInvocationHandler1());
        System.out.println(proxy1);//没有错误
        proxy1.clear();//没有错误
        //proxy1.size();报错，因为，代理调用size方法，其实是调用了MyInvocationHandler1中的invoke，他的返回值是null
        //System.out.println("111111111111111");//调试用的
        //用了匿名内部类的方法实现
        Collection  proxy2 =(Collection)constructor.newInstance(new InvocationHandler(){
            public Object invoke(Object proxy,Method method,Object[] args)throws  Throwable  {
                return  null;
            }
        });
        /*下边这部分代码非常重要和精辟*/
        final  ArrayList  target  =  new  ArrayList();
        Collection proxy3 = (Collection)getProxy(target,new MyAdvice());
        proxy3.add("zxx");
        proxy3.add("lhm");
        proxy3.add("bxd");
        System.out.println(proxy3.size());
        System.out.println(proxy3.getClass().getName());
    }
    private  static  Object  getProxy(final  Object  target,final  Advice advice)  {
        //方式三，newProxyInstance这个方法需要三个参数，可以直接创建target的代理对象
        Object  proxy3  =  Proxy.newProxyInstance(target.getClass().getClassLoader(),
            /*new  Class[]{Collection.class},*/
            //获取target上的接口
            target.getClass().getInterfaces(),
            new  InvocationHandler(){
                public  Object  invoke(Object proxy,Method method,Object[]  args)throws Throwable{
                    /*long  beginTime  =  System.currentTimeMillis();
                    Object  retVal  =  method.invoke(target,   args);
                    long  endTime  =  System.currentTimeMillis();
                    System.out.println(method.getName()  + " running
                    time  of  "  +  (endTime  -  beginTime));
                    return  retVal;*/
                    //把上边的代码封装到一个类中，让后调用该类的方法，就实现了方法的封装
                    advice.beforeMethod(method);
                    Object  retVal  =  method.invoke(target,   args);
                    advice.afterMethod(method);
                    return  retVal;
                }
            }
        );
        return  proxy3;
    }
}
//获取代理身上的构造函数
//创建内部类MyInvocationHandler1，目的是传递给代理的构造器
class  MyInvocationHandler1 implements InvocationHandler{
    public Object  invoke(Object proxy,Method method,Object[] args)throws Throwable{
        return  null;
    }
}
/*输出结果如下：
    com.sun.proxy.$Proxy0
----------begin constructors list----------
com.sun.proxy.$Proxy0(java.lang.reflect.InvocationHandler)
----------begin list----------
add(java.lang.Object)
remove(java.lang.Object)
equals(java.lang.Object)
toString()
hashCode()
clear()
contains(java.lang.Object)
isEmpty()
iterator()
size()
toArray([Ljava.lang.Object;)
toArray()
spliterator()
addAll(java.util.Collection)
stream()
forEach(java.util.function.Consumer)
containsAll(java.util.Collection)
removeAll(java.util.Collection)
removeIf(java.util.function.Predicate)
retainAll(java.util.Collection)
parallelStream()
isProxyClass(java.lang.Class)
newProxyInstance(java.lang.ClassLoader,[Ljava.lang.Class;,java.lang.reflect.InvocationHandler)
getProxyClass(java.lang.ClassLoader,[Ljava.lang.Class;)
getInvocationHandler(java.lang.Object)
wait()
wait(long,int)
wait(long)
getClass()
notify()
notifyAll()
----------begin create instance object----------
null
到传智播客来学习啦！
从传智播客毕业上班啦！
addrunning  time  of  0
到传智播客来学习啦！
从传智播客毕业上班啦！
addrunning  time  of  0
到传智播客来学习啦！
从传智播客毕业上班啦！
addrunning  time  of  0
到传智播客来学习啦！
从传智播客毕业上班啦！
sizerunning  time  of  0
3
com.sun.proxy.$Proxy1*/