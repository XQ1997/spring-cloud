package com.x.demo.bank;

/**
 * @ClassName NumberMachine
 * @Description 定义三个成员变量分别指向三个NumberManager对象，分别表示普通、快速和VIP客户的
 * 号码管理器，定义三个对应的方法来返回这三个NumberManager对象。 将NumberMachine类设计成单例。
 * @Author X
 * @Date 2022/8/12 11:19
 * @Version 1.0
 */
public  class  NumberMachine  {
    private  NumberMachine(){}
    private  static  NumberMachine  instance  = new  NumberMachine();
    public  static  NumberMachine  getInstance(){
        return  instance;
    }
    private  NumberManager  commonManager  =  new   NumberManager();
    private  NumberManager  expressManager  =   new  NumberManager();
    private  NumberManager  vipManager  =   new  NumberManager();
    public  NumberManager  getCommonManager()   {
        return  commonManager;
    }
    public  NumberManager  getExpressManager()   {
        return  expressManager;
    }
    public  NumberManager  getVipManager()  {
        return  vipManager;
    }
}
