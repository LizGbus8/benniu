package com.lzg.common.enums;

import lombok.Getter;

/**
 * 作者：LizG on 2018/7/22 19:18
 * 描述：
 */
@Getter
public enum PayStatusEnum {
    WAIT(0,"等待支付"),
    SUCCESS(1,"成功支付")
    ;
    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
