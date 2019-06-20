package com.xinyan;

import com.xinyan.service.RedisDistributedLock;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisIntegrationApplicationTests {

	@Autowired
	RedisDistributedLock distributedLock;

	@Test
	public void contextLoads() {
        String result = distributedLock.tryLock("LOCK_KEY", 60000);
        if (StringUtils.isNoneBlank(result)) {
            Assert.assertTrue(distributedLock.releaseLock("LOCK_KEY", result));
        }
    }

}
