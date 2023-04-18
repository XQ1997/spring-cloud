package com.x.demo.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName TCPDemo8
 * @Description 服务器端
 * @Author X
 * @Date 2022/8/10 10:49
 * @Version 1.0
 **/
public class TCPDemo8 {
    public  static  void  main(String[]  args)   throws  Exception  {
        ServerSocket ss  =  new  ServerSocket(12036);
        while(true){
            Socket  s  =  ss.accept();
            new  Thread(new  MyUpdate(s)).start();
        }
    }
}
class  MyUpdate  implements  Runnable{
    private Socket s;
    public  MyUpdate(Socket  s)  {
        super();
        this.s  =  s;
    }
    @Override
    public  void  run()  {
        String  ip  =  s.getInetAddress().getHostAddress();
        System.out.println(ip+".........connected!");
        int  count  =  0;
        try  {
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            File file  =  new  File("E:/");
            File  f  =  new  File(file,"你好"+count+".txt");
            //如果写成if，就不可以！
            while(f.exists()){
                f  =  new  File(file,"你好"+(++count)+".txt");
            }
            PrintWriter pw =  new  PrintWriter(new FileWriter(f),true);
            PrintWriter pw2 = new  PrintWriter(s.getOutputStream(),true);
            String  line;
            while((line  =  br.readLine())  !=  null){
                pw.println(line);
            }
            pw2.println("恭喜您，上传成功！");
            s.close();
        }  catch  (Exception  e)  {
            e.printStackTrace();
        }
    }
}
