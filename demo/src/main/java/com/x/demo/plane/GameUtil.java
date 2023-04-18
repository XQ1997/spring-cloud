package com.x.demo.plane;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @ClassName GameUtil
 * @Description 加载图片代码工具类
 * @Author X
 * @Date 2022/8/1 11:44
 * @Version 1.0
 **/
@Slf4j
public class GameUtil {

    /**构造器私有。防止别人创建本类的对象. */
    private GameUtil() {}

    public static Image getImage(String path) {     //images/plane.png
        BufferedImage img = null;
        URL u = GameUtil.class.getClassLoader().getResource(path);
        try {
            img = ImageIO.read(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static void main(String[] args) {
        Image img = GameUtil.getImage("images/plane.png");
        log.info(String.valueOf(img));
    }
}
