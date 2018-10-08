package com.lzg.tbook.content.web.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.JsonUtilByGson;
import com.lzg.manager.entity.Content;
import com.lzg.manager.service.ContentService;
import com.lzg.tbook.content.web.dto.HotContentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

/**
 * 作者：LizG on 2018/9/27 14:20
 * 描述：
 */
@Service
@Slf4j
public class HotListener {
    @Reference
    private ContentService contentService;

    @Autowired
    private RedisUtil redisUtil;

    private static final String READ = "read";

    /**
     * 描述: 商品发布时加入热门队列
     * @Param
     * @return
     */
    @RabbitListener(queues = "category.add2hot")
    public void add2hot(HotContentDTO hotContentDTO){
        try {
            /* 获取第30名 */
            Set<Object> hotMinSet = redisUtil.zRank(hotContentDTO.getCategorySname(), 30L, 30L);

            /* 如果队列没到30 */
            if (hotMinSet == null || hotMinSet.size() <=0){
                redisUtil.zAdd(hotContentDTO.getCategorySname(),JsonUtilByGson.beanToJson(hotContentDTO.getHotProductDTO()),hotContentDTO.getHot());
            }else {
                /* 队列到了30，拿出队列中第30的热门商品 */
                HotContentDTO h30 = JsonUtilByGson.jsonToBean((String) hotMinSet.toArray()[0], HotContentDTO.class);
                if (hotContentDTO.getHot() > h30.getHot()){
                    redisUtil.zAdd(hotContentDTO.getCategorySname(),JsonUtilByGson.beanToJson(hotContentDTO.getHotProductDTO()),hotContentDTO.getHot());
                }
            }

        }catch (Exception e){
            log.error("add2hot listen exception");
        }
    }

}
