package com.x.demo.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @ClassName Demo3
 * @Description TODO
 * @Author X
 * @Date 2022/8/10 9:36
 * @Version 1.0
 **/
public class Demo3 {
  public static void main(String[] args) throws SocketException {
      DatagramSocket  sendDs  =  new  DatagramSocket();
      //服务器端端口须和客户端的端口保持一致
      DatagramSocket  receDs  =  new  DatagramSocket(10225);
      new  Thread(new  Send(sendDs)).start();
      new  Thread(new  Rece(receDs)).start();
  }
}
/**客户端 发送端 */
class Send implements Runnable {
    private DatagramSocket ds;
    public Send(DatagramSocket ds){
        super();
        this.ds = ds;
    }
    @Override
    public void run(){
        try{
            //数据源是键盘录入
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = br.readLine()) != null){
                byte[] buf = line.getBytes();
                DatagramPacket dp = new DatagramPacket (buf,buf.length,InetAddress.getByName("localhost"),  10225);
                ds.send(dp);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
/**服务器端，接收端 */
class Rece implements Runnable {
    private DatagramSocket ds;
    public Rece(DatagramSocket ds){
        super();
        this.ds = ds;
    }
    @Override
    public void run(){
        try{
            while (true){
                byte[] buf = new byte[1024];
                DatagramPacket  dp = new DatagramPacket(buf,0,buf.length);
                ds.receive(dp);
                String ip = dp.getAddress().getHostAddress();
                byte[] arr = dp.getData();
                String data = new String(arr,0,dp.getLength(),"UTF-8");
                System.out.println("接收ip结果为：" + ip);
                System.out.println("接收数据结果为：" + data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}