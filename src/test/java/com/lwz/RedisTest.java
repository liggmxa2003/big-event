package com.lwz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //Redis存储数据
    @Test
    public void testSet(){
        //让Redis中存储一个键值对 StringRedisTemplate
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        operations.set("name","lwz");
                                                  /*过期时间，单位是秒  */
        operations.set("id","1",10, TimeUnit.SECONDS);
    }
    //从Redis中获取数据
    @Test
    public void testGet(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String name = operations.get("ligg200309");
        System.out.println(name);
    }
}
