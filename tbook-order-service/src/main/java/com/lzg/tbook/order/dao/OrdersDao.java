package com.lzg.tbook.order.dao;

import com.lzg.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：LizG on 2018/7/28 21:28
 * 描述：
 */
public interface OrdersDao extends JpaRepository<Orders, String> {

    /**
     * 描述: find订单Page
     *
     * @return
     * @Param
     */
    Page<Orders> findByUserIdAndAndOrderType(String userId, Integer orderType, Pageable pageable);

}
