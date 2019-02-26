package com.triumen.apns.http2.client;

import com.triumen.apns.http2.client.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by by Martin Lee on 2019/2/16 17:02.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    RedisService redisService;

    @Test
    public void test() {
        int i = 0;
        while (i < 1) {



            String str = "{" +
                    "  \"title\": \"收款通知\"," +
                    "  \"content\": \"微信收款"+ (i + 1) + "元\"," +
                    "  \"order_id\": \"1902120100300761501\"," +
                    "  \"noticetype\": 1," +
                    "  \"createtime\": 1549966645," +
                    "  \"data\": {" +
                    "    \"pay_money\": 9," +
                    "    \"pay_type\": \"1\"," +
                    "    \"order_time\": 1549966645," +
                    "    \"app_type\": 1," +
                    "    \"create_time\": 1549966645" +
                    "  }," +
                    "  \"vertype\": \"110\"," +
                    "  \"profiles\": \"pro\"," +
//                    "  \"device_token\": \"8390e6e3bb099990f99452b9f9510137dbf0786ebd08bede1273f90c492b8408\"," +


                    "  \"device_token\": \" 1b6ee0e1a4b49287bfaecd6f45777478625bd6a0c166f859a4c8ff0faf9135e3\"," +
                    "  \"system\": 1," +
                    "  \"sound\": \"\"" +
                    "}";

            JSONObject obj = JSONObject.parseObject(str);
            System.out.println(obj.toJSONString());
            redisService.putMesage(obj);


            i++;
//        redisService.gutMesage();
//        redisService.gutMesage();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
