package com.lzg.manager.service;

import com.lzg.manager.dto.OrdersDTO;
import com.lzg.manager.entity.Tbcomment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 作者：LizG on 2018/10/7 16:18
 * 描述：
 */
public interface CommentService {
    Tbcomment findComment(String orderId);

    Tbcomment comment(OrdersDTO ordersDTO, String content, String type);

    Page<Tbcomment> list(String userId, Pageable pageable);
}
