package com.lzg.manager.listener;

import com.lzg.common.redis.RedisUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 作者：LizG on 2018/9/27 10:35
 * 描述：
 */
//暂时没用
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        RedisUtil redisUtil = event.getApplicationContext().getBean(RedisUtil.class);
        /** 初始化redis热门商品队列 */
        redisUtil.zAdd("it","Initialization",0.0);
        redisUtil.zAdd("en","Initialization",0.0);
        redisUtil.zAdd("course","Initialization",0.0);
        redisUtil.zAdd("ky","Initialization",0.0);
        redisUtil.zAdd("kg","Initialization",0.0);
        redisUtil.zAdd("mng","Initialization",0.0);
        redisUtil.zAdd("ec","Initialization",0.0);
    }
}
