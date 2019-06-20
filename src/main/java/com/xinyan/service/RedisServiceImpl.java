package com.xinyan.service;

import com.alibaba.fastjson.JSON;
import com.xinyan.common.RedisTypeEnum;
import com.xinyan.common.SymbolEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作类
 *
 * @author weimin_ruan
 * @date 2019/6/20
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @SuppressWarnings("rawtypes")
    @Resource
    private RedisTemplate redisTemplate;

    @Value("${spring.profiles.active}")
    private String env;

    @Value("${spring.application.name}")
    private String applicationName;

    private String getKey(RedisTypeEnum typeEnum, String key){
        return new StringBuilder(env)
                .append(SymbolEnum.COLON.getSymbol())
                .append(applicationName)
                .append(SymbolEnum.COLON.getSymbol())
                .append(typeEnum.getType())
                .append(SymbolEnum.COLON.getSymbol())
                .append(key).toString();
    }

    private List<String> getKeyList(RedisTypeEnum typeEnum, List<String> keys) {
        List<String> newKeys = new ArrayList<>(keys.size());
        for (String key : keys) {
            newKeys.add(getKey(typeEnum, key));
        }
        return newKeys;
    }

    private <T> Map<String, T> getKeyMap(RedisTypeEnum typeEnum, Map<String, T> paramMap) {
        Map<String, T> newKeys = new HashMap<>(paramMap.size());
        for (String key : paramMap.keySet()) {
            newKeys.put(getKey(typeEnum, key), paramMap.get(key));
        }
        return newKeys;
    }

    /** ===============Redis-String数据结构接口START=============== */
    /**
     * 设置字符串
     *
     * @param key
     *            key
     * @param value
     *            字符串值
     */
    @SuppressWarnings("unchecked")
    @Override
    public void set(String key, String value) {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * 设置字符串(含过期时间)
     *
     * @param key
     *            key
     * @param value
     *            字符串值
     * @param timeout
     *            过期时间，单位：秒
     */
    @SuppressWarnings("unchecked")
    @Override
    public void set(String key, String value, long timeout) {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置多个字符串
     *
     * @param paramMap
     *            字符串Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public void mset(Map<String, String> paramMap) {
        paramMap = getKeyMap(RedisTypeEnum.STRING, paramMap);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.multiSet(paramMap);
    }

    /**
     * 获取字符串
     *
     * @param key
     *            key
     * @return String key对应的字符串值
     */
    @SuppressWarnings("unchecked")
    @Override
    public String get(String key) throws SerializationException {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 根据key列表获取字符串列表
     *
     * @param keys
     *            key列表
     * @return List<String> key列表对应的字符串列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> mget(List<String> keys) {
        keys = getKeyList(RedisTypeEnum.STRING, keys);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.multiGet(keys);
    }

    /**
     * 设置对象(采用Redis的String存储)，默认只存七天
     *
     * @param key
     *            key
     * @param T
     *            对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void setObject(String key, T T) {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, T);
    }

    /**
     * 设置对象(采用Redis的String存储，含过期时间)
     *
     * @param key
     *            key
     * @param T
     *            对象
     * @param timeout
     *            过期时间，单位：秒
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void setObject(String key, T T, long timeout) {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, T, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置对象(采用Redis的String存储，含过期时间)
     *
     * @param key
     *            key
     * @param T
     *            对象
     * @param timeout
     *            过期时间，单位：秒
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Object> void setObjectByKey(String key, T T, long timeout) {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, T, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置多key的同一类型对象(采用Redis的String存储)
     *
     * @param paramMap
     *            参数Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void msetObject(Map<String, T> paramMap) {
        paramMap = getKeyMap(RedisTypeEnum.STRING, paramMap);
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.multiSet(paramMap);
    }

    /**
     * 根据key获取对象(对象采用Redis的String存储)
     *
     * @param key
     *            key
     * @return T 对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T getObject(String key) throws SerializationException {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 根据key获取对象(对象采用Redis的String存储)
     *
     * @param key
     *            key
     * @return T 对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Object> T getObjectByKey(String key) throws SerializationException {
        key = getKey(RedisTypeEnum.STRING, key);
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);

    }

    /**
     * 根据key列表获取同一对象列表(列表中的对象采用Redis的String存储)
     *
     * @param keys
     *            keys列表
     * @return List<T> 对象列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> mgetObject(List<String> keys) {
        keys = getKeyList(RedisTypeEnum.STRING, keys);
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.multiGet(keys);
    }

    /** ===============Redis-String数据结构接口END=============== */

    /** ===============Redis-HASH数据结构接口START=============== */
    /**
     * Hash设置,可用于保存对象或者保存对象的单个field
     *
     * @param key
     *            Hash表的key
     * @param field
     *            Hash表中的域field
     * @param T
     *            对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void hset(String key, String field, T T) {
        key = getKey(RedisTypeEnum.HASH, key);
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, field, T);
    }

    /**
     * Hash批量设置,可用于保存多个对象或者保存单个对象的多个field
     *
     * @param key
     *            Hash表的key
     * @param paramMap
     *            Hash表的field和Value组成的Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void hmset(String key, Map<String, T> paramMap) {
        key = getKey(RedisTypeEnum.HASH, key);
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, paramMap);
    }

    /**
     * 根据Hash表的key和域Field获取对应的Value(可用于获取对象或者获取对象的单个field)
     *
     * @param key
     *            Hash表的key
     * @param field
     *            Hash表中的域field
     * @return T Hash的key和域Field获取对应的Value
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T hget(String key, String field) {
        key = getKey(RedisTypeEnum.HASH, key);
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, field);
    }

    /**
     * 根据Hash表的key和域Field列表获取对应的Value列表(可用于获取多个对象或者对象的多个属性)
     *
     * @param key
     *            Hash表的key
     * @param fields
     *            域Field列表
     * @return List<T> Hash的key和域Field列表获取对应的Value列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> hmget(String key, List<String> fields) {
        key = getKey(RedisTypeEnum.HASH, key);
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.multiGet(key, fields);
    }


    /**
     * 根据Hash表的key获取对应的所有对象(可用于获取多个对象)
     *
     * @param key
     *            Hash表的key
     * @return Map<String, T> Hash表的key对应的所有对象Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> hgetAll(String key) {
        key = getKey(RedisTypeEnum.HASH, key);
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * 根据Hash表的key和域Field进行删除
     *
     * @param <T>
     * @param key
     *            Hash表的key
     * @param fields
     *            Hash表对应域Field数组
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void hDel(String key, Object... fields) {
        key = getKey(RedisTypeEnum.HASH, key);
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, fields);
    }

    /** ===============Redis-HASH数据结构接口END=============== */

    /** ===============Redis-SET数据结构接口START=============== */

    @Override
    public <T> void addSet(String key, T val) {
        key = getKey(RedisTypeEnum.SET, key);
        SetOperations<String, T> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, val);
    }

    @Override
    public <T> Set<T> getSetAll(String key) {
        key = getKey(RedisTypeEnum.SET, key);
        SetOperations<String, T> setOperations = redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    /** ===============Redis-SET数据结构接口END=============== */

    /** ===============Redis-LIST数据结构接口START=============== */

    @Override
    public <T> Long rPush(String key, T... obj) {
        String[] values = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            values[i] = JSON.toJSONString(obj[i]);
        }
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public String lPop(String key) {
        Object o = redisTemplate.opsForList().leftPop(key);
        return o == null ? null : o.toString();
    }

    /** ===============Redis-LIST数据结构接口END=============== */

    /** ===============Redis接口START=============== */
    /**
     * 设置key在多少秒后过期
     *
     * @param key
     *            key
     * @param timeout
     *            过期时间
     * @return boolean 是否设置成功
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean expire(RedisTypeEnum typeEnum, String key, long timeout) {
        key = getKey(typeEnum, key);
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置key在固定的某个时刻后过期
     *
     * @param key
     *            key
     * @param date
     *            固定的某个时刻
     * @return boolean 是否设置成功
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean expireAt(RedisTypeEnum typeEnum, String key, Date date) {
        key = getKey(typeEnum, key);
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean hasKey(RedisTypeEnum typeEnum, String key) {
        key = getKey(typeEnum, key);
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key删除单个对象
     *
     * @param key
     *            Redis中存储的key
     */
    @SuppressWarnings("unchecked")
    @Override
    public void delete(RedisTypeEnum typeEnum, String key) {
        key = getKey(typeEnum, key);
        redisTemplate.delete(key);
    }

    /**
     * 根据key列表删除多个对象
     *
     * @param keys
     *            keys列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public void deleteAll(RedisTypeEnum typeEnum, List<String> keys) {
        keys = getKeyList(typeEnum, keys);
        redisTemplate.delete(keys);
    }

    /**
     * 根据key列表删除多个对象
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    @Override
    public Long increase(String key) {
        key = getKey(RedisTypeEnum.STRING, key);
        return redisTemplate.boundValueOps(key).increment(1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long decrease(String key) {
        key = getKey(RedisTypeEnum.STRING, key);
        return redisTemplate.boundValueOps(key).increment(-1);
    }

    /**
     * 获取token的有效期
     * @param key
     */
    @SuppressWarnings("unchecked")
    @Override
    public Long getExpireTime(RedisTypeEnum typeEnum, String key) {
        key = getKey(typeEnum, key);
        return redisTemplate.getExpire(key);
    }

    /** ===============Redis接口END=============== */
}
