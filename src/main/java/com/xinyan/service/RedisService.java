package com.xinyan.service;

import com.xinyan.common.RedisTypeEnum;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作类
 *
 * @author weimin_ruan
 * @date 2019/6/20
 */

public interface RedisService {

    /** ===============Redis-String数据结构接口START=============== */
    /**
     * 设置字符串
     *
     * @param key   key
     * @param value 字符串值
     */
    void set(String key, String value);

    /**
     * 设置字符串(含过期时间)
     *
     * @param key     key
     * @param value   字符串值
     * @param timeout 过期时间，单位：秒
     */
    void set(String key, String value, long timeout);

    /**
     * 设置多个字符串
     *
     * @param paramMap 字符串Map
     */
    void mset(Map<String, String> paramMap);

    /**
     * 根据key获取字符串
     *
     * @param key key
     * @return String key对应的字符串值
     */
    String get(String key) throws SerializationException;

    /**
     * 根据key列表获取字符串列表
     *
     * @param keys key列表
     * @return List<String> key列表对应的字符串列表
     */
    List<String> mget(List<String> keys);

    /**
     * 设置对象(采用Redis的String存储)
     *
     * @param key key
     * @param T   对象
     */
    <T extends Serializable> void setObject(String key, T T);

    /**
     * 设置对象(采用Redis的String存储，含过期时间)
     *
     * @param key     key
     * @param T       对象
     * @param timeout 过期时间，单位：秒
     */
    <T extends Serializable> void setObject(String key, T T, long timeout);

    /**
     * 设置对象(采用Redis的String存储，含过期时间)
     *
     * @param key     key
     * @param T       对象
     * @param timeout 过期时间，单位：秒
     */
    <T extends Object> void setObjectByKey(String key, T T, long timeout);

    /**
     * 设置多key的同一类型对象集合(采用Redis的String存储)
     *
     * @param paramMap 参数Map
     */
    <T extends Serializable> void msetObject(Map<String, T> paramMap);

    /**
     * 根据key获取对象(对象采用Redis的String存储)
     *
     * @param key key
     * @return T 对象
     * @throws SerializationException
     */
    <T extends Serializable> T getObject(String key) throws SerializationException;

    /**
     * 根据key获取对象(对象采用Redis的String存储)
     *
     * @param key key
     * @return T 对象
     */
    <T extends Object> T getObjectByKey(String key) throws SerializationException;

    /**
     * 根据key列表获取同一对象列表(列表中的对象采用Redis的String存储)
     *
     * @param keys keys列表
     * @return List<T> 对象列表
     */
    <T extends Serializable> List<T> mgetObject(List<String> keys);

    /** ===============Redis-String数据结构接口END=============== */

    /** ===============Redis-Hash数据结构接口START=============== */
    /**
     * Hash设置(可用于保存对象或者保存对象的单个field)
     *
     * @param key   Hash表的key
     * @param field Hash表中的域field
     * @param T     对象
     */
    <T extends Serializable> void hset(String key, String field, T T);

    /**
     * Hash批量设置(可用于保存多个对象或者保存单个对象的多个field)
     *
     * @param key      Hash表的key
     * @param paramMap Hash表的field和Value组成的Map
     */
    <T extends Serializable> void hmset(String key, Map<String, T> paramMap);

    /**
     * 根据Hash表的key和域Field获取对应的Value(可用于获取对象或者获取对象的单个field)
     *
     * @param key   Hash表的key
     * @param field Hash表中的域field
     * @return T Hash的key和域Field获取对应的Value
     */
    <T extends Serializable> T hget(String key, String field);

    /**
     * 根据Hash表的key和域Field列表获取对应的Value列表(可用于获取多个对象或者对象的多个属性)
     *
     * @param key    Hash表的key
     * @param fields 域Field列表
     * @return List<T> Hash的key和域Field列表获取对应的Value列表
     */
    <T extends Serializable> List<T> hmget(String key, List<String> fields);


    /**
     * 根据Hash表的key获取对应的所有对象(可用于获取多个对象)
     *
     * @param key Hash表的key
     * @return Map<String, T> Hash表的key对应的所有对象Map
     */
    <T> Map<String, T> hgetAll(String key);

    /**
     * 根据Hash表的key和域Field进行删除
     *
     * @param key    Hash表的key
     * @param fields Hash表对应域Field数组
     */
    <T> void hDel(String key, Object... fields);

    /** ===============Redis-Hash数据结构接口END=============== */

    /**
     * ===============Redis-Set无序集合数据结构接口END===============
     */

    <T extends Object> boolean isMember(String key, T T);

    /**
     * Set设置(可用于保存对象)
     *
     * @param key
     * @param T   对象
     */
    <T extends Object> Long addSet(String key, T T);


    /**
     * Set设置(可用于保存对象)
     *
     * @param key
     * @param T   对象
     */
    <T extends Object> Long setRemove(String key, T T);

    /**
     * 根据Set-key获取对应的Value
     *
     * @param key
     * @return T 获取对应的Value
     */
    <T extends Object> Set<T> getSetAll(String key);

    /**
     * key对应set的大小
     *
     * @param setKey
     * @return
     */
    Long scard(String setKey);

    /** ===============Redis-Set无序集合数据结构接口END=============== */

    /** ===============Redis-LIST列表数据结构接口END=============== */

    /**
     * List 增加一个或多个value
     *
     * @param key
     * @param obj
     * @return
     */
    <T> Long rPush(String key, T... obj);

    /**
     * List 获取一个值
     *
     * @param key $@return
     */
    String lPop(String key);

    /**
     * key对应list的大小
     *
     * @param key
     * @return
     */
    Long llen(String key);

    /** ===============Redis-LIST列表数据结构接口END=============== */

    /** ===============Redis接口START=============== */
    /**
     * 设置key在多少秒后过期
     *
     * @param key     key
     * @param timeout 过期时间
     * @return boolean 是否设置成功
     */
    boolean expire(RedisTypeEnum typeEnum, String key, long timeout);

    /**
     * 设置key在固定的某个时刻后过期
     *
     * @param key  key
     * @param date 固定的某个时刻
     * @return boolean 是否设置成功
     */
    boolean expireAt(RedisTypeEnum typeEnum, String key, Date date);

    /**
     * 根据key删除单个对象
     *
     * @param key Redis中存储的key
     */
    void delete(RedisTypeEnum typeEnum, String key);

    /**
     * 根据key列表删除多个对象
     *
     * @param keys keys列表
     */
    void deleteAll(RedisTypeEnum typeEnum, List<String> keys);

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    boolean hasKey(RedisTypeEnum typeEnum, String key);

    /**
     * @author wenbin_zhu
     * @Title increase
     * @Description: 自增
     */
    Long increase(String key);

    /**
     * @author wenbin_zhu
     * @Title increase
     * @Description: 自增
     */
    Long decrease(String key);

    /**
     * 获取token的有效期
     *
     * @param key
     */
    Long getExpireTime(RedisTypeEnum typeEnum, String key);

    /** ===============Redis接口END=============== */


}