package com.x.demo.proxy;

import java.lang.reflect.Method;

/**
 * @ClassName Advice
 * @Description TODO
 * @Author X
 * @Date 2022/8/12 9:34
 * @Version 1.0
 **/
public interface Advice{
    void  beforeMethod(java.lang.reflect.Method method);
    void  afterMethod(Method method);
}
