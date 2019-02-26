package com.triumen.apns.http2.client;

/**
 * Created by by Martin Lee on 2019/2/26 15:21.
 */

import com.triumen.apns.http2.core.error.ErrorDispatcher;
import com.triumen.apns.http2.core.error.ErrorListener;
import com.triumen.apns.http2.core.error.ErrorModel;
import com.triumen.apns.http2.core.manager.ApnsServiceManager;
import com.triumen.apns.http2.core.model.ApnsConfig;
import com.triumen.apns.http2.core.model.Payload;
import com.triumen.apns.http2.core.model.PushNotification;
import com.triumen.apns.http2.core.service.ApnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ApnsClientTest {

    private static final Logger log = LoggerFactory.getLogger(ApnsClientTest.class);

    private static final String TOKEN = "1b6ee0e1a4b49287bfaecd6f45777478625bd6a0c166f859a4c8ff0faf9135e3";

    public static void main(String[] args) {
        // 读取证书
     //   InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("zhengshu.p12");
        InputStream is = null;
        try {
            is = new FileInputStream("E:/apns/shilian/pro.p12");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ApnsConfig config = new ApnsConfig();
        config.setName("name1");// 推送服务名称
        config.setDevEnv(false);// 是否是开发环境
        config.setKeyStore(is);// 证书
        config.setPassword("Chaomeng@1012");// 证书密码
        config.setPoolSize(1);// 线程池大小
        config.setTimeout(3000);// TCP连接超时时间
        config.setTopic("com.chaomeng.cmfoodchain");// 标题,即证书的bundleID
        ApnsServiceManager.createService(config);


        ErrorDispatcher.getInstance().addListener(new ErrorListener() {
            @Override
            public void handle(ErrorModel errorModel) {
                System.out.println(errorModel);
                log.info("收到错误监听:" + errorModel);

            }
        });

        sendNotification(config.getName(), TOKEN);

    }

    public static void sendNotification(String appName, String token) {

        Payload payload = new Payload();
        payload.setAlert("test");

        PushNotification notification = new PushNotification();
        notification.setPayload(payload);
        notification.setToken(token);

        ApnsService service = ApnsServiceManager.getService(appName);
        //service.sendNotification(notification);// 异步发送
        boolean result = service.sendNotificationSynch(notification);// 同步发送
        System.out.println(result);
    }

}