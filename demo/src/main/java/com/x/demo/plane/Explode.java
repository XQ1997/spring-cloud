package com.x.demo.plane;

import java.awt.*;
/**
 * @ClassName Explode
 * @Description 爆炸类
 * @Author X
 * @Date 2022/8/1 11:47
 * @Version 1.0
 **/
public class Explode {
    //位置
    double x,y;
    static Image[] imgs = new Image[1];
    int count;
    static {
        for(int i=0;i<1;i++) {
            //imgs[i] = GameUtil.getImage("images/explode/e" + (i + 1) + ".gif");//加载爆炸效果动图
            imgs[i] = GameUtil.getImage("images/explode/e" + (i + 1) + ".gif");//加载爆炸效果动图
//            imgs[i].getWidth(null);     //解决懒加载问题。目前没有问题，加和不加都行。
        }
    }

    public void drawMySelf(Graphics g) {
        if(count<1) {
            g.drawImage(imgs[count],(int)x,(int)y,null);
            count++;
        }
    }
    public Explode(){}

    public Explode(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
