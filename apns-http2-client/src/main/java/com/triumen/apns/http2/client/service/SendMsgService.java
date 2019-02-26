package com.triumen.apns.http2.client.service;

import com.alibaba.fastjson.JSONObject;
import com.triumen.apns.http2.client.config.RedisConfig;
import com.triumen.apns.http2.core.manager.ApnsServiceManager;
import com.triumen.apns.http2.core.model.Payload;
import com.triumen.apns.http2.core.model.PushNotification;
import com.triumen.apns.http2.core.service.ApnsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.String;
import javax.annotation.Resource;

/**
 * Created by by Martin Lee on 2019/2/13 17:54.
 */

@Service("sendMsgService")
public class SendMsgService {


    static final Logger log = LoggerFactory.getLogger(SendMsgService.class);

    public static void sendNotification(JSONObject objMsg) {
        String token = objMsg.getString("device_token");
        Payload payload = new Payload();
        payload.setAlertTitle(objMsg.getString("title"));
        payload.setAlertBody(objMsg.getString("content"));
        payload.setBadge(1);
        String vertype = objMsg.getString("vertype");
        String profiles = objMsg.getString("profiles");
        String connName = "";
        if("dev".equals(profiles)){
            connName = vertype+"_"+profiles;
        }else{
            connName = vertype + "_pro";
        }
        payload.setData(objMsg.getJSONObject("data"));
        if(StringUtils.isNoneBlank(objMsg.getString("sound"))){
            payload.setSound(objMsg.getString("sound"));
        }
        if(StringUtils.isNoneBlank(objMsg.getString("order_id"))){
            payload.setOrderId(objMsg.getString("order_id"));
        }
        if(StringUtils.isNoneBlank(objMsg.getString("noticetype"))){
            payload.setNoticetype(objMsg.getString("noticetype"));
        }
        if(StringUtils.isNoneBlank(objMsg.getString("createtime"))){
            payload.setCreatetime(objMsg.getInteger("createtime"));
        }
        payload.setOriginalMessages(objMsg);
        PushNotification notification = new PushNotification();
        notification.setPayload(payload);
        notification.setToken(token);
        log.info(payload.toString());
        ApnsService service = ApnsServiceManager.getService(connName);
        if(service!=null) {
            service.sendNotificationSynch(notification);// 异步发送
        }else{
            log.error("ERROR:{"+connName+"}"+"服务不存在！ ----> "+objMsg.toJSONString());
        }
    }

}
