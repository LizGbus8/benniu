package com.lzg.tbook.order.converter;

import com.lzg.manager.dto.OrdersDTO;
import com.lzg.order.dto.ResultDTO;
import org.springframework.beans.BeanUtils;

/**
 * 作者：LizG on 2018/10/4 12:55
 * 描述：
 */
public class OrderDTO2ResultDTO {

    public static ResultDTO orderDTO2ResultDTO(OrdersDTO ordersDTO){
        ResultDTO resultDTO = new ResultDTO();
        BeanUtils.copyProperties(ordersDTO,resultDTO);
        BeanUtils.copyProperties(ordersDTO.getContent(),resultDTO);
        return resultDTO;
    }
}
