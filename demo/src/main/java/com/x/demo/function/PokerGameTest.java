package com.x.demo.function;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @ClassName PokerGameTest
 * @Description Java开发腾讯游戏-欢乐斗地主
 * @Author X
 * @Date 2022/7/8 8:52
 * @Version 1.0
 **/
@Slf4j
public class PokerGameTest {

  public static void main(String[] args) {
      //创建容器：数组、集合。索引，花色 int
      HashMap<Integer, String> hashMap = new HashMap<>();
      //创建一个排数的容器 54
      ArrayList<Integer> indexList = new ArrayList<>();
      //创建牌的花色
      String[] colors = {"♥","♠","♣","♦"};
      //创建牌的数字
      String[] numbers = {"3","4","5","6","7","8","9","10","J","Q","K","A","2"};
      //牌数从0开始
      int index = 0;
      //在集合中循环添加 添加数字
      for(String number : numbers){
          //添加花色
          for(String color : colors){
              hashMap.put(index,(color+number));
              indexList.add(index);
              index++;
          }
      }
      //大小王
      indexList.add(index);
      hashMap.put(index,"大王");
      index++;
      indexList.add(index);
      hashMap.put(index,"小王");
      //洗牌  洗牌 可以洗牌的数字
      Collections.shuffle(indexList);
      //发牌   TreeSet是有序不可重复
      //第一个人的牌
      TreeSet<Integer> one = new TreeSet<>();
      //第二个人的牌
      TreeSet<Integer> two = new TreeSet<>();
      //第三个人的牌
      TreeSet<Integer> three = new TreeSet<>();
      //底牌
      TreeSet<Integer> last = new TreeSet<>();
      //循环的发牌
      for(int i = 0; i < indexList.size();i++){
          //三张底牌已经拿出来了 取模
          if(i >= indexList.size() -3){
              last.add(indexList.get(i));
          }else if(i % 3 == 0){
              one.add(indexList.get(i));
          }else if(i % 3 == 1){
              two.add(indexList.get(i));
          }else {
              three.add(indexList.get(i));
          }
      }
      //看牌
      lookPoker("玩家1",one,hashMap);
      lookPoker("玩家2",two,hashMap);
      lookPoker("玩家3",three,hashMap);
      lookPoker("底牌",last,hashMap);

      //抢地主 属于玩家的牌和底牌放一块

  }
  /** 看牌的方法
   * @param name 姓名
   * @param treeSet 牌
   * @param hashMap 所有的牌
   */
  private static void lookPoker(String name, TreeSet<Integer> treeSet, HashMap<Integer, String> hashMap) {
      System.out.println(name + "的牌为：");
      for(Integer index : treeSet){
          System.out.print(hashMap.get(index));
      }
  }
}
