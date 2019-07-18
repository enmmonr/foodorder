package com.food.foodorder.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis分布式锁
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     * setIfAbsent()相当于redis中的setnx命令,
     *    设置值为value的key,如果key不存在，等同于set命令，key存在不做任何操作
     *    返回值：如果key被设置返回1，否则返回0
     * getAndSet(key,value)相当于redis中的GETSET命令
     *      先返回key旧对应的值value，再设置现在的value
     */
    public boolean lock(String key,String value){

        if(stringRedisTemplate.opsForValue().setIfAbsent(key,value)){
            //key不存在，上锁
            return true;
        }
        String currentValue=stringRedisTemplate.opsForValue().get(key);
        //如果锁过期  （超时时间<当前系统时间）
        if(StringUtils.isNotEmpty(currentValue)&& Long.parseLong(currentValue)<System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldValue=stringRedisTemplate.opsForValue().getAndSet(key,value);
            if(StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentValue)){
                return  true;
            }
        }
    return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unLock(String key,String value){
        try {
            String currentValue=stringRedisTemplate.opsForValue().get(key);
            if(StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)){
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("[分布式锁解锁]解锁异常");
        }

    }
}
