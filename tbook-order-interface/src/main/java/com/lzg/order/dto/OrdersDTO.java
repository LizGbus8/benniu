package com.lzg.order.dto;

import com.lzg.manager.entity.Content;
import lombok.Data;

import java.util.Date;

/**
 * 作者：LizG on 2018/7/27 22:33
 * 描述：订单DTO ----Orders & Content----
 */
@Data
public class OrdersDTO {
    /** 订单id */
    private String orderId;

    /** 用户id */
    private String userId;

    /** 订单类型 */
    private Integer orderType;

    /** 订单状态 新订单-0 已拍下-1 已付款-2 完结-3 待评价-4*/
    private Integer orderStatus;

    /** 买家评价 */
    private String commented;

    /** 买家id */
    private String buyerId;

    /** 买家昵称*/
    private String buyerNickName;

    /** 买家地址 */
    private String buyerAddress;

    /** 联系方式 */
    private String buyerPhone;

    /** 商品id */
    private Content content;

    /** 订单生成时间 */
    private Date createTime;

    /** 订单更新时间*/
    private Date updateTime;

    /** 付款时间 */
    private Date paidTime;

    /** 完结时间 */
    private Date finishTime;

    /** 评价时间 */
    private Date commentedTime;
}
