package com.x.demo.function;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName WaterMarkTest
 * @Description java开发微博自动添加水印系统
 * @Author X
 * @Date 2022/7/8 11:51
 * @Version 1.0
 **/
@Slf4j
public class WaterMarkTest {

  /**添加水印的主方法
   * @param srcImgPath      原图片路径
   * @param tarImgPath      新图片路径
   * @param waterMarkContent 水印的内容
   * @param color 水印的颜色
   * @param font  水印的字体
   */
  public void addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent, Color color, Font font) {
      FileOutputStream fileOutputStream = null;
      try {
          //1.通过原图片的路径得到文件
          File srcImgFile = new File(srcImgPath);
          //2.把这个文件转换成为图片
          Image srcImg = ImageIO.read(srcImgFile);
          //3.获取图片的宽高
          int srcImgWidth = srcImg.getWidth(null);
          int secImgHeight = srcImg.getHeight(null);
          //4.创建一个画板，用来画出水印   r代表red红色，g代表green绿色 b代表blue蓝色
          BufferedImage buffImg = new BufferedImage(srcImgWidth,secImgHeight,BufferedImage.TYPE_INT_RGB);
          //5。创建一个2D的图像
          Graphics2D g = buffImg.createGraphics();
          //6.画出来这个图像
          g.drawImage(srcImg,0,0,srcImgWidth,secImgHeight,null);
          //7.设置水印内容的颜色
          g.setColor(color);
          //8.设置水印内容字体
          g.setFont(font);
          //9.获取水印的坐标
          int x = srcImgWidth*19/20 - getWatermarkLength(waterMarkContent,g);
          int y = secImgHeight*9/10;
          //10.画出水印
          g.drawString(waterMarkContent,x,y);
          //11.释放内存
          g.dispose();
          //12.输出新的已经添加水印的图片
          fileOutputStream = new FileOutputStream(tarImgPath);
          //13.新的图片
          ImageIO.write(buffImg,"jpg",fileOutputStream);
          log.info("水印添加完成");
      } catch (IOException e) {
          e.printStackTrace();
      }finally{
          try {
              //14.关流
              fileOutputStream.flush();
              fileOutputStream.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }

  /** 创建一个获取水印坐标  横向的水印
   * @param waterMarkContent 水印内容
   * @param g 2D图形
   * @return 坐标
   */
  public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(),0,waterMarkContent.length());
  }

  public static void main(String[] args) {
      //设置字体
      Font font = new Font("微软雅黑",Font.PLAIN,35);
    // 设置原图片
    String srcImgPath = "C:/Users/X/Pictures/ceshi.png";
      //设置新的图片
      String tarImgPath = "C:/Users/X/Pictures/ceshiTest.png";
      //设置内容
      String waterMarkContent = "图片来源：http://www.baidu.com";
      //设置颜色
      Color color = new Color(0,0,255,128);
      new WaterMarkTest().addWaterMark(srcImgPath,tarImgPath,waterMarkContent,color,font);
  }
}
