package com.x.demo.plane;

import java.awt.*;

/**
 * @ClassName Shell
 * @Description 炮弹类
 * @Author X
 * @Date 2022/8/1 11:55
 * @Version 1.0
 **/
public class Shell  extends GameObject {
    /**角度。炮弹沿着指定的角度飞行 */
    double degree;

    public  Shell(){
        x = 200;
        y = 200;
        degree = Math.random()*Math.PI*2;
        width = 5;
        height = 5;
        speed = 1;
    }

    @Override
    public void drawMyself(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.fillOval((int)x,(int)y,width,height);
        g.setColor(c);
        //根据自己算法指定移动的路径
        x += speed*Math.cos(degree);
        y += speed*Math.sin(degree);
        //碰到边界改变方向
        if(y>Constant.GAME_HEIGHT-this.height||y<40) {
            degree = -degree;
        }

        if(x<0||x>Constant.GAME_WIDTH-this.width) {
            degree = Math.PI-degree;
        }
    }
}