package com.x.demo.function;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @ClassName QrcodeTest
 * @Description 微信二维码扫一扫功能
 * @Author X
 * @Date 2022/7/9 10:56
 * @Version 1.0
 **/
public class QrcodeTest {

  public static void main(String[] args) {
      qrcodeTest();
  }

  /**生成二维码 */
  public static void qrcodeTest() {
      //1.定义二维码得宽和高
      int width = 300;
      int height = 300;
      //2.定义二维码的内容 图片路径只能是带http的
      //String content = "癞蛤蟆想吃天鹅肉";
      String content = "http://ankaisoft.com/book/mobile/index.html#p=1";
      String contents = null;
      try {
          contents = new String(content.getBytes("UTF-8"),"ISO-8859-1");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      //3.二维码和二维码图片 二维码在内存中，是虚拟的，看不到 图片是真实存在本地的，我们能看到
      String format = "jpg";
      //4.容错率logo图片所占二维码面积的大小 容错率越高，logo图的面积就越大
      HashMap<EncodeHintType, Comparable> map = new HashMap<>();
      //H30% L7% M15% Q25%
      map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
      try {
          //5.画二维码只是在我们的内存当中所以我们看不到BarcodeFormate二维码的格式类型 qrcode
          BitMatrix encode = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE,width,height);
          //6.创建一个本地图片文件
          File file = new File("C:/Users/X/Pictures/weixin.jpg");
          //7.把内存中的二维码写入本地文件当中
          MatrixToImageWriter.writeToFile(encode,format,file);
      } catch (WriterException | IOException e) {
          e.printStackTrace();
      }
  }
}
