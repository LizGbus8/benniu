package com.lzg.order.service;

import com.lzg.manager.dto.OrdersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 作者：LizG on 2018/7/27 22:05
 * 描述：订单服务
 */
public interface OrderService {

    OrdersDTO createBuyOrder(OrdersDTO ordersDTO);

    OrdersDTO findOrder(String ordersId);

    Page<OrdersDTO> findSellList(String userId, Pageable pageable);

    Page<OrdersDTO> findBuyList(String userId, Pageable pageable);

    OrdersDTO update(OrdersDTO ordersDTO);

    OrdersDTO finished(String ordersId);

    OrdersDTO commented(OrdersDTO ordersDTO);

    //TODO 删除订单

    OrdersDTO changeStatus(String ordersId,Integer status);
}
