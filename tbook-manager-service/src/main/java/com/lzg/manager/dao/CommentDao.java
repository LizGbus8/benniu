package com.lzg.manager.dao;

import com.lzg.manager.entity.Tbcomment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/10/7 16:20
 * 描述：
 */
public interface CommentDao extends JpaRepository<Tbcomment,String> {
    Tbcomment findByOrderId(String orderId);
    Page<Tbcomment> findByUserIdOrderByCreateTime(String userId, Pageable pageable);
}
