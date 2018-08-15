package com.lzg.publish.service;

import com.lzg.order.dto.OrderDTO;
import com.lzg.publish.dto.PublishDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 作者：LizG on 2018/7/28 0:29
 * 描述：发布服务
 */
public interface PublishService {

    /**
     * 描述: 创建发布
     * @Param
     * @return
     */
    PublishDTO create(PublishDTO publishDTO);

    /**
     * 描述: 查找发布
     * @Param
     * @return
     */
    PublishDTO findOne(String publishId);

    /**
     * 描述: 点赞
     * @Param
     * @return
     */
    void star(String publishId);

    /**
     * 描述: 购买
     * @Param
     * @return
     */
    OrderDTO buy(PublishDTO publishDTO);


    /**
     * 描述: 查找我的发布
     * @Param
     * @return
     */
    Page<PublishDTO> findList(String userId, Pageable pageable);
}
