package com.lzg.tbook.content.web.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.JsonUtilByGson;
import com.lzg.manager.entity.Category;
import com.lzg.manager.entity.Content;
import com.lzg.manager.service.CategoryService;
import com.lzg.manager.service.ContentService;
import com.lzg.tbook.content.web.converter.Content2ProductDTOConverter;
import com.lzg.tbook.content.web.dto.HotProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.TimerTask;

/**
 * 作者：LizG on 2018/10/6 16:51
 * 描述：//在项目启动时，启动计时器
 */
@Slf4j
@Component
public class HotUpdateTask extends TimerTask {

    @Autowired
    private RedisUtil redisUtil;

    @Reference
    private CategoryService categoryService;

    @Reference
    private ContentService contentService;

    @Override
    public void run() {
        log.info("HotUpdateTask 执行了一次");

        //更新hot队列
        /* 获取所有的队列（分类） */
        List<Category> categoryList = categoryService.findAll();

        /* 获取每个热门队列前100的content */
        for (Category category : categoryList) {

            //获取分类简称
            String sname = category.getCategorySname();
            //获取每个队列前100的数据,json格式
            Set<Object> set = redisUtil.zRank(sname, 0L, 100L);

            /** 删除原队列（分类） */
            redisUtil.del(sname);

            //更新队列中的元素
            for (Object json : set) {
                HotProductDTO hotProductDTO = JsonUtilByGson.jsonToBean((String) json, HotProductDTO.class);

                Content findContent = contentService.findContentById(hotProductDTO.getContentId());


                /* 计算hot */
                long grown = System.currentTimeMillis() - findContent.getCreateTime().getTime();

                int readAndStar = findContent.getContent_read() + findContent.getContentStar()*9;

                Double hot = (double)readAndStar/(double)grown;

                HotProductDTO p = Content2ProductDTOConverter.content2ProductDTO(findContent);

                //更新入队
                redisUtil.zAdd(sname,JsonUtilByGson.beanToJson(p),hot);
            }
        }
    }
}
