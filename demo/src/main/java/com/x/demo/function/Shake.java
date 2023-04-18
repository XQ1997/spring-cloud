package com.x.demo.function;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName Shake
 * @Description java开发qq抖动窗口
 * @Author X
 * @Date 2022/7/9 11:46
 * @Version 1.0
 **/
@Slf4j
public class Shake extends JFrame {
    /**创建按钮*/
    private JButton btn = new JButton("点击抖动");
    /**创建窗口*/
    public Shake(){
        super("QQ窗口");
        //窗口的大小
        this.setSize(650,700);
        //显示窗口
        this.setVisible(true);
        //自动关闭我们内存
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置默认的布局样式
        this.setLayout(null);
        //添加按钮到窗口中
        this.add(btn);
        //设置按钮的位置和大小
        btn.setBounds(200,20,100,30);
        //给按钮添加监听功能
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取窗口的宽高
                int x = Shake.this.getX();
                int y = Shake.this.getY();
                //循环抖动
                for(int i = 0;i < 20;i++){
                    //& 按位与
                    if((i & 1) == 0){
                        x += 3;
                        y += 3;
                    }else{
                        x -= 3;
                        y -= 3;
                    }
                    Shake.this.setLocation(x,y);
                    try {
                        //线程休眠
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

  public static void main(String[] args) {
    new Shake();
    log.info("运行完成");
  }
}
