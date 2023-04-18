package com.x.demo.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * @ClassName Demo5
 * @Description TODO
 * @Author X
 * @Date 2022/8/11 11:50
 * @Version 1.0
 **/
public class Demo5 {
    public  static  void  main(String[]  args)   {
        final  Frame  f  =  new  Frame("我的电脑");
        f.setBounds(300,  100,  600,  500);
        f.setLayout(new FlowLayout());
        JButton b  =  new  JButton("转到");
        JButton  okBut  =  new  JButton("确定");
        final  TextField  tf  =  new  TextField(60);
        final  TextArea  ta  =  new  TextArea(25,   70);
        f.add(tf);
        f.add(b);
        f.add(ta);
        f.setVisible(true);
        final  Dialog  d  =  new  Dialog(f,"提示信息",true);
        final  JLabel  lab  =  new  JLabel();
        //没有给出内容，用到的时候再给出！
        d.add(lab);
        //label标签加到Dialog上去！
        d.setBounds(400,  200,  240,  150);
        d.setLayout(new  FlowLayout());
        d.add(okBut);
        okBut.addKeyListener(new  KeyAdapter()  {
            public  void  keyPressed(KeyEvent  e){
                d.setVisible(false);
            }
        });
        //只可以对鼠标有作用！
        okBut.addActionListener(new  ActionListener(){
            public  void  actionPerformed(ActionEvent e)  {
                d.setVisible(false);
            }
        });
        d.addWindowListener(new  WindowAdapter()  {
            public  void  windowClosing(WindowEvent   e)  {
                d.setVisible(false);//对话框不显示
            }
        });
        //窗体上的操作
        f.addWindowListener(new  WindowAdapter()  {
            public  void  windowClosing(WindowEvent   e)  {
                System.exit(0);
            }
        });
        //设置键盘监听器，当输入enter键的时候实现和点击鼠标同样的功能！
        /*tf.addKeyListener(new  KeyAdapter()  {
            public  void  keyPressed(KeyEvent  e)   {
                if  (e.getKeyCode()  ==  10)  {
                //  buttonAction();
                    run(tf,ta,f,d,lab);
                }
                System.out.println(e.getKeyCode());
            }
        });*/
        //和上面被注释的代码实现的是同样的功能，也是键盘控制，不过不能设定哪个键，只有enter！
        tf.addActionListener(new  ActionListener()  {
            public  void  actionPerformed(ActionEvent   e)  {
                run(tf,ta,f,d,lab);
            //  System.out.println(text);
            }
        });
        //给转到添加键盘和鼠标双控制
        b.addActionListener(new  ActionListener()  {
            public  void  actionPerformed(ActionEvent   e)  {
                run(tf,ta,f,d,lab);
            }
        });
        b.addKeyListener(new  KeyAdapter()  {
            public  void  keyPressed(KeyEvent  e){
                run(tf,ta,f,d,lab);
            }
        });
    }
  /** 封装这一方法，为了方便使用(注意这个时候传递参数太多了，尽量避免这种情况的发生！
   在一个方法内部创建的对象只有在自己方法体里面才可以直接调用，而在外部方法 必须传递参数)
   */
  public static void run(TextField tf, TextArea ta, Frame f, Dialog d, JLabel lab) {
        String  dirPath  =  tf.getText();
        //获取文本（我们想验证的是路径），接下来获取文件
        File file  =  new  File(dirPath);
        //获取文件
        if  (file.exists()  && file.isDirectory())  {
            //判断，存在否以及是否是文件夹
            ta.setText("");
            //如果符合条件的话，清空以前的数据；
            String[]  names  =  file.list();
            for  (String  name  :  names)   {
                ta.append(name  +  "\r\n");
            }
        }else{
            //备注：应该在这里构建对话框，为了内存的优化，用到的时候才创建对象，用不到就不创建！
            String  info  =  "您输入的信息："+dirPath+"有误，请重新输入！";
            lab.setText(info);
            d.setVisible(true);
        }
    }
}
