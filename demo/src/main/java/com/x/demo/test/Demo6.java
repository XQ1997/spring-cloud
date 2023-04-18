package com.x.demo.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * @ClassName Demo6
 * @Description TODO
 * @Author X
 * @Date 2022/8/11 14:21
 * @Version 1.0
 **/
public class Demo6 {
    public  static  void  main(String[]  args)   {
        new  MyMenuDemo();
    }
}
class  MyMenuDemo{
    private  Frame  f;//首先声明对象的好处，全局可以调用！
    private  MenuBar  mb;
    private  Menu  m,subm;
    private  MenuItem  mi,close,save,open;
    private FileDialog openDia,saveDia;
    private  TextArea  ta;
    private  File  file;
    MyMenuDemo(){
        f  =  new  Frame("我的电脑");
        f.setBounds(400,  150,  500,  500);
        //备注：此时没有设置布局管理器类型（因为不设置的话下面的  TextArea会很爽！）
        mb  =  new  MenuBar();
        m  =  new  Menu("文件");
        save  =  new  MenuItem("保存");
        open  =  new  MenuItem("打开");
        subm  =  new  Menu("子菜单");
        close  =  new  MenuItem("退出");
        mi  =  new  MenuItem("子菜单2");
        openDia  =  new  FileDialog(f,  "我的打开",  FileDialog.LOAD);//加载
        saveDia  =  new  FileDialog(f,  "我的保存",  FileDialog.SAVE);//保存
        ta  =  new  TextArea();
        f.setMenuBar(mb);//添加
        mb.add(m);
        subm.add(mi);
        m.add(subm);
        m.add(open);
        m.add(save);
        m.add(close);
        f.add(ta);
        f.setVisible(true);
        init();
    }
    public
    void  init(){
        //打开文件，弹出对话框
        open.addActionListener(new  ActionListener(){
            public  void  actionPerformed(ActionEvent e)  {
                openDia.setVisible(true);
                String  path  =  openDia.getDirectory();
                String  name  =  openDia.getFile();
                //ta.append(path+"-----"+name+"\n");
                if(path==null  ||  name==null) {
                    return  ;
                }
                ta.setText("");//每次开始都清空
                file  =  new  File(path,  name);
                try  {
                    BufferedReader br = new BufferedReader(new FileReader(file));//缓冲流
                    String  line  =  null;
                    while((line  =  br.readLine())  !=  null){
                        ta.append(line+"\n");
                    }
                    br.close();
                }  catch  (IOException  e1)  {
                    e1.printStackTrace();
                }
            }
        });
        //保存文件，弹出对话框！
        save.addActionListener(new  ActionListener()  {
            public  void  actionPerformed(ActionEvent   e)  {
                //注意问题：只有文件第一次保存（不存在）的时候才需要你去弹出对话框，以后只保存，不弹出！
                if(file  ==  null){
                    saveDia.setVisible(true);//不存在才弹，创建文件
                    String  path  =  saveDia.getDirectory();
                    String  name  =  saveDia.getFile();
                    if(name  ==  null  ||  path   ==  null) {
                        return;
                    }
                    //目录和名字正确，但是文件不存在，就新建一个文件！
                    file  =  new  File(path,name);
                }
                try  {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    String  s  =  ta.getText();
                    bw.write(s);
                    bw.close();
                }  catch  (IOException e1)  {
                    e1.printStackTrace();
                }
            }
        });
        f.addWindowListener(new  WindowAdapter(){
            public  void  windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        close.addActionListener(new  ActionListener(){
            public  void  actionPerformed(ActionEvent   e)  {
                System.exit(0);
            }
        });
    }
}