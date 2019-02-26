package com.triumen.apns.http2.client.service;

import com.triumen.apns.http2.core.model.PushNotification;
import com.triumen.apns.http2.core.service.FailureMsgList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by by Martin Lee on 2019/2/20 18:27.
 */
@Service("redisFailureMsgList")
public class RedisFailureMsgList implements FailureMsgList {

    @Autowired
    RedisService redisService;

    @Override
    public void push(PushNotification pushNotification) {
        redisService.putFailureMesage(pushNotification.getPayload().getOriginalMessages());
    }
}
