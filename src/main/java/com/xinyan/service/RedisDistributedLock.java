package com.xinyan.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁
 *
 * @author weimin_ruan
 * @date 2019/6/20
 */
@Component
@Slf4j
public class RedisDistributedLock {

    @Resource
    private RedisTemplate redisTemplate;

    private static String unlockScript;

    /**
     * 释放锁脚本，原子操作
     * 封装Redis Script
     * @return  Redis Script
     */
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        unlockScript = sb.toString();
    }

    /**
     * 获取分布式锁，原子操作 -> 只尝试获取一次
     *
     * @param lockKey
     * @param expire
     * @return
     */
    public String tryLock(String lockKey, long expire) {
        String token = UUID.randomUUID().toString().replace("-", "");
        try{
            RedisCallback<Boolean> callback = (connection) -> connection.set(lockKey.getBytes(Charset.forName("UTF-8")), token.getBytes(Charset.forName("UTF-8")), Expiration.seconds(TimeUnit.MILLISECONDS.toSeconds(expire)), RedisStringCommands.SetOption.SET_IF_ABSENT);
            if ((Boolean)redisTemplate.execute(callback)){
                return token;
            }
            return null;
        } catch (Exception e) {
            log.error("redis lock error {}", e);
            return null;
        }
    }

    /**
     * 指定时间内自旋获取分布式锁
     *
     * @param lockKey
     * @param expire key失效时间
     * @param timeout 获取锁超时时间
     * @return
     */
    public String tryLock(String lockKey, long expire, long timeout) {
        // 限制阻塞时间，根据自己的业务系统设置。如果尝试加锁的线程多的话最好不要设置的太大，要不然会有太多的线程在自旋，耗费CPU
        Assert.isTrue(timeout>0 && timeout<=60000, "timeout must greater than 0 and less than 1 min");
        long startTime = System.currentTimeMillis();
        String token;
        do {
            token = tryLock(lockKey, expire);
            // 加锁失败
            if(StringUtils.isBlank(token)) {
                if((System.currentTimeMillis()-startTime) > (timeout-50)) {
                    // 超过阻塞时间则跳出
                    break;
                }
                try {
                    // 等待50ms再试，线程多的话这个值不建议设置的太小
                    Thread.sleep(50);
                } catch (InterruptedException ie) {
                    log.error("lockKey: {} sleep error: {}", lockKey, ie);
                }
            }
        } while (StringUtils.isBlank(token));
        log.debug("tryLock 获取锁token: {} 完成.", token);
        return token;
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @param token 唯一ID
     * @return
     */
    public boolean releaseLock(String lockKey, String token) {
        Assert.notNull(lockKey, "lockKey must not be null");
        Assert.notNull(token, "Token must not be null");
        RedisCallback<Boolean> callback = (connection) -> connection.eval(unlockScript.getBytes(), ReturnType.BOOLEAN ,1, lockKey.getBytes(Charset.forName("UTF-8")), token.getBytes(Charset.forName("UTF-8")));
        return (Boolean)redisTemplate.execute(callback);
    }

    /**
     * 获取Redis锁的value值
     *
     * @param lockKey
     * @return
     */
    public String get(String lockKey) {
        try {
            RedisCallback<String> callback = (connection) -> new String(connection.get(lockKey.getBytes()), Charset.forName("UTF-8"));
            return (String)redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("get redis occurred an exception", e);
        }
        return null;
    }

}