package com.x.demo.function;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Scanner;

/**
 * @ClassName ChinesePinyinTranslationTest
 * @Description 开发汉字拼音互译组件
 * @Author X
 *
 * @Date 2022/7/7 10:56
 * @Version 1.0
 **/
@Slf4j
public class ChinesePinyinTranslationTest {

  /**获取拼音的方法
   * @param str 汉字
   * @return 拼音
   */
  public static String getPinyin(String str) {
      //把字符串转换成字符数组
      char[] charStr = str.toCharArray();
      //创建一个字符串数组 用来存储汉字的每一种读音
      String[] sss;
      //创建汉语拼音的格式；字母的大小写 声调
      HanyuPinyinOutputFormat pof = new HanyuPinyinOutputFormat();
      //设置字母的大小写
      pof.setCaseType(HanyuPinyinCaseType.LOWERCASE);
      //设置字母的声调
      pof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
      //定义一个空字符串，用来接收汉字的第一种读音
      StringBuilder sb = new StringBuilder() ;
      //循环判断是不是汉字
      //普通for循环外部定义 只调用一次(推荐)，将获取数组长度放到循环中则会调用n次
      for (char c : charStr) {
          // 判断是汉字
          if (Character.toString(c).matches("[\\u4e00-\\u9fa5]")) {
              try {
                  //将汉字所有的读音和格式全部存储在字符串数组中
                  sss = PinyinHelper.toHanyuPinyinStringArray(c, pof);
                  //取出第一种读音放到字符串中，数组的索引从0开始，0代表第一个读音
                  sb.append(sss[0]);
              } catch (BadHanyuPinyinOutputFormatCombination e) {
                  e.printStackTrace();
              }
          } else {
              //不是汉字了
              sb.append(c);
          }
      }
      //StringBuilder转换为string
      return sb.toString();
  }

  public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      log.info("请输入你需要转换的汉字：");
      String str = sc.nextLine();
      log.info("转换中----");
      log.info("转换结果为：{}",getPinyin(str));
  }
}
