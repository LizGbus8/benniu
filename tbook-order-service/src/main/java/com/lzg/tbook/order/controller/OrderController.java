package com.lzg.tbook.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.OrdersTypeEnum;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.dto.OrdersDTO;
import com.lzg.manager.entity.Content;
import com.lzg.manager.entity.User;
import com.lzg.order.dto.ResultDTO;
import com.lzg.sso.service.UserInfoService;
import com.lzg.tbook.order.converter.OrderDTO2ResultDTO;
import com.lzg.tbook.order.form.WantForm;
import com.lzg.tbook.order.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 作者：LizG on 2018/10/3 22:00
 * 描述：
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Reference
    private UserInfoService userInfoService;

    @PostMapping(value = "/order")
    public ResultVO want(@Valid @RequestBody WantForm wantForm, BindingResult bindingResult) {
        /* 验证表单 */
        log.info("userInfoForm is {}", wantForm);
        if (bindingResult.hasErrors()) {
            throw new TBookException(ResultEnum.FORM_PARAM_ERROR);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        /** 获取用户信息 */
        User userInfo = userInfoService.getUserInfo(token);
        /** 获取交易人信息 */
        User sallerUser = userInfoService.getUserInfoByOpenid(wantForm.getUserId());


        //contentid
        OrdersDTO ordersDTO = new OrdersDTO();
        /* 设置contentId */
        Content content = new Content();
        content.setContentId(wantForm.getContentId());
        ordersDTO.setContent(content);

        /* 设置userId */
        ordersDTO.setUserId(userInfo.getOpenid());
        ordersDTO.setNickName(userInfo.getNickname());
        ordersDTO.setContact(userInfo.getContact());

        /* 设置交易人信息 */
        ordersDTO.setTradersId(sallerUser.getOpenid());
        ordersDTO.setTradersNickName(sallerUser.getNickname());
        ordersDTO.setTradersContact(sallerUser.getContact());


        OrdersDTO buyOrder = orderService.createBuyOrder(ordersDTO);
        /* 转化为结果 */
        ResultDTO resultDTO = OrderDTO2ResultDTO.orderDTO2ResultDTO(buyOrder);
        log.info("resultDTO is {}",resultDTO);
        return ResultVOUtil.success(resultDTO);
    }

    @GetMapping("/order/list")
    public ResultVO getOrdersByUserId(@RequestParam(value = "userId") String userId,
                                        @RequestParam(value = "type") Integer orderType,
                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest request = new PageRequest(page, size);

        /* 如果是卖出 */
        Page<OrdersDTO> orderList = null;
        if (OrdersTypeEnum.SELL.getCode().equals(orderType)){
            orderList = orderService.findSellList(userId, request);

        /* 如果是买入 */
        }else if (OrdersTypeEnum.BUY.getCode().equals(orderType)){
            orderList = orderService.findBuyList(userId, request);
        }

        log.info("orderList is {}",orderList.getContent());
        return ResultVOUtil.success(orderList.getContent());
    }

    public ResultVO readCommentByOrder(){
        return null;
    }

    @PutMapping(value = "/order/{orderId}/{orderStatus}")
    public ResultVO changeOrderStatus(@PathVariable("orderId") String orderId,
                               @PathVariable("orderStatus") Integer orderStatus){
        //校验参数
        if (StringUtils.isEmpty(orderId) || orderStatus == null){
            throw new TBookException(ResultEnum.FORM_PARAM_ERROR);
        }

        OrdersDTO ordersDTO = orderService.changeStatus(orderId, orderStatus);

        return ResultVOUtil.success(ordersDTO);
    }
}
