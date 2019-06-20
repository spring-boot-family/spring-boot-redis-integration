package com.xinyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * class description
 *
 * @author weimin_ruan
 * @date 2019/6/20
 */
@RedisHash
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {

    @Id
    String id;

    String firstname;

    String lastname;

    int age;
}
