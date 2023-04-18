package com.x.demo.plane;

import java.awt.*;
/**
 * @ClassName GameObject
 * @Description 游戏物体的根类
 * @Author X
 * @Date 2022/8/1 11:48
 * @Version 1.0
 **/
public class GameObject {
     /** 图片*/
    Image img;

    /** 物体的坐标*/
    double x, y;

    /**物体移动的速度 */
    int speed;
    /** 物体的宽度和高度*/
    int width,height;

    public GameObject(Image img, double x, double y, int speed, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public GameObject(Image img, double x, double y, int speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }
    public GameObject(){}
    public void drawMyself(Graphics g){
        g.drawImage(img,(int)x,(int)y,width,height,null);
    }

  /** 所有的物体都是矩形。当你获得对应的矩形的时候，我们就可以做一些相关的判断的操作!
   * @return
   */
  public Rectangle getRec() {
        return new Rectangle((int)x,(int)y,width,height);
    }
}
