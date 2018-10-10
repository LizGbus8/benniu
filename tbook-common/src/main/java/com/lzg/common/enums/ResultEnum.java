package com.lzg.common.enums;

import lombok.Getter;

/**
 * 作者：LizG on 2018/7/23 15:00
 * 描述：响应结果
 */
@Getter
public enum ResultEnum {
    UNKOWN(-1,"未知"),
    SUCCESS(0,"成功"),
    CONTENT_NOT_EXIST(1,"内容不存在"),
    CONTENT_STOCK_ERROR(2,"内容库存错误"),
    ORDERS_NOT_EXIST(3,"订单信息不存在"),
    ORDERDETAIL_NOT_EXIST(4,"订单详情不存在"),
    ORDERS_STATUS_ERROR(5,"订单状态错误"),
    ORDERS_UPDATE_ERROR(6,"订单更新失败"),
    FORM_PARAM_ERROR(7,"表单参数错误"),
    BUYER_OPENID_ERROR(8,"当前用户ID不一致"),
    PULISH_NOT_EXIST(9,"发布不存在"),
    CATEGORY_NOT_EXIST(10,"类目不存在"),
    SCHOOL_NOT_EXIST(11,"学校不存在"),
    HAS_USER(12,"用户已存在"),
    VERIFY_ERROR(13,"验证错误"),
    NOT_HAS_LOGIN(14,"未登录"),
    NOT_HAS_Register(15,"未注册"),
    TOKEN_ERROT(16,"token error"),
    IMAGE_ERROT(17,"图片不存在"),
    PARAM_ERROT(18,"参数错误"),
    INDEX_IMPORT_ERROT(18,"索引导入错误")
    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
