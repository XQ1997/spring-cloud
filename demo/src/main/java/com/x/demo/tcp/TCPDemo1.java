package com.x.demo.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @ClassName TCPDemo1
 * @Description 客户端
 * @Author X
 * @Date 2022/8/10 10:13
 * @Version 1.0
 **/
public class TCPDemo1 {
  public static void main(String[] args) throws IOException {
    Socket s = new Socket("localhost",10036);
    OutputStream out = s.getOutputStream();
    out.write("你好，服务器，我是哈哈".getBytes());
    s.shutdownOutput();//注意：关闭标签
    InputStream is = s.getInputStream();
    byte[] buf = new byte[1024];
    int len = is.read(buf);
    System.out.println(new String(buf,0,len));
    s.close();
  }
}
