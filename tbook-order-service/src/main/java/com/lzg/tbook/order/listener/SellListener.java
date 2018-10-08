package com.lzg.tbook.order.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.enums.OrdersTypeEnum;
import com.lzg.manager.dto.OrdersDTO;
import com.lzg.manager.entity.User;
import com.lzg.order.entity.Orders;
import com.lzg.order.service.OrderService;
import com.lzg.tbook.order.dao.OrdersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：LizG on 2018/8/11 19:57
 * 描述：卖家订单监听
 */
@Service
@Slf4j
public class SellListener {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private OrderService orderService;

    @Reference
    private com.lzg.sso.service.UserInfoService userInfoService;

    private static final String SELL= "sell";

    @RabbitListener(queues = "orders.create")
    public void createOrder(OrdersDTO ordersDTO){
        /** 设置订单标识 */
        String sellOrderId = SELL + ordersDTO.getOrderId();
        ordersDTO.setOrderId(sellOrderId);

        /** 设置订单类型 */
        ordersDTO.setOrderType(OrdersTypeEnum.SELL.getCode());

        /** 获取交易人信息 */
        User traders = userInfoService.getUserInfoByOpenid(ordersDTO.getUserId());

        /** 设置用户id */
        ordersDTO.setUserId(ordersDTO.getContent().getUserId());

        /** 设置交易人信息 */
        ordersDTO.setTradersId(traders.getOpenid());
        ordersDTO.setTradersNickName(traders.getNickname());
        ordersDTO.setTradersContact(traders.getContact());

        /** 订单入库 */
        Orders o = new Orders();
        o.setContentId(ordersDTO.getContent().getContentId());
        BeanUtils.copyProperties(ordersDTO,o);
        ordersDao.save(o);
    }

    @RabbitListener(queues = "orders.changestatus")
    public void changeStatus(OrdersDTO ordersDTO){
        /** 设置订单标识 */
        String flag = ordersDTO.getOrderId().substring(0, 4);
        log.info("order 类型：" + flag);
        String orderId = null;
        if (SELL.equals(flag)){
            orderId = ordersDTO.getOrderId().substring(4);
        }else {
            orderId = SELL + ordersDTO.getOrderId();
        }

        ordersDTO.setOrderId(orderId);

        log.info("rabbit 异步更新的订单：" + ordersDTO);

        /** 更新 */
        orderService.update(ordersDTO);
    }
}
