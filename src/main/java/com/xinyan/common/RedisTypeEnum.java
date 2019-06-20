package com.xinyan.common;

import lombok.Getter;

/**
 * REDIS类型
 *
 * @author weimin_ruan
 * @date 2019/6/20
 */
@Getter
public enum RedisTypeEnum {
    /**
     * 字符串
     */
    STRING("string"),
    /**
     * 哈希表
     */
    HASH("hash"),
    /**
     * 列表
     */
    LIST("list"),
    /**
     * 无序集合
     */
    SET("set"),
    /**
     * 有序集合
     */
    SORTED_SET("sorted_set"),
    ;
    private String type;

    RedisTypeEnum(String type) {
        this.type = type;
    }
}
