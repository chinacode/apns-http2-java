package com.triumen.apns.http2.client.listener;

import com.alibaba.fastjson.JSONObject;
import com.triumen.apns.http2.client.service.RedisService;
import com.triumen.apns.http2.client.service.SendMsgService;
import com.triumen.apns.http2.core.manager.ApnsServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by by Martin Lee on 2019/2/20 17:45.
 */
@Service("newMsglistener")
public class NewMsglistener {
    static final Logger log = LoggerFactory.getLogger(NewMsglistener.class);
    @Resource
    RedisService redisService;

    public boolean state = true;
    @Async
    public void run(){
        log.info("启动redis消息监听服务");
        while (state) {
            long size = ApnsServiceManager.getPoolRunSize();
            if(size>=100){
                log.info("服务器繁忙！当前正在执行任务："+size );
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    continue;
                }
            }
            log.info("get redis list msg.....");
            JSONObject objMsg = redisService.getMesage();
            if (objMsg == null){
                continue;
            }
            SendMsgService.sendNotification(objMsg);
        }

    }
}
