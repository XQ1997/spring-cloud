package com.x.demo.tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName TCPDemo3
 * @Description 客户端
 * @Author X
 * @Date 2022/8/10 10:34
 * @Version 1.0
 **/
public class TCPDemo3 {
    public  static  void  main(String[]  args)   throws  Exception  {
        Socket s  =  new  Socket("localhost",12362);
        BufferedReader br =  new   BufferedReader(new FileReader("E:/你好.txt"));
        PrintWriter  pw  =  new PrintWriter(s.getOutputStream(),true);
        String  line;
        while((line  =  br.readLine())  !=  null){
            pw.println(line);
        }
        s.shutdownOutput();//阻塞式方法的应对，否则会一直等待！
        BufferedReader br2 = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String  str  =  br2.readLine();//服务器端反馈的数据
        System.out.println(str);
        br.close();
        s.close();
    }
}
