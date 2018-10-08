package com.lzg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.JsonUtilByGson;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.dto.CommentDTO;
import com.lzg.manager.dto.OrdersDTO;
import com.lzg.manager.entity.Tbcomment;
import com.lzg.manager.entity.User;
import com.lzg.manager.form.CommentForm;
import com.lzg.manager.service.CommentService;
import com.lzg.order.service.OrderService;
import com.lzg.sso.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LizG on 2018/8/3 22:58
 * 描述：
 */
@RestController
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Reference
    private OrderService orderService;

    @Reference
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "/comment/{orderId}")
    public ResultVO readComment(@PathVariable("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            throw new TBookException(ResultEnum.ORDERS_NOT_EXIST);
        }
        Tbcomment comment = commentService.findComment(orderId);

        OrdersDTO order = orderService.findOrder(comment.getOrderId());
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment,commentDTO);
        commentDTO.setProductTitle(order.getContent().getProductTitle());

        return ResultVOUtil.success(commentDTO);
    }

    @PostMapping(value = "/comment")
    public ResultVO comment(@Valid @RequestBody CommentForm commentForm, BindingResult bindingResult) {

        /* 验证表单 */
        if (bindingResult.hasErrors()) {
            throw new TBookException(ResultEnum.FORM_PARAM_ERROR);
        }

        /** 获取买家的信息 */
        /* 获取token */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        log.info("token is {}", token);

        /* 获取用户信息 */
        String json = (String) redisUtil.get(token);
        if (json == null) {
            throw new TBookException(ResultEnum.NOT_HAS_LOGIN);
        }
        //买家
        User user = JsonUtilByGson.jsonToBean(json, User.class);

        //查找买家方的订单
        OrdersDTO buyOrder = orderService.findOrder(commentForm.getOrderId());
        buyOrder.setNickName(user.getNickname());

        log.info("buyOrder : {}", buyOrder);
        Tbcomment comment = commentService.comment(buyOrder, commentForm.getContent(), commentForm.getType());

        return ResultVOUtil.success(comment);
    }

    @GetMapping(value = "/comments/{userId}")
    public ResultVO commentlist(@PathVariable("userId") String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new TBookException(ResultEnum.VERIFY_ERROR);
        }
        PageRequest request = new PageRequest(0, 10);
        Page<Tbcomment> list = commentService.list(userId, request);

        /* 查找商品信息 */
        List<CommentDTO> comments = new ArrayList<>();
        for (Tbcomment comment : list.getContent()) {
            OrdersDTO order = orderService.findOrder(comment.getOrderId());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setProductTitle(order.getContent().getProductTitle());
            comments.add(commentDTO);
        }


        log.info("comments is {}", comments);
        return ResultVOUtil.success(comments);
    }
}
