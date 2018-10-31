package com.gz.common;

import com.gz.common.model.Order;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gongzhen on 2018/7/17.
 */
public class TimedTask {
    private static TimedTask service;
    private  TimedTask() {
    }

    public static TimedTask getService() {
        if (service == null) {
            service = new TimedTask();
        }
        return service;
    }
    public  void orderStatus(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
              Page<Order> orderPage= OrderService.getService().getOrder(1,100,1,null,null,null,false);
                for(Order order:orderPage.getList()){
                    if(new Date().getTime()-order.getCreateTime().getTime()>1800){
                        order.setOrderStatus(7);
                        order.update();
                    }
                }
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 10 * 1000;
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }
}
