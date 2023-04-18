package com.x.demo.bank;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import  java.util.Random;
import static java.util.concurrent.Executors.*;

/**
 * @ClassName ServiceWindow
 * @Description 定义一个start方法，内部启动一个线程，根据服务窗口的类别分别循环调用三个不同的 方法。
 * 定义三个方法分别对三种客户进行服务，为了观察运行效果，应详细打印出其中的细节信息。
 * 没有把VIP窗口和快速窗口做成子类，是因为实际业务中的普通窗口可以随时被设置为VIP窗口和快速窗口。
 * @Author X
 * @Date 2022/8/12 11:22
 * @Version 1.0
 */
@Slf4j
public  class  ServiceWindow  {
    private  CustomerType  type  =  CustomerType.COMMON;
    private  int  number  =  1;
    public  CustomerType  getType()  {
        return  type;
    }
    public  void  setType(CustomerType   type)  {
        this.type  =  type;
    }
    public  void  setNumber(int  number){
        this.number  =  number;
    }
    public  void  start(){
        newSingleThreadExecutor().execute(
                new  Runnable(){
                    @SneakyThrows
                    public  void  run(){
                        if (type.equals(CustomerType.COMMON)) {
                            commonService();
                        }else if (type.equals(CustomerType.EXPRESS)) {
                            expressService();
                        }else if (type.equals(CustomerType.VIP)) {
                            vipService();
                        }else{
                            log.info("暂无客户");
                        }
                    }
                }
        );
    }
    private  void  commonService() throws InterruptedException {
        String  windowName  =  "第"  +  number   +  "号"  +  type  +  "窗口";
        log.info("{}开始获取普通任务",windowName);
        Integer serviceNumber = NumberMachine.getInstance().getCommonManager().fetchNumber();
        if(serviceNumber  !=  null){
            log.info("普通手牌{}",serviceNumber);
            log.debug("{}开始为第{}号普通客户服务",windowName,serviceNumber);
            int maxRandom = Constants.MAX_SERVICE_TIME - Constants.MIN_SERVICE_TIME;
            int serviceTime = new Random().nextInt(maxRandom)+1+ Constants.MIN_SERVICE_TIME;
            Thread.sleep(serviceTime);
            log.info("{}完成为第{}号普通客户服务，总共耗时{}秒",windowName,serviceNumber,(serviceTime/1000));
        }else{
            log.info("{}没有取到普通任务，正在空闲",windowName);
            Thread.sleep(1000);
        }
    }
    private  void  expressService() throws InterruptedException {
        String  windowName  =  "第"  +  number   +  "号"  +  type  +  "窗口";
        log.info("{}开始获取快速任务",windowName);
        Integer serviceNumber = NumberMachine.getInstance().getExpressManager().fetchNumber();
        if(serviceNumber  !=null){
            log.info("快速手牌{}",serviceNumber);
            log.debug("{}开始为第{}号快速客户服务",windowName,serviceNumber);
            int  serviceTime  =  Constants.MIN_SERVICE_TIME;
            Thread.sleep(serviceTime);
            log.info("{}完成为第{}号快速客户服务，总共耗时{}秒",windowName,serviceNumber,(serviceTime/1000));
        }else{
            log.info("{}没有取到快速任务",windowName);
            commonService();
        }
    }
    private  void  vipService() throws InterruptedException {
        String  windowName  =  "第"  +  number   +  "号"  +  type  +  "窗口";
        log.info("{}开始获取VIP任务",windowName);
        Integer serviceNumber = NumberMachine.getInstance().getVipManager().fetchNumber();
        if(serviceNumber  !=null){
            log.info("VIP手牌{}",serviceNumber);
            log.debug("{}开始为第{}号VIP客户服务",windowName,serviceNumber);
            int maxRandom = Constants.MAX_SERVICE_TIME - Constants.MIN_SERVICE_TIME;
            int serviceTime = new Random().nextInt(maxRandom)+1 + Constants.MIN_SERVICE_TIME;
            Thread.sleep(serviceTime);
            log.info("{}完成为第{}号VIP客户服务，总共耗时{}秒",windowName,serviceNumber,(serviceTime/1000));
        }else{
            log.info("{}没有取到VIP任务",windowName);
            commonService();
        }
    }
}