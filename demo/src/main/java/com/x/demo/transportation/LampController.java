package com.x.demo.transportation;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/**
 * @ClassName LampController
 * @Description TODO
 * @Author X
 * @Date 2022/8/12 10:58
 * @Version 1.0
 */
public class LampController {
  private Lamp currentLamp;

  public LampController() {
    // 刚开始让由南向北的灯变绿;
    currentLamp = Lamp.S2N;
    currentLamp.light();
    /*每隔10秒将当前绿灯变为红灯，并让下一个方向的灯变绿*/
    ScheduledExecutorService timer = newScheduledThreadPool(1);
    timer.scheduleAtFixedRate(
        new Runnable() {
          public void run() {
            System.out.println("来啊");
            currentLamp = currentLamp.blackOut();
          }
        },
        10,
        10,
        TimeUnit.SECONDS);
  }
}
