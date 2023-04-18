package com.x.demo.tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName TCPDemo7
 * @Description 客户端
 * @Author X
 * @Date 2022/8/10 10:47
 * @Version 1.0
 **/
public class TCPDemo7 {
    public  static  void  main(String[]  args)   throws  Exception  {
        Socket s  =  new  Socket("localhost",  12036);
        BufferedReader br  =  new  BufferedReader(new FileReader("E:/你好.txt"));
        PrintWriter  pw=new PrintWriter(s.getOutputStream(),true);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String  line;
        while((line  =  br.readLine())  !=  null){
            pw.println(line);
        }
        s.shutdownOutput();
        String  str  =  br2.readLine();
        System.out.println(str);
        s.close();
    }
}