package com.triumen.apns.http2.client.domain;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by by Martin Lee on 2019/2/13 18:05.
 */
public class AppCert {

    Integer id;//int(11) NOT NULL
    Integer appId;//int(11) NOT NULL
    String cert;//blob NULL
    String name;//varchar(255) NULL
    String password;//varchar(255) NULL
    String topic;//varchar(255) NULL
    String type;//varchar(32) NULL
    String createTime;//datetime NULL
    String updateTime;//datetime NULL

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    @Autowired
    public boolean equals(AppCert appCert){
        return this.updateTime.equals(appCert.getUpdateTime());
    }
}
