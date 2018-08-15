package com.lzg.tbook.order.listener;

import com.lzg.common.enums.OrdersTypeEnum;
import com.lzg.order.dto.OrdersDTO;
import com.lzg.order.entity.Orders;
import com.lzg.order.service.OrderService;
import com.lzg.tbook.order.dao.OrdersDao;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;

/**
 * 作者：LizG on 2018/8/11 19:57
 * 描述：卖家订单监听
 */
@Service
public class SellListener {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private OrderService orderService;

    private static final String SELL= "sell";

    @RabbitListener(queues = "orders.create")
    public void createOrder(OrdersDTO ordersDTO){
        /** 设置订单标识 */
        String sellOrderId = SELL + ordersDTO.getOrderId();
        ordersDTO.setOrderId(sellOrderId);

        /** 设置订单类型 */
        ordersDTO.setOrderType(OrdersTypeEnum.SELL.getCode());

        /** 设置用户id*/
        ordersDTO.setUserId(ordersDTO.getContent().getUserId());

        /** 订单入库 */
        Orders o = new Orders();
        o.setContentId(ordersDTO.getContent().getContentId());
        BeanUtils.copyProperties(ordersDTO,o);
        ordersDao.save(o);
    }

    @RabbitListener(queues = "orders.finished")
    public void finished(OrdersDTO ordersDTO){
        /** 设置订单标识 */
        String sellOrderId = SELL + ordersDTO.getOrderId();
        ordersDTO.setOrderId(sellOrderId);

        /** 更新 */
        orderService.update(ordersDTO);
    }

    @RabbitListener(queues = "orders.close")
    public void close(OrdersDTO ordersDTO){
        /** 设置订单标识 */
        String sellOrderId = SELL + ordersDTO.getOrderId();
        ordersDTO.setOrderId(sellOrderId);

        /** 更新 */
        orderService.update(ordersDTO);
    }
}
