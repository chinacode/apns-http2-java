package com.triumen.apns.http2.core.service;

import com.triumen.apns.http2.core.model.PushNotification;

/**
 * Created by by Martin Lee on 2019/2/20 18:09.
 */
public interface FailureMsgList {
    public void push(PushNotification pushNotification);
}
