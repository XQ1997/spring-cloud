package com.x.demo.test;

import javax.swing.*;
import  java.awt.FlowLayout;
import  java.awt.Frame;
import  java.awt.TextField;
import  java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;
import  java.awt.event.KeyAdapter;
import  java.awt.event.KeyEvent;
import  java.awt.event.MouseAdapter;
import  java.awt.event.MouseEvent;
import  java.awt.event.WindowAdapter;
import  java.awt.event.WindowEvent;

/**
 * @ClassName Demo3
 * @Description TODO
 * @Author X
 * @Date 2022/8/11 11:08
 * @Version 1.0
 **/
public class Demo3 {
    //如果写成内部类的形式那么前面必须加上 public static，因为主方法是静态的，不能调用动态类或者方法
    public  static  void  main(String[]  args)   {
        //设置窗体
        Frame  f  =  new  Frame("窗体");
        f.setSize(400,  300);
        f.setLocation(500,  300);//距离左侧，距离上面
        /*可以用这个方法一次性设置
        setBounds(int  x,  int  y,  int  width,  int  height)
        移动组件并调整其大小。 */
        JButton  b  =  new  JButton("关闭窗体");
        JButton  b2  =  new  JButton("按钮2");
        TextField  tf  =  new  TextField(20);
        f.add(b);//把按钮添加到窗体上
        f.add(b2);//把按钮添加到窗体上
        f.add(tf);//在窗体上的的顺序按照添加的顺序
        f.setLayout(new FlowLayout());//设置容器的布局管理器
        //f.addWindowListener(new  MyWin());
        b.addActionListener(new ActionListener(){
            //通过匿名内部类，方便添加动作监听器
            public  void  actionPerformed(ActionEvent   e)  {
                System.out.println("按钮把界面关闭了");
                System.exit(0);
            }
        });
        //鼠标
        b.addMouseListener(new  MouseAdapter()  {
            //鼠标动作监听器
            int  count  =  1;
            public  void  mouseEntered(MouseEvent   e){
                System.out.println("鼠标移动到关闭窗体按钮"+(count++)+"次！");
            }
        });
        b.addMouseListener(new MouseAdapter(){
            //和上面的一样，可以写在一起
            int  clickCount  =  1;
            public  void  mouseClicked(MouseEvent   e){
                if(e.getClickCount()  ==  2){
                    System.out.println("双击动作"+clickCount++);
                }
            }
        });
        f.addWindowListener(new  WindowAdapter(){
            //匿名内部类的写法
            public  void  windowClosing(WindowEvent   e){
                System.out.println("我关");
                System.exit(0);
            }
            public  void  windowActivated(WindowEvent   e){
                System.out.println("我活了。");
            }
            public  void  windowOpened(WindowEvent   e){
                System.out.println("我被打开了,hahahhahah");
            }
        });
        //键盘：需要点击一下按钮2才可以触发
        b2.addKeyListener(new  KeyAdapter()  {
            public  void  keyPressed(KeyEvent  e){
                System.out.println("键盘的作用");//用鼠标按没反应，
                System.out.println(e.getKeyChar()+"---"+e.getKeyCode());//f---70等、
                if(e.getKeyCode()  ==  27){//按住esc键退出
                    System.out.println("ESC键把我关闭了！");
                    System.exit(0);
                }
                //组合键去关闭  CTRL  +  ENTER
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER){
                    System.out.println("CTRL +  ENTER组合键把我关闭了！");
                    System.exit(0);
                }
            }
        });
        //文本框
        tf.addKeyListener(new  KeyAdapter()  {
            public  void  keyPressed(KeyEvent  e){
                if(!(e.getKeyCode()>=KeyEvent.VK_0 && e.getKeyCode()<=KeyEvent.VK_9)){
                    System.out.println(e.getKeyChar()+"不符合是数字！");
                }
            }
        });
        //设置可见性
        f.setVisible(true);
    }
}
class  MyWin  extends WindowAdapter {
    public  void  windowClosing(WindowEvent   e){
        System.out.println("hahahha");
        System.exit(0);
    }
}