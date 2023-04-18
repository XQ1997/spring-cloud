package com.x.demo.plane;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * @ClassName MyGameFrame
 * @Description 画游戏窗口
 * @Author X
 * @Date 2022/8/1 11:34
 * @Version 1.0
 **/
public class MyGameFrame extends Frame {
    Image  planeImg = GameUtil.getImage("images/plane.png");
    Image  bg = GameUtil.getImage("images/bg.jpg");
    Plane p1 = new Plane(planeImg,220,220,7);
    Shell[] shells = new Shell[50];
    Explode explode;    //爆炸

    Date start = new Date();     //游戏开始的时间
    Date end;       //游戏结束的时间(飞机死的那一刻)
    long period = 0;   //玩了多少秒

  /** g当做是一支画笔
   * @param g the specified Graphics window
   */
  @Override
  public void paint(Graphics g) {
        //画背景
        g.drawImage(bg,0,0,500,500,null);
        //画时间
        drawTime(g);
        //画飞机
        p1.drawMyself(g);
        //画炮弹
        for(int i=0;i<shells.length;i++) {
            if(shells[i]!=null) {
                shells[i].drawMyself(g);
            }

            //碰撞检测。将所有的炮弹和飞机进行进行检测，看有没有碰撞
            boolean peng = shells[i].getRec().intersects(p1.getRec());
            if(peng) {
                System.out.println("飞机被击中了！！");
                p1.live = false;

                //处理爆炸效果
                if(explode==null) {
                    explode = new Explode(p1.x,p1.y);
                }
                explode.drawMySelf(g);
            }
        }
    }

    public void drawTime(Graphics g){
        Color c = g.getColor();
        Font  f = g.getFont();
        g.setColor(Color.green);
        if(p1.live) {
            period = (System.currentTimeMillis()-start.getTime())/1000;
            g.drawString("坚持："+period,30,50);
        }else{
            if(end==null) {
                end = new Date();
                period = (end.getTime()- start.getTime())/1000;
            }
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑",Font.BOLD,30));
            g.drawString("最终时间："+period,200,200);
        }
        g.setColor(c);
        g.setFont(f);
    }

    //初始化窗口
    public void launchFrame(){
        this.setTitle("飞机大战");
        setVisible(true);   //窗口是否可见
        setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);   //窗口大小
        setLocation(400,400);       //窗口打开的位置

        //增加关闭窗口的动作
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);     //正常退出程序
            }
        });

        new PaintThread().start();  //启动重画窗口的线程
        this.addKeyListener(new KeyMonitor());  //启动键盘监听

        //初始化创建50个炮弹对象
        for(int i=0;i<shells.length;i++) {
            shells[i] = new Shell();
        }
    }

    /**
     * 定义了一个重画窗口的线程类。
     * 定义成内部类是为了方便直接使用窗口类的相关方法
     */
    class PaintThread extends Thread {
        @Override
        public void run() {
            while(true) {
                repaint();      //内部类可以直接使用外部类的成员！
                try {
                    Thread.sleep(50);           //1s=1000ms。  1s画20次（20*50=1000）
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //内部类，实现键盘的监听处理
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
//            System.out.println("按下："+e.getKeyCode());
            /*if(e.getKeyCode()==KeyEvent.VK_LEFT){
                left = true;
            }
            if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                right = true;
            }*/
            p1.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            System.out.println("抬起："+e.getKeyCode());
            p1.minusDirection(e);
        }
    }
    private Image offScreenImage = null;

    public void update(Graphics g) {
        if (offScreenImage == null) {
          // 这是游戏窗口的宽度和高度
          offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        }
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }
    public static void main(String[] args) {
        MyGameFrame  gameFrame = new MyGameFrame();

        gameFrame.launchFrame();
    }
}
