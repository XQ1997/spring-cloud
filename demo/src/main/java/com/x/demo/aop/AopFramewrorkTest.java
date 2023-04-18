package com.x.demo.aop;

import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName AopFramewrorkTest
 * @Description TODO
 * @Author X
 * @Date 2022/8/12 9:51
 * @Version 1.0
 **/
public class AopFramewrorkTest {
    public  static  void  main(String[]  args)throws Exception  {
        //读取配置文件的数据 config.property存放位置为resources文件夹下
        InputStream ips  = AopFramewrorkTest.class.getResourceAsStream("/config.property");
        //将配置文件中的配置解析成键值对的形式
       /* Properties properties = new Properties();
        //从输入字节流中读取属性列表(键和元素值)
        properties.load(ips);
        //中文乱码的原因 https://blog.csdn.net/fenglllle/article/details/125267150
        System.out.println(properties);
        String result=properties.getProperty("asdasd");
        result=new String(result.getBytes("ISO-8859-1"), "utf-8");
        System.out.println(result);*/
        //获取bean对象 需要在配置文件中写上bean的完全限定名
        //例如：test=com.x.demo.aop.Student
        Object  bean  =  new  BeanFactory(ips).getBean("test");
        System.out.println(bean.getClass().getName());
    }
}