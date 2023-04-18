package com.x.demo.tcp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @ClassName TCPDemo5
 * @Description 客户端
 * @Author X
 * @Date 2022/8/10 10:41
 * @Version 1.0
 **/
public class TCPDemo5 {
    public  static  void  main(String[]  args)   throws  Exception  {
        Socket s  =  new  Socket("localhost",10256);
        FileInputStream fis =  new  FileInputStream("E:/logo.jpg");
        OutputStream os  =  s.getOutputStream();
        byte  []buf  =  new  byte[1024];
        int  len;
        while((len  =  fis.read(buf))  !=  -1){
            os.write(buf);
        }
        s.shutdownOutput();
        InputStream in  =  s.getInputStream();
        byte  []b  =  new  byte[1024];
        int  i  =  in.read(b);
        System.out.println(new  String(b,0,i));
        fis.close();
        s.close();
    }
}
