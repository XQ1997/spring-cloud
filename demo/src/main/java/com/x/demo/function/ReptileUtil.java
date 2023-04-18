package com.x.demo.function;

import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ReptileUtil
 * @Description Java开发网络爬虫
 * @Author X
 * @Date 2022/7/11 11:45
 * @Version 1.0
 **/
@Slf4j
public class ReptileUtil {
  /**需要爬取的网站链接*/
  private static String url = "http://588ku.com/image/1284323.html";
  private static String noNext = "javascript:void(0)";

  public static void main(String[] args) {
     String newUrl = null;
    while (true){
        //1.获取源代码 从原网站开一个口将流引入到新地址中
        String code = getCodeByUrl(url);

       // 2.从源代码中解析，获取图片的链接的集合
        List<String> list = getImgListFromCode(code);
       // 3.循环图片地址 保存图片
        saveImg(list);
        newUrl = getNextUrlFromCode(code);
        if(newUrl.contains(noNext)){
            break;
        }
        //将网址更新
        url = newUrl;
    }
  }

  /**
   * 将所有的图片保存在本地
   * @param list 图片链接地址集合
   */
  public static void saveImg(List<String> list) {
      String newUrl = null;
      URL webUrl = null;
      //定义链接
      HttpURLConnection connection = null;
      //定义输入流
      InputStream inputStream = null;
      byte[] bs = null;
      //定义文件输出流
      FileOutputStream outputStream = null;
      try {
          for(String url : list){
              //获取每一次的地址
              newUrl = url;
              //获取链接的地址
              webUrl = new URL(newUrl);
              connection = (HttpURLConnection) webUrl.openConnection();
              //获取输入流
              inputStream = connection.getInputStream();
              //从输入流里面获取相应的数组
              bs = ByteStreams.toByteArray(inputStream);
        outputStream =
            new FileOutputStream(
                new File("C:/Users/X/Pictures/img/" + System.currentTimeMillis() + ".png"));
              outputStream.write(bs);
          }
      } catch (IOException e) {
          e.printStackTrace();
      }finally{
          if(outputStream != null){
              try{
                  outputStream.close();
              }catch (IOException e){
                  e.printStackTrace();
              }
          }
      }
  }

    /**从源代码中获取下一页的地址
     * @param code 源代码
     * @return 網址
     */
    public static String getNextUrlFromCode(String code) {
        //获取文档对象
        Document document = Jsoup.parse(code);
        //获取相应的节点
        Elements elements = document.getElementsByClass("fl clearfix page-list");
        //https://588ku.com/sucai/0-default-0-0-1284323-0-0-2/
        String res = "http:" + elements.get(0).child(1).child(0).attr("href");
        return  res;
    }

  /**
   * 从源代码中国获取图片集合
   * @param code 源代码
   * @return 图片集合
   */
  public static List<String> getImgListFromCode(String code) {
      ArrayList<String> list = new ArrayList<>();
      //3.获取文档对象
      Document document = Jsoup.parse(code);
    // 4.获取相应的节点
    Elements elements =
        document
            .select("div [class=clearfix data-list dataList V-marony Vmarony]")
            .select("img[class=lazy]");
      elements.forEach(element -> list.add("https:" + element.attr("data-original")));
      return list;
  }

  /** 通过网络地址获取相应的源代码
   * @param url 网络地址
   * @return 源代码
   */
  public static String getCodeByUrl(String url) {
      String code = null;
      try {
          code = Jsoup.connect(url).execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return code;
  }
 }
