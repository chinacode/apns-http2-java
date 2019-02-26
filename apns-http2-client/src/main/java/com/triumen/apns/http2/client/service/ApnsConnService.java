package com.triumen.apns.http2.client.service;

import com.triumen.apns.http2.client.dao.AppCertDao;
import com.triumen.apns.http2.client.dao.AppInfoDao;
import com.triumen.apns.http2.client.domain.AppCert;
import com.triumen.apns.http2.client.domain.AppInfo;
import com.triumen.apns.http2.client.utils.FileToBase64;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.triumen.apns.http2.core.error.ErrorDispatcher;
import com.triumen.apns.http2.core.error.ErrorListener;
import com.triumen.apns.http2.core.error.ErrorModel;
import com.triumen.apns.http2.core.manager.ApnsServiceManager;
import com.triumen.apns.http2.core.model.ApnsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by by Martin Lee on 2019/2/13 18:02.
 */
@Scope("prototype")
@Service("apnsConnService")
public class ApnsConnService {

    @Resource
    AppCertDao appCertDao;
    @Resource
    AppInfoDao appInfoDao;

    @Autowired
    RedisFailureMsgList failureMsgList;

    private static final Logger log = LoggerFactory.getLogger(ApnsConnService.class);

    private static Map<Integer, AppCert> appCertMap = new HashMap<Integer, AppCert>();

    public List<AppInfo> selectAppInfoListAll() {
        EntityWrapper appInfosEw = new EntityWrapper();
        appInfosEw.where("1=1");
        return appInfoDao.selectList(appInfosEw);
    }

    public List<AppCert> selectAppCertListByAppId(Integer appId) {
        EntityWrapper ew = new EntityWrapper();
        ew.where("app_id={0}", appId);
        return appCertDao.selectList(ew);
    }

    public List<AppCert> selectAppCertUpdateListByAppId(Integer appId) {
        return appCertDao.selectAppCertUpdateListByAppId(appId);
    }

    public AppCert selectAppCerById(Integer id) {
        return appCertDao.selectById(id);
    }


    public void createConn() throws Exception {
        List<AppInfo> appInfos = selectAppInfoListAll();
        if (appInfos == null || appInfos.size() < 1) {
            throw new Exception("not found appInfos");
        }
        for (AppInfo appInfo : appInfos) {

            List<AppCert> appCerts = selectAppCertListByAppId(appInfo.getId());
            for (AppCert appCert : appCerts) {
                createConnService(appCert);
            }
        }
        System.out.println("初始化连接池完成！");
        ErrorDispatcher.getInstance().addListener(new ErrorListener() {
            @Override
            public void handle(ErrorModel errorModel) {
                log.info("收到错误监听:" + errorModel);
            }
        });
        //updateConn();

    }



    public void createConnService(AppCert appCert){
        try {
            InputStream is = FileToBase64.getInputStreamFromBase64(appCert.getCert());
            ApnsConfig config = new ApnsConfig();
            config.setName(appCert.getAppId()+"_"+appCert.getType());// 推送服务名称
            config.setDevEnv("dev".equals(appCert.getType()));// 是否是开发环境
            config.setKeyStore(is);// 证书
            config.setPassword(appCert.getPassword());// 证书密码
            config.setPoolSize(100);// 线程池大小
            config.setTimeout(3000);// TCP连接超时时间
            config.setTopic(appCert.getTopic());// 标题,即证书的bundleID
            config.setFailureMsgList(failureMsgList);
            ApnsServiceManager.createService(config);
            appCertMap.put(appCert.getId(),appCert);
        }catch (IllegalStateException e){
            log.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  boolean checkAppcertUpdate(AppCert appCert){
        return !appCertMap.get(appCert.getId()).equals(appCert);
    }



}
