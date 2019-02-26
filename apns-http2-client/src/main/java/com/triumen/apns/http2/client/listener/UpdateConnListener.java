package com.triumen.apns.http2.client.listener;

import com.triumen.apns.http2.client.domain.AppCert;
import com.triumen.apns.http2.client.domain.AppInfo;
import com.triumen.apns.http2.client.service.ApnsConnService;
import com.triumen.apns.http2.core.manager.ApnsServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by by Martin Lee on 2019/2/20 18:37.
 */
@Service("updateConnListener")
public class UpdateConnListener {

    static final Logger log = LoggerFactory.getLogger(UpdateConnListener.class);
    @Resource
    ApnsConnService apnsConnService;
    @Async
    public void run(){

        log.info("启动证书更新服务");
        while (true) {
            List<AppInfo> appInfos = apnsConnService.selectAppInfoListAll();
            for (AppInfo appInfo : appInfos) {
                List<AppCert> appCerts = apnsConnService.selectAppCertUpdateListByAppId(appInfo.getId());
                for (AppCert appCert : appCerts) {
                    if(ApnsServiceManager.getService(appCert.getAppId()+"_"+appCert.getType())==null //判断连接是否为空
                            || apnsConnService.checkAppcertUpdate(appCert)//判断 appCert 是否已更新
                    ){
                        apnsConnService.createConnService(apnsConnService.selectAppCerById(appCert.getId())); //重新创建连接
                        log.info("已更新apns连接  {" + appCert.getId()+"_"+appCert.getType()+"|"+appCert.getName()+"}");
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
