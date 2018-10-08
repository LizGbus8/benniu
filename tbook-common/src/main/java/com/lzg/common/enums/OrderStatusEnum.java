package com.lzg.common.enums;

import lombok.Getter;

/**
 * 作者：LizG on 2018/7/22 19:14
 * 描述：
 */
@Getter
public enum OrderStatusEnum {
    PROCESSING(0, "交易中"),
    CLOSE(1,"关闭交易"),
    FINISH(3,"完成交易"),
    COMMENT(4,"已评价")
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
