package com.x.demo.tcp;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName TCPDemo4
 * @Description 服务器端
 * @Author X
 * @Date 2022/8/10 10:35
 * @Version 1.0
 **/
public class TCPDemo4 {
    public  static  void  main(String[]  args)   throws  Exception  {
        ServerSocket ss  =  new  ServerSocket(12362);
        Socket s  =  ss.accept();
        String  ip  =  s.getInetAddress().getHostAddress();
        System.out.println(ip+".....connected!");
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(new FileWriter("E:/你好2.txt"),true);
        //建议打印时都用打印流
        String  line;
        while((line  =  br.readLine())  !=  null){
            //不可以写成是write();
            pw.println(line);
        }
        PrintWriter  out =  new  PrintWriter(s.getOutputStream(),true);
        out.println("上传成功！");
        s.close();
        ss.close();
    }
}
