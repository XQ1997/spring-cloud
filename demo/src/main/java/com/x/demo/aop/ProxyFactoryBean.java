package com.x.demo.aop;

import com.x.demo.proxy.Advice;

import  java.lang.reflect.*;
/**
 * @ClassName ProxyFactoryBean
 * @Description TODO
 * @Author X
 * @Date 2022/8/12 9:49
 * @Version 1.0
 **/
public class ProxyFactoryBean {
    private  Object  target;
    private Advice advice;
    public  Object  getTarget()  {
        return  target;
    }
    public  void  setTarget(Object  target)   {
        this.target  =  target;
    }
    public  Advice  getAdvice()  {
        return  advice;
    }
    public  void  setAdvice(Advice  advice)   {
        this.advice  =  advice;
    }
    public  Object  getProxy()  {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                //这里的接口要和target实现相同的接口
                target.getClass().getInterfaces(),
                new  InvocationHandler()  {
                    public  Object  invoke(Object proxy,Method method,Object[] args)
                            throws  Throwable  {
                        //通过契约，使用其方法--before和after方法
                        advice.beforeMethod(method);
                        Object  value  =  method.invoke(target,   args);
                        advice.afterMethod(method);
                        return  value;
                    }
                }
        );
        return  proxy;
    }
}