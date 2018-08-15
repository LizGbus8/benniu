package com.lzg.common.enums;

import lombok.Getter;

/**
 * 作者：LizG on 2018/7/23 15:00
 * 描述：响应结果
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
    CONTENT_NOT_EXIST(1,"内容不存在"),
    CONTENT_STOCK_ERROR(2,"内容库存错误"),
    ORDERS_NOT_EXIST(3,"订单信息不存在"),
    ORDERDETAIL_NOT_EXIST(4,"订单详情不存在"),
    ORDERS_STATUS_ERROR(5,"订单状态错误"),
    ORDERS_UPDATE_ERROR(6,"订单更新失败"),
    FORM_PARAM_ERROR(7,"表单参数错误"),
    BUYER_OPENID_ERROR(8,"当前用户ID不一致"),
    PULISH_NOT_EXIST(9,"发布不存在")
    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
