package com.lzg.manager.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lzg.common.utlis.KeyUtil;
import com.lzg.manager.dao.CommentDao;
import com.lzg.manager.dto.OrdersDTO;
import com.lzg.manager.entity.Tbcomment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 作者：LizG on 2018/10/7 16:19
 * 描述：
 */
@Component
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    private static final String COMMENT = "comment";

    private static final String SELL = "sell";
    @Override
    public Tbcomment findComment(String orderId) {
        return commentDao.findByOrderId(orderId);
    }

    @Override
    public Tbcomment comment(OrdersDTO ordersDTO, String content, String type) {
        Tbcomment tbcomment = new Tbcomment();
        //设置id
        tbcomment.setCommentId(COMMENT+KeyUtil.getKey());

        //设置order_id 卖家方的
        tbcomment.setOrderId(SELL+ordersDTO.getOrderId());

        //设置user_id卖家方的
        tbcomment.setUserId(ordersDTO.getTradersId());

        //设置from_nickname
        tbcomment.setFromNickname(ordersDTO.getNickName());

        //设置type
        tbcomment.setType(type);

        //设置content
        tbcomment.setContent(content);

        log.info("tbcomment is {}",tbcomment);
        Tbcomment save = commentDao.save(tbcomment);

        return save;
    }

    @Override
    public Page<Tbcomment> list(String userId, Pageable pageable) {
        Page<Tbcomment> comments = commentDao.findByUserIdOrderByCreateTime(userId, pageable);
        return comments;
    }

}
