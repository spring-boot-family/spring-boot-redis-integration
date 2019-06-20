package com.xinyan;

import com.xinyan.common.RedisTypeEnum;
import com.xinyan.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author weimin_ruan
 * @date 2019/6/20
 */
@SpringBootApplication
@Slf4j
public class SpringBootRedisIntegrationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRedisIntegrationApplication.class, args);
	}

	@Autowired
    RedisService redisService;

    @Override
    public void run(String... args) throws Exception {
        log.info("service started");
        redisService.set("hello", "world!");
        log.info("连接redis成功，hello, {}", redisService.get("hello"));
        redisService.delete(RedisTypeEnum.STRING, "hello");
    }
}
