package com.lzg.tbook.order.service;

import com.lzg.manager.entity.Content;
import com.lzg.order.dto.OrdersDTO;
import com.lzg.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 作者：LizG on 2018/7/29 10:25
 * 描述：
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    public OrdersDTO data(){
        Content content = new Content();
        content.setContentId("500");
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setUserId("3366");
        ordersDTO.setContent(content);
        ordersDTO.setBuyerId("837422051");
        ordersDTO.setBuyerPhone("13602799701");
        ordersDTO.setBuyerAddress("华南农业大学");
        ordersDTO.setBuyerNickName("L");
        return ordersDTO;
    }

    @Test
    public void createBuyOrder() {
        OrdersDTO ordersDTO = data();
        OrdersDTO buyOrder = orderService.createBuyOrder(ordersDTO);
        Assert.assertNotNull(buyOrder);
    }

    @Test
    public void update() {
    }

    @Test
    public void findOrder() {
    }

    @Test
    public void findSellList() {

    }

    @Test
    public void findBuyList1() {
        Page<OrdersDTO> buyPage = orderService.findBuyList("3366", new PageRequest(0, 1));
        buyPage.getContent().forEach(ordersDTO -> {
            System.out.println(ordersDTO);
        });
    }

    @Test
    public void finished1() {
        OrdersDTO ordersDTO = data();
        OrdersDTO finished = orderService.finished("orders20182812182851396029");
        Assert.assertNotNull(finished);
    }

    @Test
    public void commented() {
    }

    @Test
    public void close() {
    }
}