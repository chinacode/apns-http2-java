package com.triumen.apns.http2.client;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@MapperScan("com.triumen.apns.http2.client.dao")
@SpringBootApplication
//开启定时任务
@EnableScheduling
public class ClientApplication {
    private static final Logger log = LoggerFactory.getLogger(ClientApplication.class);

    private static final String TOKEN = "8390e6e3bb099990f99452b9f9510137dbf0786ebd08bede1273f90c492b8408";

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
