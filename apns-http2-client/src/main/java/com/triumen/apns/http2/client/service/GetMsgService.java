package com.triumen.apns.http2.client.service;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;

/**
 * Created by by Martin Lee on 2019/2/18 9:37.
 */
public class GetMsgService implements Runnable{




    @Resource
    RedisService redisService;

    @Override
    public void run() {
        JSONObject objMsg = redisService.getMesage();
    }
}
