package com.lzg.manager.service;

import com.lzg.manager.entity.Content;

/**
 * 作者：LizG on 2018/7/27 23:36
 * 描述：商品
 */
public interface ContentService {
    /**
     * 描述:通过id查找内容
     * @Param
     * @return
     */
    Content findContentById(String contentId);

    /**
     * 描述: 保存内容
     * @Param
     * @return
     */
    Content saveContent(Content content);

    /**
     * 描述: 修改商品信息
     * @Param
     * @return
     */
    Content updateContent(Content content);

    /**
     * 描述: 加库存
     * @Param productId-商品id
     * @return
     */
    void increaseStock(String contentId);

    /**
     * 描述: 减库存
     * @Param productId-商品id
     * @return
     */
    void decreaseStock(String contentId);
}
