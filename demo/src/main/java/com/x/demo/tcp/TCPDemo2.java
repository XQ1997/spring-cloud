package com.x.demo.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName TCPDemo2
 * @Description 服务器端
 * @Author X
 * @Date 2022/8/10 10:17
 * @Version 1.0
 **/
public class TCPDemo2 {
  public static void main(String[] args) throws IOException {
    ServerSocket ss = new ServerSocket(10036);
    Socket s = ss.accept();
    String ip = s.getInetAddress().getHostAddress();
    System.out.println(ip+"..........connected!");
    InputStream in  =  s.getInputStream();
    byte[]  buf  =  new  byte[1024];
    /*int  len  =  in.read(buf);
    System.out.println(new  String(buf,0,len));*/
    int  len;
    while((len  =  in.read(buf))  !=  -1){
      System.out.println(new  String(buf,0,len));
    }
    OutputStream os  =  s.getOutputStream();
    os.write("哈哈你好！我是服务器！".getBytes());
    s.close();
    ss.close();
  }
}
