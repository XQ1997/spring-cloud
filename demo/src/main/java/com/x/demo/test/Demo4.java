package com.x.demo.test;

import javax.swing.*;
import  java.awt.FlowLayout;
import  java.awt.Frame;
import  java.awt.TextArea;
import  java.awt.TextField;
import  java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;
import  java.awt.event.KeyAdapter;
import  java.awt.event.KeyEvent;
import  java.awt.event.WindowAdapter;
import  java.awt.event.WindowEvent;
import  java.io.File;

/**
 * @ClassName Demo4
 * @Description TODO
 * @Author X
 * @Date 2022/8/11 11:32
 * @Version 1.0
 **/
public class Demo4 {
    public  static  void  main(String[]  args)   {
        new  MyFrame();
    }
}
class  MyFrame  {
    private  Frame  f;
    private JButton b;
    private  TextField  tf;
    private  TextArea  ta;

    MyFrame()  {
        init();
    }
    void  init()  {
        f  =  new  Frame("我的电脑");
        f.setBounds(300,  100,  600,  500);
        f.setLayout(new FlowLayout());
        b  =  new  JButton("转到");
        tf  =  new  TextField(60);
        ta  =  new  TextArea(25,  70);
        f.add(tf);
        f.add(b);
        f.add(ta);
        f.setVisible(true);
        action();
    }
    //窗体上的操作
    void  action()  {
        f.addWindowListener(new  WindowAdapter()  {
            public  void  windowClosing(WindowEvent   e)  {
                System.exit(0);
            }
        });
        buttonAction();
        keyAction();
    }
    void  keyAction(){
        //设置键盘监听器，当输入enter键的时候实现和点击鼠标同样的功能！
        b.addKeyListener(new  KeyAdapter()  {
            public  void  keyPressed(KeyEvent  e){
                /*if(e.getKeyCode()  ==  10){
                    buttonAction();
                }*/
                String  dirPath  =  tf.getText();//获取文本（我们想验证的是路径），接下来获取文件
                File  file  =  new  File(dirPath);//获取文件
                if  (file.exists()  && file.isDirectory())  {
                    //判断，存在否以及是否是文件夹
                    ta.setText("");//如果符合条件的话，清空以前的数据；
                    String[]  names  =  file.list();
                    for  (String  name  :  names)   {
                        ta.append(name  +  "\r\n");
                    }
                    System.out.println("=======");
                }  else  {
                    ta.setText("");
                    ta.append("对不起，请确认您输入的是路径！");
                }
                System.out.println(e.getKeyCode());
            }
        });
        tf.addActionListener(new  ActionListener()  {
            public  void  actionPerformed(ActionEvent   e)  {
                String  dirPath  =  tf.getText();
                //获取文本（我们想验证的是路径），接下来获取文件
                File  file  =  new  File(dirPath);//获取文件
                if  (file.exists()  && file.isDirectory())  {
                    //判断，存在否以及是否是文件夹
                    ta.setText("");//如果符合条件的话，清空以前的数据；
                    String[]  names  =  file.list();
                    for  (String  name  :  names)   {
                        ta.append(name  +  "\r\n");
                    }
                    System.out.println("=======");
                }  else  {
                    ta.setText("");
                    ta.append("对不起，请确认您输入的是路径！");
                }
            }
        });
    }
    void  buttonAction()  {
        b.addActionListener(new  ActionListener()  {
            public  void  actionPerformed(ActionEvent   e)  {
                String  dirPath  =  tf.getText();
                //获取文本（我们想验证的是路径），接下来获取文件
                File  file  =  new  File(dirPath);//获取文件
                if  (file.exists()  && file.isDirectory())  {
                    //判断，存在否以及是否是文件夹
                    ta.setText("");//如果符合条件的话，清空以前的数据；
                    String[]  names  =  file.list();
                    for  (String  name  :  names)   {
                        ta.append(name  +  "\r\n");
                    }
                    System.out.println("=======");
                }  else  {
                    ta.setText("");
                    ta.append("对不起，请确认您输入的是路径！");
                }
            }
        });
    }
}