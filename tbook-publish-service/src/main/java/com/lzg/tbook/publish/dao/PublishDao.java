package com.lzg.tbook.publish.dao;

import com.lzg.publish.entity.Publish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/7/29 17:44
 * 描述：
 */
public interface PublishDao extends JpaRepository<Publish,String> {
    /**
     * 描述: 查找卖出订单
     * @Param
     * @return
     */
    Page<Publish> findByPublisherId(String publishId,Pageable pageable);
}
