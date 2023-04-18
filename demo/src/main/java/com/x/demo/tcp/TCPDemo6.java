package com.x.demo.tcp;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName TCPDemo6
 * @Description 服务器端
 * @Author X
 * @Date 2022/8/10 10:43
 * @Version 1.0
 **/
public class TCPDemo6{
    public  static  void  main(String[]  args)   throws  Exception  {
        ServerSocket ss  =  new  ServerSocket(10256);
        Socket s  =  ss.accept();
        String  ip  =  s.getInetAddress().getHostAddress();
        System.out.println(ip+".........connected");
        InputStream is  =  s.getInputStream();
        FileOutputStream fos=new FileOutputStream("E:/我的照片0.jpg");
        byte  []b  =  new  byte[1024];
        int  len;
        while((len  =  is.read(b))  !=  -1){
            fos.write(b);
        }
        OutputStream os  =  s.getOutputStream();
        os.write("上传成功！".getBytes());
        s.close();
        ss.close();
    }
}
