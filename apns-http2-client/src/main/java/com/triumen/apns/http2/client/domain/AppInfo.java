package com.triumen.apns.http2.client.domain;

/**
 * Created by by Martin Lee on 2019/2/13 18:05.
 */
public class AppInfo {

    private Integer id;
    private String name;
    private String createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
