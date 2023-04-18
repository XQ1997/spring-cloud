package com.x.demo.function;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName VideoUtil
 * @Description java开发抖音字符视频
 * @Author X
 * @Date 2022/7/11 9:20
 * @Version 1.0
 **/
@Slf4j
public class VideoUtil {

  public static void main(String[] args) {
      //路径可以反斜也可以正斜 反斜必须转义  正斜与反斜可以比喻一个武士正常是右手拿剑即为正斜，反之为反斜
      String pathSrc = "C:\\Users\\X\\Pictures/logo.jpg";
      String pathAim = "C:\\Users\\X\\Pictures/new.jpg";
      //将图片加载进内存
      BufferedImage bufferedSrc = null;
      BufferedImage bufferedAim = null;
      String ss = "天地玄黄宇宙洪荒日月盈昃辰宿列张寒来暑往秋收冬藏闰余";
      String[] strings = ss.split("");
      int bei = 10;
      Font font = new Font("微软雅黑",1,bei);
      try {
          //异常 运行时编译时
          bufferedSrc = ImageIO.read(new File(pathSrc));
          //获取图片的宽高
          int width = bufferedSrc.getWidth();
          int height = bufferedSrc.getHeight();
          bufferedAim = new BufferedImage(width*bei,height*bei,1);
          Graphics2D gg = bufferedAim.createGraphics();
          gg.setBackground(Color.WHITE);
          gg.fillRect(0,0,width*bei,height*bei);
          gg.setColor(Color.BLACK);
          gg.setFont(font);
          int color,r,g,b;
          for(int i = 0;i < width;i++){
               for(int j = 0;j < height;j++){
                   color = bufferedSrc.getRGB(i,j);
                   r = (color & 0xff0000) >> 16;
                   g = (color & 0xff00) >> 8;
                   b = color & 0xff;
                   color = (int) (r * 0.3 + g * 0.59 + b * 0.11);
                   gg.setColor(new Color(r,g,b));
                   gg.drawString(strings[color / 10],i*bei,j*bei);
               }
          }
          ImageIO.write(bufferedAim,"jpg",new File(pathAim));
          log.info("成功生成");
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}
