package com.x.demo.aop;

import com.x.demo.proxy.Advice;

import  java.io.IOException;
import  java.io.InputStream;
import  java.util.Properties;
/**
 * @ClassName BeanFactory
 * @Description TODO
 * @Author X
 * @Date 2022/8/12 9:44
 * @Version 1.0
 **/
public class BeanFactory {
    Properties  prop  =  new  Properties();
    //创建对象时需要传入一个配置文件中的数据，所以需要在构造方法中接受一个参数
    public  BeanFactory(InputStream  ips)   {
        try  {
            //将配置文件加载进来
            prop.load(ips);
        }  catch  (IOException  e)  {
            e.printStackTrace();
        }
    }
    //创建getBean方法，通过配置文件中的名字获取bean对象
    public  Object  getBean(String  name){
        //从配置文件中读取类名
        String  className  =  prop.getProperty(name);
        Object  bean  =  null;
        try  {
            //由类的字节码获取对象
            Class  clazz  =  Class.forName(className);
            bean  =  clazz.newInstance();
        }  catch  (Exception  e)  {
            e.printStackTrace();
        }
        //判断bean是特殊的bean即ProxyFactoryBean还是普通的bean
        if(bean  instanceof ProxyFactoryBean){
            Object  proxy  =  null;
            try  {
                //是ProxyFactoryBean的话，强转，并获取目标和通告
                ProxyFactoryBean proxyFactoryBean = (ProxyFactoryBean)bean;
                //获取advice和target
                Advice advice = (Advice)Class.forName(prop.getProperty(name + ".advice")).newInstance();
                Object  target  =  Class.forName(prop.getProperty(name + ".target")).newInstance();
                //设置目标和通告
                proxyFactoryBean.setAdvice(advice);
                proxyFactoryBean.setTarget(target);
                //通过类ProxyFactoryBean（开发中是作为接口存在）中获得proxy对象
                proxy  =  proxyFactoryBean.getProxy();
            }  catch  (Exception  e)  {
                e.printStackTrace();
            }
            //是ProxyFactoryBean的话，返回proxy对象
            return  proxy;
        }
        //否则返回普通bean对象
        return  bean;
    }
}