package com.x.demo.plane;

import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * @ClassName Plane
 * @Description 飞机类
 * @Author X
 * @Date 2022/8/1 11:54
 * @Version 1.0
 **/
public class Plane extends GameObject{
    /** 飞机的方向控制*/
    boolean left,right,up,down;
    /** 活着 */
    boolean live = true;

    @Override
    public void drawMyself(Graphics g) {
        if(live){
            super.drawMyself(g);
            //飞机飞行的算法，可以自行设定
            /*x += speed;*/
            if(left) {
                x -= speed;
            }
            if(right){
                x += speed;
            }
            if(up){
                y -= speed;
            }
            if(down) {
                y += speed;
            }
        }
    }

    public void addDirection(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            default:
        }
    }

    public void minusDirection(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            default:
        }
    }

    public Plane(Image img, double x, double y, int speed) {
        super(img, x, y, speed);
    }
}
