package com.lzg.tbook.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lzg.common.enums.OrderStatusEnum;
import com.lzg.common.enums.OrdersTypeEnum;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.KeyUtil;
import com.lzg.manager.entity.Content;
import com.lzg.order.dto.OrdersDTO;
import com.lzg.order.entity.Orders;
import com.lzg.order.service.OrderService;
import com.lzg.manager.service.ContentService;
import com.lzg.tbook.order.dao.OrdersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LizG on 2018/7/28 21:38
 * 描述：
 */
@Component
@Service(version = "1.0.0")
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Reference(version = "1.0.0")
    private ContentService contentService;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String CONTENT = "content";

    private static final String ORDERS = "orders";

    private static final String ORDERS_PAGE = "ordersPage";

    @Override
    @Transactional
    public OrdersDTO createBuyOrder(OrdersDTO ordersDTO) {
        /** 查找内容 */
        Content findContent = contentService.findContentById(ordersDTO.getContent().getContentId());
        ordersDTO.setContent(findContent);

        /** 设置订单id */
        String ordersId = ORDERS + KeyUtil.getKey();
        ordersDTO.setOrderId(ordersId);

        /** 设置订单类型 */
        ordersDTO.setOrderType(OrdersTypeEnum.BUY.getCode());

        /** 设置订单状态 */
        ordersDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());

        //TODO 扣库存
        contentService.decreaseStock(findContent.getContentId());

        /** 订单入库 */
        Orders o = new Orders();
        o.setContentId(ordersDTO.getContent().getContentId());
        BeanUtils.copyProperties(ordersDTO, o);
        ordersDao.save(o);

        /** 异步创建卖家订单 */
        try {
            rabbitTemplate.convertAndSend("orders",
                    "orders.create", ordersDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordersDTO;
    }

    @Override
    public OrdersDTO update(OrdersDTO ordersDTO) {
        /** 查找订单 */
        OrdersDTO findOrderDTO = findOrder(ordersDTO.getOrderId());

        /** 更新 */
        Orders o = new Orders();
        o.setContentId(ordersDTO.getContent().getContentId());
        BeanUtils.copyProperties(ordersDTO, o);
        ordersDao.save(o);

        /** 同步缓存 */
        try {
            redisUtil.hget(ORDERS, o.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordersDTO;
    }

    @Override
    public OrdersDTO findOrder(String ordersId) {
        /** 查找缓存 */
        Orders findOrders = null;
        try {
            findOrders = (Orders) redisUtil.hget(ORDERS, ordersId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** 查找数据库 */
        if (findOrders == null) {
            /** 查找订单 */
            findOrders = ordersDao.findOne(ordersId);
        }

        /** 缓存和数据库都不存在 */
        if (findOrders == null) {
            throw new TBookException(ResultEnum.ORDERS_NOT_EXIST);
        }

        /** 查找商品 */
        Content findContent = contentService.findContentById(findOrders.getContentId());

        /** 聚合DTO */
        OrdersDTO ordersDTO = new OrdersDTO();
        BeanUtils.copyProperties(findOrders, ordersDTO);
        ordersDTO.setContent(findContent);

        return ordersDTO;
    }

    @Override
    public Page<OrdersDTO> findSellList(String userId, Pageable pageable) {
        /** 查找数据库 */
        Page<Orders> findSellPage = ordersDao.findByUserIdAndAndOrderType(userId,
                OrdersTypeEnum.SELL.getCode(), pageable);
        List<Orders> sellOrderList = findSellPage.getContent();//get OrdersList


        /** 查找每个订单的内容 */
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        for (Orders orders : sellOrderList) {
            String contentId = orders.getContentId();
            //find Content
            Content content = contentService.findContentById(contentId);
            //set Content To Orders
            OrdersDTO o = new OrdersDTO();
            BeanUtils.copyProperties(orders,o);
            o.setContent(content);

            ordersDTOList.add(o);
        }
        PageImpl<OrdersDTO> ordersDTOPage = new PageImpl<>(ordersDTOList);
        return ordersDTOPage;
    }

    @Override
    public Page<OrdersDTO> findBuyList(String userId, Pageable pageable) {
        /** 查找数据库 */
        Page<Orders> findBuyPage = ordersDao.findByUserIdAndAndOrderType(userId,
                OrdersTypeEnum.BUY.getCode(), pageable);
        List<Orders> buyOrderList = findBuyPage.getContent();//get OrdersList

        /** 查找每个订单的内容 */
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        for (Orders orders : buyOrderList) {
            String contentId = orders.getContentId();
            //find Content
            Content content = contentService.findContentById(contentId);
            //set Content To Orders
            OrdersDTO o = new OrdersDTO();
            BeanUtils.copyProperties(orders,o);
            o.setContent(content);

            ordersDTOList.add(o);
        }
        PageImpl<OrdersDTO> ordersDTOPage = new PageImpl<>(ordersDTOList);
        return ordersDTOPage;
    }


    @Override
    public OrdersDTO finished(String ordersId) {
        /** 查找订单 */
        OrdersDTO ordersDTO = findOrder(ordersId);

        /** 判断订单状态 */
        if (!ordersDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new TBookException(ResultEnum.ORDERS_STATUS_ERROR);
        }

        /** 修改状态 */
        ordersDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());

        /** 更新 */
        update(ordersDTO);

        /** 异步更新卖家订单 */
        try {
            rabbitTemplate.convertAndSend("orders","orders.finished",ordersDTO);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ordersDTO;
    }

    @Override
    public OrdersDTO commented(OrdersDTO ordersDTO) {
        return null;
    }

    @Override
    public OrdersDTO close(String ordersId) {
        /** 查找订单 */
        OrdersDTO ordersDTO = findOrder(ordersId);

        /** 判断订单状态 */
        if (!ordersDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new TBookException(ResultEnum.ORDERS_STATUS_ERROR);
        }

        /** 修改状态 */
        ordersDTO.setOrderStatus(OrderStatusEnum.CLOSE.getCode());

        /** 更新 */
        update(ordersDTO);

        /** 异步更新卖家订单 */
        try {
            rabbitTemplate.convertAndSend("orders","orders.close",ordersDTO);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ordersDTO;
    }
}
