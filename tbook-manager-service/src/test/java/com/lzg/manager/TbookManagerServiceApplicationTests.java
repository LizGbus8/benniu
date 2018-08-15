package com.lzg.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TbookManagerServiceApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * stringRedisTemplate.opsForValue();//操作字符串
     * stringRedisTemplate.opsForHash();//操作hash
     * stringRedisTemplate.opsForList();//操作list
     * stringRedisTemplate.opsForSet();//操作set
     * stringRedisTemplate.opsForZSet();//操作有序set
     */
    @Test
    public void contextLoads() {
        stringRedisTemplate.opsForValue().append("lzg","fyt");
    }

}
