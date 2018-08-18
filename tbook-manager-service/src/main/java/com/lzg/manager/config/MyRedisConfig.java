package com.lzg.manager.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;


/**
 * 作者：LizG on 2018/8/2 15:46
 * 描述：springboot缓存注解整合redis
 */
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

//        Hibernate5Module hm = new Hibernate5Module();
//        hm.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, false);
//        objectMapper.registerModule(hm);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        hostAndPortSet.add(new HostAndPort("120.79.254.32",7001));
        hostAndPortSet.add(new HostAndPort("120.79.254.32",7002));
        hostAndPortSet.add(new HostAndPort("120.79.254.32",7003));
        hostAndPortSet.add(new HostAndPort("120.79.254.32",7004));
        hostAndPortSet.add(new HostAndPort("120.79.254.32",7005));
        hostAndPortSet.add(new HostAndPort("120.79.254.32",7006));
        JedisCluster jedisCluster = new JedisCluster(hostAndPortSet);
        return jedisCluster;
    }
}
