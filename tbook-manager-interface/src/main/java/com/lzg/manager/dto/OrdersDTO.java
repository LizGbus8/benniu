package com.lzg.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzg.common.utlis.serializer.Date2StringSerialize;
import com.lzg.manager.entity.Content;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LizG on 2018/7/27 22:33
 * 描述：订单DTO ----Orders & Content----
 */
@Data
public class OrdersDTO implements Serializable {
    /** 订单id */
    private String orderId;

    /** 用户id */
    private String userId;

    private String nickName;

    /** 用户联系方式 */
    private String contact;


    /** 订单类型 */
    private Integer orderType;

    private Integer orderStatus;

    /** 买家id */
    private String tradersId;

    /** 买家昵称 */
    private String tradersNickName;

    /** 买家联系方式 */
    private String tradersContact;

    /** 买家评价 */
    private String commented;

    /** 商品 */
    private Content content;

    /** 订单生成时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date createTime;

    /** 订单更新时间*/
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date updateTime;

    /** 完结时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date finishTime;

    /** 评价时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date commentedTime;
}
