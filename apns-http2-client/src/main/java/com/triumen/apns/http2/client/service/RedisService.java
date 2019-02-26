package com.triumen.apns.http2.client.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by by Martin Lee on 2019/2/16 16:56.
 */
@Service("redisService")
public class RedisService {

    static final Logger log = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    private JedisPool jedisPool;

    static final String message_redislist = "sendmessage:apns:redislist";
    static final String message_failure_redislist = "sendmessage:apns_failure:redislist";

    public void putFailureMesage(JSONObject object){
        redisTemplate.opsForList().leftPush(message_failure_redislist,object.toJSONString());
    }
    public void putMesage(JSONObject object){
        redisTemplate.opsForList().leftPush(message_redislist,object.toJSONString());
    }
    public JSONObject getMesage(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> listingList = jedis.brpop(3000,message_redislist);
            if(listingList!=null&&listingList.get(1)!=null){
                return JSONObject.parseObject(listingList.get(1));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(jedisPool, jedis);
        }
        return null;
    }
    public JSONObject getFailureMesage(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> listingList = jedis.brpop(5000,message_failure_redislist);
            if(listingList!=null&&listingList.get(1)!=null){
                return JSONObject.parseObject(listingList.get(1));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(jedisPool, jedis);
        }
        return null;
    }
    /**
     * 返还到连接池
     *
     * @param jedisPool
     * @param jedis
     */
    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
