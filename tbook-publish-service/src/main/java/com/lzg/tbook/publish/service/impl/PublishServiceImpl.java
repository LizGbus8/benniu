package com.lzg.tbook.publish.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lzg.common.enums.PublishStatusEnum;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.KeyUtil;
import com.lzg.manager.service.ContentService;
import com.lzg.order.dto.OrderDTO;
import com.lzg.publish.dto.PublishDTO;
import com.lzg.publish.entity.Publish;
import com.lzg.publish.service.PublishService;
import com.lzg.tbook.publish.dao.PublishDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LizG on 2018/7/29 13:15
 * 描述：
 */
@Component
@Service(version = "1.0.0")
public class PublishServiceImpl implements PublishService {

    @Reference(version = "1.0.0")
    private ContentService contentService;

    @Autowired
    private PublishDao publishDao;

    @Override
    @Transactional
    public PublishDTO create(PublishDTO publishDTO) {
        //1.设置发布id
        String publishId = KeyUtil.getKey();
        publishDTO.setPublishId(publishId);

        //2.商品入库
        ProductInfo productInfo = publishDTO.getProductInfo();
        String productId = KeyUtil.getKey();
        productInfo.setProductId(productId);
        contentService.saveProduct(productInfo);

        //3.发布入库
        Publish publish = new Publish();
        publishDTO.setPublishStatus(PublishStatusEnum.PUBLISHED.getCode());
        publishDTO.setStarNum(0);
        BeanUtils.copyProperties(publishDTO, publish);
        publishDao.save(publish);

        return publishDTO;
    }

    @Override
    public PublishDTO findOne(String publishId) {
        //TODO 搜索发布？？？
        Publish publish = publishDao.findOne(publishId);
        ProductInfo productInfo = contentService.findProductById(publish.getProductId());
        PublishDTO publishDTO = new PublishDTO();
        publishDTO.setProductInfo(productInfo);
        BeanUtils.copyProperties(publish,publishDTO);
        return publishDTO;
    }

    @Override
    public void star(String publishId) {
        //1.判断是否存在该发布
        Publish publish = publishDao.findOne(publishId);
        if (publish == null) {
            throw new TBookException(ResultEnum.PULISH_NOT_EXIST);
        }

        //2.点赞加1
        Integer starNum = publish.getStarNum();
        publish.setStarNum(starNum+1);

        //3.更新发布
        publishDao.save(publish);
    }

    @Override
    public OrderDTO buy(PublishDTO publishDTO) {
        //TODO 考虑移至用户模块
        return null;
    }

    @Override
    public Page<PublishDTO> findList(String userId, Pageable pageable) {
        //TODO 判断是否存在该用户

        //2.按页查找
        Page<Publish> publishPage = publishDao.findByPublisherId(userId, pageable);
        List<Publish> publishList = publishPage.getContent();
        List<PublishDTO> publishDTOList = new ArrayList<>();

        //查找各个发布中的商品
        for (Publish publish : publishList){
            ProductInfo productInfo = contentService.findProductById(publish.getProductId());
            PublishDTO publishDTO = new PublishDTO();
            BeanUtils.copyProperties(publish,publishDTO);
            publishDTOList.add(publishDTO);
        }
        PageImpl<PublishDTO> publishDTOPage = new PageImpl<>(publishDTOList);
        return publishDTOPage;
    }
}
