package com.lzg.order.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzg.common.utlis.serializer.Date2StringSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：LizG on 2018/10/4 12:51
 * 描述：
 */
@Data
public class ResultDTO {
    /** 用户id */
    private String userId;

    private String nickName;

    /** 用户联系方式 */
    private String contact;

    /** 商品title */
    private String productTitle;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 买家id */
    private String tradersId;

    /** 买家昵称 */
    private String tradersNickName;

    /** 买家联系方式 */
    private String tradersContact;

    /** 订单生成时间 */
    @JsonSerialize(using = Date2StringSerialize.class)
    private Date createTime;


}
