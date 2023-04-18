package com.x.demo.test;

/**
 * @ClassName StringDemo
 * @Description TODO
 * @Author X
 * @Date 2022/8/6 14:46
 * @Version 1.0
 **/
public class StringDemo {
    public static  boolean isNum(String s){
        char[] chars = s.toCharArray();
        for(char c : chars){
            if(!(c > '0' && c <= '9')){
                return false;
            }
        }
        return true;
    }
}
