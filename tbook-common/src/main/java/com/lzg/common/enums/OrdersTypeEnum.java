package com.lzg.common.enums;

import lombok.Getter;

/**
 * 作者：LizG on 2018/7/22 19:14
 * 描述：
 */
@Getter
public enum OrdersTypeEnum {
    BUY(0, "买"),
    SELL(1,"卖")
    ;

    private Integer code;

    private String message;

    OrdersTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
