package com.x.demo.bank;

import  java.util.concurrent.TimeUnit;
import  java.util.logging.Logger;

import static java.util.concurrent.Executors.*;

/**
 * @ClassName MainClass
 * @Description 用for循环创建出4个普通窗口，再创建出1个快速窗口和一个VIP窗口。
 *接着再创建三个定时器，分别定时去创建新的普通客户号码、新的快速客户号码、新的VIP客户号码。
 * @Author X
 * @Date 2022/8/12 11:30
 * @Version 1.0
 */
public  class  MainClass  {
    private static Logger logger = Logger.getLogger("cn.itcast.bankqueue");
    public  static  void  main(String[]  args)   {
        //产生4个普通窗口
        for(int  i=1;i<5;i++){
            ServiceWindow window = new  ServiceWindow();
            window.setNumber(i);
            window.start();
        }
        //产生1个快速窗口
        ServiceWindow  expressWindow = new  ServiceWindow();
        expressWindow.setType(CustomerType.EXPRESS);
        expressWindow.start();
        //产生1个VIP窗口
        ServiceWindow  vipWindow = new  ServiceWindow();
        vipWindow.setType(CustomerType.VIP);
        vipWindow.start();
        //普通客户拿号
        newScheduledThreadPool(1).scheduleAtFixedRate(
                new  Runnable(){
                    public  void  run(){
                        Integer serviceNumber = NumberMachine.getInstance().getCommonManager().generateNewNumber();
                        System.out.println("第"  +  serviceNumber  +  "号普通客户正在等待服务！");
                    }
                },
                0,
                Constants.COMMON_CUSTOMER_INTERVAL_TIME,
                TimeUnit.SECONDS
        );
        //快速客户拿号
        newScheduledThreadPool(1).scheduleAtFixedRate(
                new  Runnable(){
                    public  void  run(){
                        Integer serviceNumber = NumberMachine.getInstance().getExpressManager().generateNewNumber();
                        System.out.println("第"  +  serviceNumber  +  "号快速客户正在等待服务！");
                    }
                },
                0,
                Constants.COMMON_CUSTOMER_INTERVAL_TIME  *  2,
                TimeUnit.SECONDS
        );
        //VIP客户拿号
        newScheduledThreadPool(1).scheduleAtFixedRate(
                new  Runnable(){
                    public  void  run(){
                        Integer serviceNumber = NumberMachine.getInstance().getVipManager().generateNewNumber();
                        System.out.println("第" +  serviceNumber +  "号VIP客户正在等待服务！");
                    }
                },
                0,
                Constants.COMMON_CUSTOMER_INTERVAL_TIME  *  6,
                TimeUnit.SECONDS
        );
    }
}
