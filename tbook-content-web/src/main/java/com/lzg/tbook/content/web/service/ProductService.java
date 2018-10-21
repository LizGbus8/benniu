package com.lzg.tbook.content.web.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.JsonUtilByGson;
import com.lzg.manager.entity.Category;
import com.lzg.manager.entity.Content;
import com.lzg.manager.entity.User;
import com.lzg.manager.service.CategoryService;
import com.lzg.manager.service.ContentService;
import com.lzg.sso.service.UserInfoService;
import com.lzg.tbook.content.web.converter.Content2ProductDTOConverter;
import com.lzg.tbook.content.web.dto.ContentDTO;
import com.lzg.tbook.content.web.dto.HotContentDTO;
import com.lzg.tbook.content.web.dto.HotProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 作者：LizG on 2018/10/3 0:29
 * 描述：
 */
@Component
@Slf4j
public class ProductService {
    @Autowired
    RedisUtil redisUtil;

    @Reference
    ContentService contentService;

    @Reference
    UserInfoService userInfoService;

    @Reference
    private CategoryService categoryService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    static final String CONTNET = "content";

    static final String READ = "read";

    public ContentDTO findContent(String contentId) {
        if (StringUtils.isEmpty(contentId)) {
            throw new TBookException(ResultEnum.CONTENT_NOT_EXIST);
        }
        //查找缓存
        Content content = (Content) redisUtil.hget(CONTNET, contentId);

        ContentDTO contentDTO = new ContentDTO();

        if (content != null) {
            /* 查找商品发布者信息 */
            User user = userInfoService.getUserInfoByOpenid(content.getUserId());
            log.info("user is {}", user);

            /* 设置用户名称 */
            contentDTO.setUsername(user.getNickname());
            /* 设置头像 */
            contentDTO.setImage(user.getHeadImgUrl());
            BeanUtils.copyProperties(content, contentDTO);
            return contentDTO;
        }

        //缓存未命中，数据库查找
        content = contentService.findContentById(contentId);
        if (content == null) {
            throw new TBookException(ResultEnum.CONTENT_NOT_EXIST);
        }

        /* 查找商品发布者信息 */
        User user = userInfoService.getUserInfoByOpenid(content.getUserId());
        log.info("user is {}", user);

        /* 设置用户名称 */
        contentDTO.setUsername(user.getNickname());
        /* 设置头像 */
        contentDTO.setImage(user.getHeadImgUrl());

        BeanUtils.copyProperties(content, contentDTO);
        log.info("contentDTO has {}", contentDTO);
        return contentDTO;
    }

    public void increaseRead(Content content) {
        String contentId = content.getContentId();

        /** 访问redis */
        Integer read = (Integer) redisUtil.hget(READ, contentId);
        if (read == null) {
            /* 初始化 */
            redisUtil.hset(READ, contentId, 1);
        } else if (read >= 10) {
            try {
                /* 持久化 */
                content.setContent_read(content.getContent_read()+10);
                content = contentService.updateContent(content);
            }catch (Exception e){
                log.error("read 持久化错误");
            }
            redisUtil.hdel(READ,contentId);
        }else{
            redisUtil.hset(READ, contentId, 1 + read);

        }
        /** 这里没必要 */
        //content.setContent_read(content.getContent_read()+1+read);
        //log.info("hot content :{}",content);
        //updateHot(content);
    }

    /* 没必要 */
    public void updateHot(Content content){
        /* 计算hot */
        long grown = System.currentTimeMillis() - content.getCreateTime().getTime();

        log.info("grown:{}",grown);
        int readAndStar = content.getContent_read() + content.getContentStar();

        Double hot = (double)readAndStar/(double)grown;

        log.info("hot:{}",hot);

        /** 异步更新redis热门商品队列 */
        HotProductDTO productDTO = Content2ProductDTOConverter.content2ProductDTO(content);
        log.info("productDTO is {}", productDTO);

        Category category = categoryService.findCategoryById(content.getCategoryId());
        HotContentDTO hotContent = new HotContentDTO(category.getCategorySname(), productDTO, hot);
        log.info("hotContent is {}", hotContent);
        try {
            rabbitTemplate.convertAndSend("category",
                    "category.add2hot", hotContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
