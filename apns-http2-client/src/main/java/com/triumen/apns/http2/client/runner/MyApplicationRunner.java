package com.triumen.apns.http2.client.runner;

import com.triumen.apns.http2.client.listener.FailureMasListener;
import com.triumen.apns.http2.client.listener.NewMsglistener;
import com.triumen.apns.http2.client.listener.UpdateConnListener;
import com.triumen.apns.http2.client.service.ApnsConnService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by by Martin Lee on 2019/2/14 10:55.
 */
@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
@EnableAsync
public class MyApplicationRunner implements ApplicationRunner {
    @Resource
    ApnsConnService apnsConnService;
    @Resource
    FailureMasListener failureMasListener;
    @Resource
    NewMsglistener newMsglistener;
    @Resource
    UpdateConnListener updateConnListener;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("-------------->" + "项目启动，now=" + new Date());

        try {
//            apnsConnService.createConn();
//            newMsglistener.run();
//            failureMasListener.run();
//            updateConnListener.run();
        }catch (Exception e){

        }
    }


}