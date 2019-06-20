package com.xinyan;

import com.xinyan.domain.Person;
import com.xinyan.domain.PersonRepository;
import com.xinyan.service.RedisDistributedLock;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisIntegrationApplicationTests {

	@Autowired
	RedisDistributedLock distributedLock;

	@Autowired
    PersonRepository repository;

	@Test
	public void contextLoads() {
        String result = distributedLock.tryLock("LOCK_KEY", 60000);
        if (StringUtils.isNoneBlank(result)) {
            Assert.assertTrue(distributedLock.releaseLock("LOCK_KEY", result));
        }
    }

    @Test
    public void redisRepository() {
	    repository.save(new Person(null, "ruan", "weimin", 24));
        Iterable<Person> all = repository.findAll();
        Iterator<Person> iterator = all.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            System.out.println(person);
            repository.delete(person);
        }
    }

}
