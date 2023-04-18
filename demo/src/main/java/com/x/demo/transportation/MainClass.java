package com.x.demo.transportation;

/**
 * @ClassName MainClass
 * @Description 1、用for循环创建出代表12条路线的对象。
 * 2、接着再获得LampController对象并调用其start方法。
 * @Author X
 * @Date 2022/8/12 11:00
 * @Version 1.0
 */
public  class  MainClass  {
    /**
     *  @param  args
     */
    public  static  void  main(String[]  args)   {
        /*产生12个方向的路线*/
        String  []  directions  =  new  String[]{
                "S2N","S2W","E2W","E2S","N2S","N2E","W2E","W2N","S2E","E2N","N2W",
                "W2S"
        };
        for(int  i=0;i<directions.length;i++){
            new  Road(directions[i]);
        }
        /*产生整个交通灯系统*/
        new  LampController();
    }
}