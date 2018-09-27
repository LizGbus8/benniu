package com.lzg.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lzg.common.enums.ContentStatusEnum;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisLock;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.KeyUtil;
import com.lzg.manager.dao.ContentDao;
import com.lzg.manager.entity.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 作者：LizG on 2018/7/28 14:23
 * 描述：
 */
@Slf4j
@Service
@Component
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisLock redisLock;

    private static final String CONTENT = "content";

    @Override
    public Content findContentById(String contentId) {
        /** 查找缓存 */
        Content content = null;
        try {
            content = (Content) redisUtil.hget(CONTENT, contentId);
        }catch (Exception e){
            e.printStackTrace();
        }

        /** 缓存获取成功，直接返回 */
        if(content != null){
            return content;
        }

        /** 查找数据库 */
        Content findContent = contentDao.findOne(contentId);

        /** 数据库不存在该内容，直接返回 */
        if (findContent == null){
            throw new TBookException(ResultEnum.CONTENT_NOT_EXIST);
        }

        /** 缓存同步 */
        try {
            redisUtil.hset(CONTENT,contentId,findContent);
        }catch (Exception e){
            e.printStackTrace();
        }
        return findContent;
    }

    @Override
    public Content saveContent(Content content) {
        /** 生成内容id */
        String contentId = CONTENT + KeyUtil.getKey();
        content.setContentId(contentId);

        /** 设置内容状态 */
        content.setContentStatus(ContentStatusEnum.UP.getCode());

        /** 初始化点赞数 */
        content.setContentStar(0);

        System.out.println(content);

        /** 内容入库 */
        contentDao.save(content);

        //TODO 更新索引库

        return content;
    }

    @Override
    public Content updateContent(Content c) {
        /** 查找内容 */
        Content findContent = findContentById(c.getContentId());

        /** 内容入库 */
        contentDao.save(findContent);

        /** 同步缓存 */
        try {
            redisUtil.hdel(CONTENT,findContent.getContentId());
        }catch (Exception e){
            e.printStackTrace();
        }

        //TODO 更新索引库
        return findContent;
    }

    @Override
    public void increaseStock(String contentId) {
        /** 查找内容 */
        Content content = findContentById(contentId);

        if (content == null){
            throw new TBookException(ResultEnum.CONTENT_NOT_EXIST);
        }

        /** 库存加1 */
        Integer stock = content.getProductStock();
        content.setProductStock(stock++);

        /** 更新 */
        updateContent(content);
    }

    //TODO 分布式锁
    @Override
    public void decreaseStock(String contentId) {

        /** 加锁同步 */
        redisLock.tryGetDistributedLock(contentId);


        /** 查找内容 */
        Content content = findContentById(contentId);

        if (content == null){
            throw new TBookException(ResultEnum.CONTENT_NOT_EXIST);
        }

        /** 库存减1 */
        Integer stock = content.getProductStock() - 1;
        if (stock < 0){
            throw new TBookException(ResultEnum.CONTENT_STOCK_ERROR);
        }
        content.setProductStock(stock);

        /** 更新 */
        updateContent(content);
    }

}
