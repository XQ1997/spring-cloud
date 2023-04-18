package com.x.demo.function;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName CutScreenTest
 * @Description 实现截屏功能
 * @Author X
 * @Date 2022/7/9 9:54
 * @Version 1.0
 **/
@Slf4j
public class CutScreenTest {

  public static void main(String[] args) {
//    cutScreenTest1("C:/Users/X/Pictures","1.jpg");
//    cutScreenTest2("C:/Users/X/Pictures","2.jpg");
    cutScreenTest3("C:/Users/X/Pictures","3.jpg");
  }

  /** 实现截图功能 全屏
   * @param folder 保存路径
   * @param fileName 保存文件的名称
   */
  public static void cutScreenTest1(String folder, String fileName) {
    //1.获取屏幕大小
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    log.info("屏幕大小为：{}",screenSize);
    //2.创建截屏区域
    Rectangle rectangle = new Rectangle(screenSize);
    try {
      //3.创建包含从所选区域中读取的像素的图像
      Robot robot = new Robot();
      //image就是我读取的像素图片，我们看不到，因为它在我们的内存当中  全屏
      BufferedImage image = robot.createScreenCapture(rectangle);
      //4.设置保存路径
      File screenFile = new File(folder);
      if(!screenFile.exists()){
        //如果不存在，就创建多级目录
        screenFile.mkdirs();
      }
      //5.image在内存中，所以我们是不是得在本地创建一个图片，然后通过流把image写入到本地文件中
      File f = new File(screenFile,fileName);
      //6.通过流将image写入本地文件
      ImageIO.write(image,"png",f);
      //自动打开截图
      if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)){
        Desktop.getDesktop().open(f);
      }
    } catch (AWTException | IOException e) {
      e.printStackTrace();
    }
  }

  /** 实现截图功能 截屏大小
   * @param folder 保存路径
   * @param fileName 保存文件的名称
   */
  public static void cutScreenTest2(String folder, String fileName) {
    try {
      //3.创建包含从所选区域中读取的像素的图像
      Robot robot = new Robot();
      //image就是我读取的像素图片，我们看不到，因为它在我们的内存当中  全屏
      BufferedImage image = robot.createScreenCapture(new Rectangle(400,400));
      //4.设置保存路径
      File screenFile = new File(folder);
      if(!screenFile.exists()){
        //如果不存在，就创建多级目录
        screenFile.mkdirs();
      }
      //5.image在内存中，所以我们是不是得在本地创建一个图片，然后通过流把image写入到本地文件中
      File f = new File(screenFile,fileName);
      //6.通过流将image写入本地文件
      ImageIO.write(image,"png",f);
      //自动打开截图
      if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)){
        Desktop.getDesktop().open(f);
      }
    } catch (AWTException | IOException e) {
      e.printStackTrace();
    }
  }

  /** 实现截图功能 选中区域
   * @param folder 保存路径
   * @param fileName 保存文件的名称
   */
  public static void cutScreenTest3(String folder, String fileName) {
    try {
      //3.创建包含从所选区域中读取的像素的图像
      Robot robot = new Robot();
      //image就是我读取的像素图片，我们看不到，因为它在我们的内存当中  全屏
      BufferedImage image = robot.createScreenCapture(new Rectangle(1000,0,400,400));
      //4.设置保存路径
      File screenFile = new File(folder);
      if(!screenFile.exists()){
        //如果不存在，就创建多级目录
        screenFile.mkdirs();
      }
      //5.image在内存中，所以我们是不是得在本地创建一个图片，然后通过流把image写入到本地文件中
      File f = new File(screenFile,fileName);
      //6.通过流将image写入本地文件
      ImageIO.write(image,"png",f);
      //自动打开截图
      if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)){
        Desktop.getDesktop().open(f);
      }
    } catch (AWTException | IOException e) {
      e.printStackTrace();
    }
  }
}
