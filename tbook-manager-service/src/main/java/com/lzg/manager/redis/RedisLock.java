package com.lzg.manager.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Arrays;
import java.util.UUID;

/**
 * 作者：LizG on 2018/8/3 23:54
 * 描述：redis分布式锁
 */
@Component
public class RedisLock {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 锁标志
     */
    private static String LOCK = "lock:";

    /**
     * 锁超时时间
     */
    private static Long LOCK_TIMEOUT = 100L;

    /**
     * 加锁超时时间
     */
    private static Long ACQ_TIMEOUT = 3000L;


    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * lua脚本
     */
    private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 描述: 尝试获取锁
     *
     * @return 获得回锁标识，失败返回null
     * @Param key 锁的key
     * @Param getTimeout 获取超时时间
     * @Param timeout 锁超时时间
     */
    public String tryGetDistributedLock(String key) {
        //锁标识
        String identity = UUID.randomUUID().toString();

        //锁key
        String lockKey = LOCK + key;

        //超时时间
        long end = System.currentTimeMillis() + ACQ_TIMEOUT;

        //自旋锁
        while (System.currentTimeMillis() <= end) {
            //如果锁不存在则获取
            if (redisUtil.setnx(lockKey, identity)) {
                redisUtil.expire(lockKey, LOCK_TIMEOUT);
                return identity;
            }

            //锁存在则判断该锁有没有设置超时
            if (redisUtil.ttl(lockKey) == -1) {
                redisUtil.expire(lockKey, LOCK_TIMEOUT);
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public boolean releaseLock(String key, String identity) {
        //锁key
        String lockKey = LOCK + key;
        try {
            Object result = jedisCluster.eval(SCRIPT, Arrays.asList(lockKey), Arrays.asList(identity));
            System.out.println(result);
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
